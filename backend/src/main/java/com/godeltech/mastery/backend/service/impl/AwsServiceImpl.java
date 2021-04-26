package com.godeltech.mastery.backend.service.impl;

import com.godeltech.mastery.backend.service.AwsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.exception.SdkException;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.S3Utilities;
import software.amazon.awssdk.services.s3.model.GetUrlRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;

import static java.lang.String.format;
import static java.nio.ByteBuffer.wrap;
import static java.util.UUID.randomUUID;
import static software.amazon.awssdk.core.async.AsyncRequestBody.fromByteBuffer;
import static software.amazon.awssdk.services.s3.model.ObjectCannedACL.PUBLIC_READ;

@Slf4j
@Service
@RequiredArgsConstructor
public class AwsServiceImpl implements AwsService {

  private static final String IMAGE_CONTENT_TYPE = "image/jpeg";

  private final S3AsyncClient s3AsyncClient;
  private final S3Utilities s3Utilities;

  @Value(value = "${aws.s3.images.bucketName}")
  private String bucketName;

  @Value(value = "${aws.s3.images.carFolderName}")
  private String folderName;

  @Override
  public String uploadImage(final MultipartFile multipartFile, final Long carId) {
    final byte[] imageBytes = getBytesFromMultipartFile(multipartFile);
    final var imageUUID = randomUUID().toString();

    s3AsyncClient.putObject(
        createObjectRequest(carId, imageBytes, imageUUID), fromByteBuffer(wrap(imageBytes)));

    return getUrl(carId, imageUUID);
  }

  private String getUrl(final Long carId, final String imageUUID) {
    final var sb = new StringBuilder(s3Utilities.getUrl(createUrlRequest(imageUUID)).toString());
    sb.insert(sb.indexOf(imageUUID), format("%s/%s/", folderName, carId));
    return sb + ".jpg";
  }

  private byte[] getBytesFromMultipartFile(final MultipartFile multipartFile) {
    try {
      return multipartFile.getBytes();
    } catch (final IOException e) {
      log.error("Cant get bytes from multipartFile", e);
      throw SdkException.create("Cant get bytes from multipartFile", e);
    }
  }

  private PutObjectRequest createObjectRequest(
      final Long carId, final byte[] imageBytes, final String imageUUID) {
    return PutObjectRequest.builder()
        .bucket(bucketName)
        .contentType(IMAGE_CONTENT_TYPE)
        .contentLength((long) imageBytes.length)
        .acl(PUBLIC_READ)
        .key(format("%s/%s/%s.jpg", folderName, carId, imageUUID))
        .build();
  }

  private GetUrlRequest createUrlRequest(final String imageUUID) {
    return GetUrlRequest.builder().bucket(bucketName).key(imageUUID).build();
  }
}
