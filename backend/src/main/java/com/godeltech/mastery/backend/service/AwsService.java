package com.godeltech.mastery.backend.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public interface AwsService {

  String uploadImage(MultipartFile multipartFile) throws IOException;
}
