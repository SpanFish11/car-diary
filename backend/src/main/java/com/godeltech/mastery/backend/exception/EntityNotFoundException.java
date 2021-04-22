package com.godeltech.mastery.backend.exception;

import java.io.Serial;

import static java.lang.String.format;

public class EntityNotFoundException extends RuntimeException {

  @Serial private static final long serialVersionUID = -9006277830732698145L;

  public EntityNotFoundException() {}

  public EntityNotFoundException(final String message) {
    super(message);
  }

  public EntityNotFoundException(final String entity, final Long id) {
    super(format("Could not find any %s with the ID %d.", entity, id));
  }

  public EntityNotFoundException(final String message, final Throwable cause) {
    super(message, cause);
  }

  public EntityNotFoundException(final Throwable cause) {
    super(cause);
  }

  public EntityNotFoundException(
      final String message,
      final Throwable cause,
      final boolean enableSuppression,
      final boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
