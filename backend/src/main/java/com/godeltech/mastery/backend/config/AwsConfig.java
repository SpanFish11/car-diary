package com.godeltech.mastery.backend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.services.s3.S3Client;

import static software.amazon.awssdk.auth.credentials.AwsBasicCredentials.create;
import static software.amazon.awssdk.regions.Region.of;

@Configuration
@ConfigurationProperties("aws.s3")
public class AwsConfig {

  @Value(value = "${aws.s3.accessKeyId}")
  private String accessKeyId;

  @Value("${aws.s3.secretAccessKey}")
  private String secretAccessKey;

  @Value("${aws.s3.region}")
  private String region;

  @Bean(destroyMethod = "close")
  public S3Client s3Client() {
    return S3Client.builder()
        .region(of(region))
        .credentialsProvider(() -> create(accessKeyId, secretAccessKey))
        .build();
  }
}
