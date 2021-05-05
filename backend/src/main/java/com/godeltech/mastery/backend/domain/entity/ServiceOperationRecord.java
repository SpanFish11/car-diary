package com.godeltech.mastery.backend.domain.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import static javax.persistence.FetchType.EAGER;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Data
@Table(name = "m_service_records")
public class ServiceOperationRecord implements Serializable {

  @Serial private static final long serialVersionUID = -8579156536565275392L;

  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;

  @Column(name = "operation_number")
  private String serviceOperationNumber;

  @Column(name = "date", nullable = false)
  private LocalDate date;

  @Column(name = "mileage", nullable = false)
  private Integer mileage;

  @OneToMany(cascade = CascadeType.ALL)
  @JoinColumn(name = "service_operation_record_id")
  @JsonManagedReference
  private List<ServiceWork> serviceWorks;

  @OneToMany(cascade = CascadeType.ALL)
  @JoinColumn(name = "service_operation_record_id")
  @JsonManagedReference
  private List<ChangablePart> changableParts;

  @ManyToOne(fetch = EAGER)
  @JoinColumn(name = "car_id")
  @JsonBackReference
  private Car car;
}
