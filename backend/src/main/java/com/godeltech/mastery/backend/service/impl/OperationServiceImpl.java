package com.godeltech.mastery.backend.service.impl;

import com.godeltech.mastery.backend.domain.dto.request.OperationCreateRequest;
import com.godeltech.mastery.backend.domain.dto.responce.ServiceOperationRecordDTO;
import com.godeltech.mastery.backend.domain.entity.Car;
import com.godeltech.mastery.backend.exception.EntityNotFoundException;
import com.godeltech.mastery.backend.mapper.OperationMapper;
import com.godeltech.mastery.backend.repository.CarRepository;
import com.godeltech.mastery.backend.repository.OperationRepository;
import com.godeltech.mastery.backend.service.OperationService;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleWriterExporterOutput;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OperationServiceImpl implements OperationService {

  private final OperationRepository operationRepository;
  private final CarRepository carRepository;
  private final OperationMapper operationMapper;

  @Override
  public Long createOperation(final Long carId, final OperationCreateRequest operation) {
    final var serviceOperationRecord = operationMapper.toEntity(operation);
    final var car = getCarById(carId);
    car.setMileage(operation.getMileage());
    serviceOperationRecord.setCar(car);
    return operationRepository.save(serviceOperationRecord).getId();
  }

  @Override
  public List<ServiceOperationRecordDTO> getAllRecordsByCarId(final Long carId) {
    final var car = getCarById(carId);
    return operationMapper.toDTOList(operationRepository.getAllByCar(car).orElseThrow(() -> new EntityNotFoundException("Could not find any operation records by car id = " + carId)));
  }

  @Override
  public void createReport(final Long carId, final String pathName, final String fileFormat) {
    final var car = getCarById(carId);
    final InputStream stream = this.getClass().getResourceAsStream("/generateReport/report.jrxml");
    try {
      final JasperReport report = JasperCompileManager.compileReport(stream);
      final var source = new JRBeanCollectionDataSource(List.of(car));
      final JasperPrint print = JasperFillManager.fillReport(report, new HashMap<>(), source);
      switch (fileFormat == null ? "pdf" : fileFormat.toLowerCase()) {
        case "csv" -> {
          final var exporterCsv = new JRCsvExporter();
          exporterCsv.setExporterInput(new SimpleExporterInput(print));
          exporterCsv.setExporterOutput(
                  new SimpleWriterExporterOutput(new File(pathName + "/carOperations.csv")));
          exporterCsv.exportReport();
        }
        case "docx" -> {
          final var exporterDocx = new JRDocxExporter();
          exporterDocx.setExporterInput(new SimpleExporterInput(print));
          exporterDocx.setExporterOutput(
                  new SimpleOutputStreamExporterOutput(new File(pathName + "/carOperations.docx")));
          exporterDocx.exportReport();
        }
        case "html" -> JasperExportManager.exportReportToHtmlFile(print, pathName + "/carOperations.html");
        case "pdf" -> JasperExportManager.exportReportToPdfFile(print, pathName + "/carOperations.pdf");
        default -> throw new IllegalArgumentException("Not supported type to print. Type = " + fileFormat);
      }
    } catch (JRException ex) {
      ex.printStackTrace();
    }
  }

  private Car getCarById(final Long id) {
    return carRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("car", id));
  }
}
