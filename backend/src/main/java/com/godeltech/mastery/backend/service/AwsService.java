package com.godeltech.mastery.backend.service;

import org.springframework.web.multipart.MultipartFile;

public interface AwsService {

  String uploadImage(MultipartFile multipartFile, Long carId);
}
