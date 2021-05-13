package com.godeltech.mastery.backend.service.impl;

import static java.util.Collections.singletonList;
import static net.sf.jasperreports.engine.JasperCompileManager.compileReport;

import com.godeltech.mastery.backend.domain.dto.request.OperationCreateRequest;
import com.godeltech.mastery.backend.domain.dto.responce.ServiceOperationRecordDTO;
import com.godeltech.mastery.backend.exception.EntityNotFoundException;
import com.godeltech.mastery.backend.mapper.OperationMapper;
import com.godeltech.mastery.backend.repository.OperationRepository;
import com.godeltech.mastery.backend.service.CarService;
import com.godeltech.mastery.backend.service.OperationService;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Service;

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
    car.setMileage(operation.getMileage());
    serviceOperationRecord.setCar(car);
    return operationRepository.save(serviceOperationRecord).getId();
  }

  @Override
  public List<ServiceOperationRecordDTO> getAllRecordsByCarId(final Long carId) {
    final var car = carService.findCarById(carId);
    return operationMapper.toDTOList(operationRepository.getAllByCar(car).orElseThrow(
        () -> new EntityNotFoundException(
            "Could not find any operation records by car id = " + carId)));
  }

  @Override
  public File createReport(final Long carId) {
    final var car = carService.findCarById(carId);
    final File file = new File("carOperations.pdf");
    try (FileOutputStream pos = new FileOutputStream(file)) {
      final InputStream reportInputStream = getClass()
          .getResourceAsStream("/generateReport/report.jrxml");
      final JasperReport report = compileReport(reportInputStream);
      final JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(
          singletonList(car));
      final var print = JasperFillManager.fillReport(report, new HashMap<>(), dataSource);
      final byte[] outputBytes = JasperExportManager.exportReportToPdf(print);
      pos.write(outputBytes);
    } catch (final Exception e) {
      e.printStackTrace();
    }
    return file;
  }
}
