package com.godeltech.mastery.backend.service.impl;

import com.godeltech.mastery.backend.service.AwsService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetUrlRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.io.IOException;
import java.net.URL;
import java.nio.ByteBuffer;

@Service
@RequiredArgsConstructor
@Slf4j
public class AwsServiceImpl implements AwsService {

  private final S3Client s3Client;

  @Value(value = "${aws.s3.bucket}")
  private String bucketName;

  @Override
  @Async
  public String uploadImage(MultipartFile multipartFile) throws IOException {
        final byte[] imageBytes = multipartFile.getBytes();
        PutObjectResponse putObjectRequest =
            s3Client.putObject(
                PutObjectRequest.builder().bucket(bucketName).build(),
                RequestBody.fromByteBuffer(ByteBuffer.wrap(imageBytes)));
        final URL reportUrl =
            s3Client.utilities().getUrl(GetUrlRequest.builder().bucket(bucketName).build());
        return reportUrl.toString();
  }
}
