package com.godeltech.mastery.backend.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = PasswordFieldsValueMatchValidator.class)
@Target({TYPE, FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
public @interface PasswordValueMatch {

  String message() default "Fields values don't match!";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

  String field();

  String fieldMatch();

  @Target({TYPE})
  @Retention(RUNTIME)
  @interface List {
    PasswordValueMatch[] value();
  }
}
