package com.godeltech.mastery.backend.utils;

import static java.lang.String.valueOf;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.util.FileCopyUtils.copyToString;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.godeltech.mastery.backend.domain.dto.request.Filter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.MultiValueMap;

@RequiredArgsConstructor
public class TemplateDoc {

  private final ResourceLoader resourceLoader;
  private final ObjectMapper objectMapper;

  @Setter
  private String path;

  public String replaceAllTokens(final String path, String... tokenValues) {
    var result = readFileToString(path);
    for (int i = 0; i < tokenValues.length; i += 2) {
      result = result.replaceAll("\\$\\{" + tokenValues[i] + "\\}", tokenValues[i + 1]);
    }
    return result;
  }

  public String readFileToString(final String fileName) {
    final var resource = resourceLoader.getResource(path + fileName);
    return asString(resource);
  }

  private String asString(final Resource resource) {
    try (final var reader = new InputStreamReader(resource.getInputStream(), UTF_8)) {
      return copyToString(reader);
    } catch (final IOException e) {
      throw new UncheckedIOException(e);
    }
  }

  public Map<String, String> mapParams(final Integer page, final Integer size,
      final Filter filter) throws JsonProcessingException {
    final var f = objectMapper.writeValueAsString(filter);
    final TypeReference<Map<String, String>> typeRef = new TypeReference<>() {};
    final Map<String, String> params = objectMapper.readValue(f, typeRef);
    params.put("page", valueOf(page));
    params.put("size", valueOf(size));
    return params;
  }
}
