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

import static javax.persistence.FetchType.EAGER;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Data
@Table(name = "m_service_work")
public class ServiceWork implements Serializable {

  @Serial private static final long serialVersionUID = 6558826171336259255L;

  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "price", nullable = false)
  private BigDecimal price;

  @Column(name = "guarantee", nullable = false)
  private Boolean guarantee;

  @ManyToOne(fetch = EAGER)
  @JoinColumn(name = "service_operation_record_id", referencedColumnName = "id")
  @JsonBackReference
  private ServiceOperationRecord serviceOperationRecord;
}
