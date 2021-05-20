package com.godeltech.mastery.backend.service.impl;

import com.godeltech.mastery.backend.domain.dto.request.OperationCreateRequest;
import com.godeltech.mastery.backend.domain.dto.responce.ServiceOperationRecordDTO;
import com.godeltech.mastery.backend.exception.EntityNotFoundException;
import com.godeltech.mastery.backend.mapper.OperationMapper;
import com.godeltech.mastery.backend.repository.OperationRepository;
import com.godeltech.mastery.backend.service.CarService;
import com.godeltech.mastery.backend.service.OperationService;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;

import static java.util.Collections.singletonList;
import static net.sf.jasperreports.engine.JasperCompileManager.compileReport;
import static net.sf.jasperreports.engine.JasperExportManager.exportReportToPdf;
import static net.sf.jasperreports.engine.JasperFillManager.fillReport;

@Service
@RequiredArgsConstructor
public class OperationServiceImpl implements OperationService {

  private final OperationRepository operationRepository;
  private final CarService carService;
  private final OperationMapper operationMapper;

  @Override
  public Long createOperation(final Long carId, final OperationCreateRequest operation) {
    final var serviceOperationRecord = operationMapper.toEntity(operation);
    final var car = carService.findCarById(carId);
    serviceOperationRecord.setCar(car);
    return operationRepository.save(serviceOperationRecord).getId();
  }

  @Override
  public List<ServiceOperationRecordDTO> getAllRecordsByCarId(final Long carId) {
    final var car = carService.findCarById(carId);
    return operationMapper.toDTOList(
        operationRepository
            .getAllByCar(car)
            .orElseThrow(
                () ->
                    new EntityNotFoundException(
                        "Could not find any operation records by car id = " + carId)));
  }

  @Override
  public File createReport(final Long carId) {
    final var car = carService.findCarById(carId);
    final var file = new File("carOperations.pdf");
    try (var pos = new FileOutputStream(file)) {
      final var reportInputStream = getClass().getResourceAsStream("/generateReport/report.jrxml");
      final var report = compileReport(reportInputStream);
      final var dataSource = new JRBeanCollectionDataSource(singletonList(car));
      final var print = fillReport(report, new HashMap<>(), dataSource);
      final byte[] outputBytes = exportReportToPdf(print);
      pos.write(outputBytes);
    } catch (final Exception e) {
      e.printStackTrace();
    }
    return file;
  }
}
