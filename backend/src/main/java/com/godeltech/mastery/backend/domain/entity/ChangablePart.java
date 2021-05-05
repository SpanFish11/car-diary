package com.godeltech.mastery.backend.domain.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;

import static javax.persistence.FetchType.EAGER;

@Entity
@Table(name = "m_changable_parts")
public class ChangablePart {

  @Id private Long id;

  @Column(name = "replaced", nullable = false)
  private Boolean replaced;

  @Column(name = "model", nullable = false)
  private String model;

  @Column(name = "price", nullable = false)
  private BigDecimal price;

  @ManyToOne(fetch = EAGER)
  @JsonBackReference
  private ServiceOperationRecord serviceOperationRecord;
}
