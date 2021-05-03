package com.godeltech.mastery.backend.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.S3Utilities;

import static software.amazon.awssdk.auth.credentials.AwsBasicCredentials.create;
import static software.amazon.awssdk.regions.Region.of;

@Slf4j
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

  @Bean
  public JavaMailSenderImpl javaMailSender(final MailSenderConfigurationProperties properties) {
    final var mailSender = new JavaMailSenderImpl();
    mailSender.setHost(properties.getHost());
    mailSender.setPort(properties.getPort());
    mailSender.setUsername(properties.getUserName());
    mailSender.setPassword(properties.getPassword());

    final var props = mailSender.getJavaMailProperties();
    props.put("mail.transport.protocol", "smtps");
    props.put("mail.smtp.auth", "true");
    props.put("mail.smtp.starttls.enable", "true");
    props.put("mail.debug", "true");
    return mailSender;
  }

  @Bean(name = "applicationEventMulticaster")
  public ApplicationEventMulticaster simpleApplicationEventMulticaster(
      final BeanFactory beanFactory, final AsyncTaskExecutor asyncTaskExecutor) {
    final var eventMulticaster = new SimpleApplicationEventMulticaster(beanFactory);
    eventMulticaster.setTaskExecutor(asyncTaskExecutor);
    eventMulticaster.setErrorHandler(t -> log.error("Error calling ApplicationEventListener", t));
    return eventMulticaster;
  }
}
