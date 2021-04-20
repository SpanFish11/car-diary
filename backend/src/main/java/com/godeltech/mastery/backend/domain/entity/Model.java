package com.godeltech.mastery.backend.domain.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Data
public class Model {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;

  private String name;

  private Brand brand;
}
