package com.godeltech.mastery.backend.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface AwsService {

  String uploadImage(MultipartFile multipartFile, Long carId);
}
