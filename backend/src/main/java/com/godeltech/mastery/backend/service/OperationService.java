package com.godeltech.mastery.backend.service;

import com.godeltech.mastery.backend.domain.dto.request.OperationCreateRequest;
import com.godeltech.mastery.backend.domain.dto.responce.ServiceOperationRecordDTO;
import java.io.File;
import java.util.List;

public interface OperationService {

  Long createOperation(Long carId, OperationCreateRequest operation);

  List<ServiceOperationRecordDTO> getAllRecordsByCarId(Long carId);

  File createReport(Long carId);
}
