package com.godeltech.mastery.backend.util;

import static java.lang.String.valueOf;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.util.FileCopyUtils.copyToString;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.godeltech.mastery.backend.domain.dto.request.Filter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.LinkedMultiValueMap;

@RequiredArgsConstructor
public class TestUtils {

  private final ResourceLoader resourceLoader;
  private final ObjectMapper objectMapper;

  @Setter private String path;

  public String replaceAllTokens(final String path, final String... tokenValues) {
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

  public LinkedMultiValueMap<String, String> toParams(
      final Integer page, final Integer size, final Filter filter) throws JsonProcessingException {
    final var json = objectMapper.writeValueAsString(filter);
    final TypeReference<Map<String, String>> typeRef = new TypeReference<>() {};
    final Map<String, String> params = objectMapper.readValue(json, typeRef);
    final var requestParams = new LinkedMultiValueMap<String, String>();
    params.forEach(requestParams::add);
    requestParams.add("page", valueOf(page));
    requestParams.add("size", valueOf(size));
    return requestParams;
  }

  public String toJSONObject(final String pathname) throws IOException {
    return objectMapper.writeValueAsString(
        objectMapper.readValue(new File(pathname), JSONObject.class));
  }

  public String objectToJSON(final Object obj) throws JsonProcessingException {
    return objectMapper.writeValueAsString(obj);
  }

  public String toJSONArray(final String pathname) throws IOException {
    return objectMapper.writeValueAsString(
        objectMapper.readValue(new File(pathname), JSONArray.class));
  }

  private String asString(final Resource resource) {
    try (final var reader = new InputStreamReader(resource.getInputStream(), UTF_8)) {
      return copyToString(reader);
    } catch (final IOException e) {
      throw new UncheckedIOException(e);
    }
  }
}
