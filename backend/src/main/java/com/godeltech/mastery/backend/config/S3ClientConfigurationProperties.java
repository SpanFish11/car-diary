package com.godeltech.mastery.backend.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class S3ClientConfigurationProperties {

  @Value(value = "${aws.s3.images.accessKeyId}")
  private String accessKeyId;

  @Value(value = "${aws.s3.images.secretAccessKey}")
  private String secretAccessKey;

  @Value(value = "${aws.s3.images.region}")
  private String region;
}
