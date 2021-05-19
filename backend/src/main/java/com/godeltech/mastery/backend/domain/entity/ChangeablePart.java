package com.godeltech.mastery.backend.domain.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Data
@Table(name = "m_changable_parts")
public class ChangeablePart implements Serializable {

  @Serial private static final long serialVersionUID = -2507350357873574849L;

  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;

  @Column(name = "replaced", nullable = false)
  private Boolean replaced;

  @Column(name = "model", nullable = false)
  private String model;

  @Column(name = "price", nullable = false)
  private BigDecimal price;

  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "service_operation_record_id", referencedColumnName = "id")
  @JsonBackReference
  private ServiceOperationRecord serviceOperationRecord;
}
