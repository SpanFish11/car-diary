package com.godeltech.mastery.backend.domain.dto.responce;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;

import javax.validation.ConstraintViolation;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;
import static java.lang.String.format;
import static java.time.LocalDateTime.now;

@Data
@JsonInclude(NON_NULL)
public class ExceptionResponseDTO implements Serializable {

  @Serial private static final long serialVersionUID = 5189435993922615101L;

  private LocalDateTime timestamp;
  private HttpStatus status;
  private Integer error;
  private String message;
  private String debugMessage;
  private Map<String, Object> details;

  private ExceptionResponseDTO() {
    timestamp = now();
  }

  public ExceptionResponseDTO(final HttpStatus status) {
    this();
    this.status = status;
    this.error = status.value();
  }

  public ExceptionResponseDTO(final HttpStatus status, final Throwable ex) {
    this();
    this.status = status;
    this.error = status.value();
    this.message = "Unexpected error";
    this.debugMessage = ex.getLocalizedMessage();
  }

  public ExceptionResponseDTO(final HttpStatus status, final String message, final Throwable ex) {
    this();
    this.status = status;
    this.error = status.value();
    this.message = message;
    this.debugMessage = ex.getLocalizedMessage();
  }

  public void addDetails(final BindingResult bindingResult) {
    this.details = makeDetails(bindingResult);
  }

  public void addDetails(final Set<ConstraintViolation<?>> constraintViolations) {
    this.details = makeDetails(constraintViolations);
  }

  private Map<String, Object> makeDetails(final BindingResult bindingResult) {
    final var errors = new LinkedHashMap<String, Object>();
    bindingResult
        .getFieldErrors()
        .forEach(
            e -> {
              if (errors.containsKey(e.getField())) {
                errors.put(
                    e.getField(),
                    format("%s; %s", errors.get(e.getField()), e.getDefaultMessage()));
              } else {
                errors.put(e.getField(), e.getDefaultMessage());
              }
            });
    return errors;
  }

  private Map<String, Object> makeDetails(final Set<ConstraintViolation<?>> constraintViolations) {
    final var errors = new LinkedHashMap<String, Object>();
    constraintViolations.forEach(
        e -> {
          if (errors.containsKey(((PathImpl) e.getPropertyPath()).getLeafNode().asString())) {
            errors.put(
                ((PathImpl) e.getPropertyPath()).getLeafNode().asString(),
                format(
                    "%s; %s",
                    errors.get(((PathImpl) e.getPropertyPath()).getLeafNode().asString()),
                    e.getMessage()));
          } else {
            errors.put(((PathImpl) e.getPropertyPath()).getLeafNode().asString(), e.getMessage());
          }
        });
    return errors;
  }
}
