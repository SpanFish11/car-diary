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
@Table(name = "m_service_work")
public class ServiceWork {

  @Id private Long id;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "price", nullable = false)
  private BigDecimal price;

  @Column(name = "guarantee")
  private Boolean guarantee;

  @ManyToOne(fetch = EAGER)
  @JsonBackReference
  private ServiceOperationRecord serviceOperationRecord;
}
