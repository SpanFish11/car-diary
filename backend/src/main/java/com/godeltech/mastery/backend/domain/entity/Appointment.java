package com.godeltech.mastery.backend.domain.entity;

import static javax.persistence.FetchType.EAGER;
import static javax.persistence.GenerationType.IDENTITY;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.godeltech.mastery.backend.domain.entity.maintenance.Maintenance;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnTransformer;

@Entity
@Data
@Table(name = "m_appointment")
@NoArgsConstructor
@AllArgsConstructor
public class Appointment implements Serializable {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;

  @Column(name = "repairment", nullable = false)
  private Boolean repairment;

  @OneToOne
  @JoinColumn(name = "maintenance_id", referencedColumnName = "id")
  @JsonBackReference
  private Maintenance regularService;

  @ManyToOne(fetch = EAGER)
  @JoinColumn(name = "car_id")
  @JsonBackReference
  private Car car;

  @Column(name = "description", nullable = false)
  private String description;

  @Column(name = "date", nullable = false)
  private LocalDate date;

  @Enumerated(EnumType.STRING)
  @ColumnTransformer(read = "UPPER(status)")
  @Column(name = "status", nullable = false)
  private AppointmentStatus status;
}
