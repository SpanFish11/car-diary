package com.godeltech.mastery.backend.domain.entity;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
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

  @OneToMany(fetch = LAZY, cascade = ALL)
  @JoinColumn(name = "service_operation_record_id")
  @JsonManagedReference
  private List<ServiceWork> serviceWorks;

  @OneToMany(fetch = LAZY, cascade = ALL)
  @JoinColumn(name = "service_operation_record_id")
  @JsonManagedReference
  private List<ChangeablePart> changableParts;

  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "car_id")
  @JsonBackReference
  private Car car;
}
