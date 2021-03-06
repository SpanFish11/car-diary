package com.godeltech.mastery.backend.domain.entity;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

import com.fasterxml.jackson.annotation.JsonBackReference;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "m_operations")
public class MaintenanceOperation implements Serializable {

  @Serial private static final long serialVersionUID = -1420233277543272989L;

  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "price", nullable = false)
  private BigDecimal price;

  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "maintenance_id", referencedColumnName = "id")
  @JsonBackReference
  private Maintenance maintenance;
}
