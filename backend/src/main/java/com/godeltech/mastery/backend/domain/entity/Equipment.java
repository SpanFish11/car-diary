package com.godeltech.mastery.backend.domain.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serial;
import java.io.Serializable;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@Entity
@Table(name = "m_equipments")
public class Equipment implements Serializable {

  @Serial private static final long serialVersionUID = 3251234076822020165L;

  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;

  @Column(name = "name", nullable = false, unique = true)
  private String name;

  @Column(name = "engine_type", nullable = false)
  private String engineType;

  @Column(name = "transmission_type", nullable = false)
  private String transmissionType;

  @Column(name = "engine_size", nullable = false)
  private Double engineSize;

  @Column(name = "power", nullable = false)
  private Integer horsePower;
}
