package com.godeltech.mastery.backend.domain.entity.maintenance;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serial;
import java.io.Serializable;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@Entity
@Table(name = "m_maintenance")
public class Maintenance implements Serializable {

  @Serial private static final long serialVersionUID = -461133523434389337L;

  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;

  @Column(name = "operation_number", nullable = false)
  private String operationNumber;

  @Column(name = "mileage", nullable = false)
  private Integer mileage;

  @Column(name = "time_interval", nullable = false)
  private Integer timeInterval;

  @OneToMany(cascade = CascadeType.ALL)
  @JoinColumn(name = "maintenance_id")
  @JsonManagedReference
  private List<MaintenanceOperation> operations;

  @OneToMany(cascade = CascadeType.ALL)
  @JoinColumn(name = "maintenance_id")
  @JsonManagedReference
  private List<Detail> details;
}