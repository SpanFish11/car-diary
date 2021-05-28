package com.godeltech.mastery.backend.exception;

import static java.lang.String.format;
import static java.util.Objects.requireNonNull;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNSUPPORTED_MEDIA_TYPE;

import com.godeltech.mastery.backend.domain.dto.responce.ExceptionResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

  // 400
  @ExceptionHandler({IllegalArgumentException.class})
  protected ResponseEntity<Object> handleIllegalArgumentException(
      final IllegalArgumentException ex, final WebRequest request) {
    logging(ex, request);
    final var exResponse = new ExceptionResponseDTO(BAD_REQUEST);
    exResponse.setMessage(ex.getMessage());
    return buildResponseEntity(exResponse);
  }

  @ExceptionHandler({BadCredentialsException.class})
  protected ResponseEntity<Object> handleBadCredentialsException(
      final BadCredentialsException ex, final WebRequest request) {
    logging(ex, request);
    final var exResponse = new ExceptionResponseDTO(BAD_REQUEST);
    exResponse.setMessage(ex.getMessage());
    return buildResponseEntity(exResponse);
  }

  @Override
  protected ResponseEntity<Object> handleHttpMessageNotReadable(
      final HttpMessageNotReadableException ex,
      final HttpHeaders headers,
      final HttpStatus status,
      final WebRequest request) {
    final var exResponse = new ExceptionResponseDTO(BAD_REQUEST, ex);
    exResponse.setMessage("Malformed JSON request");
    logging(ex, request);
    return buildResponseEntity(exResponse);
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      final MethodArgumentNotValidException ex,
      final HttpHeaders headers,
      final HttpStatus status,
      final WebRequest request) {
    final var exResponse = new ExceptionResponseDTO(BAD_REQUEST);
    exResponse.setMessage("Validation error");
    exResponse.addDetails(ex.getBindingResult());
    logging(ex, request);
    return buildResponseEntity(exResponse);
  }

  @Override
  protected ResponseEntity<Object> handleNoHandlerFoundException(
      final NoHandlerFoundException ex,
      final HttpHeaders headers,
      final HttpStatus status,
      final WebRequest request) {
    final var exResponse = new ExceptionResponseDTO(BAD_REQUEST);
    exResponse.setMessage(
        format("Could not find the %s method for URL %s", ex.getHttpMethod(), ex.getRequestURL()));
    exResponse.setDebugMessage(ex.getMessage());
    logging(ex, request);
    return buildResponseEntity(exResponse);
  }

  @ExceptionHandler({javax.validation.ConstraintViolationException.class})
  protected ResponseEntity<Object> handleConstraintViolationException(
      final javax.validation.ConstraintViolationException ex) {
    final var message = "Validation error";
    final var exResponse = new ExceptionResponseDTO(BAD_REQUEST);
    exResponse.setMessage(message);
    exResponse.addDetails(ex.getConstraintViolations());
    logging(ex, message);
    return buildResponseEntity(exResponse);
  }

  @ExceptionHandler({MethodArgumentTypeMismatchException.class})
  protected ResponseEntity<Object> handleMethodArgumentTypeMismatch(
      final MethodArgumentTypeMismatchException ex, final WebRequest request) {
    final var exResponse = new ExceptionResponseDTO(BAD_REQUEST);
    exResponse.setMessage(
        format(
            "The parameter '%s' of value '%s' could not be converted to type '%s'",
            ex.getName(), ex.getValue(), requireNonNull(ex.getRequiredType()).getSimpleName()));
    exResponse.setDebugMessage(ex.getMessage());
    logging(ex, request);
    return buildResponseEntity(exResponse);
  }

  // 404
  @ExceptionHandler({EntityNotFoundException.class})
  protected ResponseEntity<Object> handleEntityNotFoundException(
      final Exception ex, final WebRequest request) {
    final var exResponse = new ExceptionResponseDTO(NOT_FOUND);
    exResponse.setMessage(ex.getMessage());
    logging(ex, request);
    return buildResponseEntity(exResponse);
  }

  // 409
  @ExceptionHandler({DataIntegrityViolationException.class})
  protected ResponseEntity<Object> handleDataIntegrityViolation(
      final DataIntegrityViolationException ex, final WebRequest request) {
    logging(ex, request);
    return ex.getCause() instanceof ConstraintViolationException
        ? buildResponseEntity(new ExceptionResponseDTO(CONFLICT, "Database error", ex.getCause()))
        : buildResponseEntity(new ExceptionResponseDTO(INTERNAL_SERVER_ERROR, ex));
  }

  // 415
  @Override
  protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(
      final HttpMediaTypeNotSupportedException ex,
      final HttpHeaders headers,
      final HttpStatus status,
      final WebRequest request) {
    final var builder = new StringBuilder();
    builder.append(ex.getContentType());
    builder.append(" media type is not supported. Supported media types are ");
    ex.getSupportedMediaTypes().forEach(t -> builder.append(t).append(", "));
    final var exResponse =
        new ExceptionResponseDTO(
            UNSUPPORTED_MEDIA_TYPE, builder.substring(0, builder.length() - 2), ex);
    logging(ex, request);
    return buildResponseEntity(exResponse);
  }

  // 500
  @ExceptionHandler({Exception.class})
  protected ResponseEntity<Object> handleError(final Exception ex, final WebRequest request) {
    final var exResponse = new ExceptionResponseDTO(INTERNAL_SERVER_ERROR);
    exResponse.setMessage("Something went wrong");
    exResponse.setDebugMessage(ex.getClass().getName() + " " + ex.getMessage());
    logging(ex, request);
    return buildResponseEntity(exResponse);
  }

  private ResponseEntity<Object> buildResponseEntity(final ExceptionResponseDTO exception) {
    return new ResponseEntity<>(exception, exception.getStatus());
  }

  private void logging(final Exception ex, final WebRequest request) {
    final var servletWebRequest = (ServletWebRequest) request;
    log.error(
        "{} to {}",
        servletWebRequest.getHttpMethod(),
        servletWebRequest.getRequest().getServletPath(),
        ex);
  }

  private void logging(final Exception ex, final String message) {
    log.error("{}; {}", message, ex);
  }
}
