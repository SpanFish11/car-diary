package com.godeltech.mastery.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.S3Utilities;

import static software.amazon.awssdk.auth.credentials.AwsBasicCredentials.create;
import static software.amazon.awssdk.regions.Region.of;

@Configuration
public class ApplicationConfig {

  @Bean
  public S3AsyncClient s3AsyncClient(final S3ClientConfigurationProperties properties) {
    return S3AsyncClient.builder()
        .region(of(properties.getRegion()))
        .credentialsProvider(
            () -> create(properties.getAccessKeyId(), properties.getSecretAccessKey()))
        .build();
  }

  @Bean
  public S3Utilities s3Utilities(final S3ClientConfigurationProperties properties) {
    return S3Utilities.builder().region(of(properties.getRegion())).build();
  }
}
