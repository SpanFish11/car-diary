package com.godeltech.mastery.backend.domain.entity;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.EAGER;
import static javax.persistence.GenerationType.IDENTITY;

import com.fasterxml.jackson.annotation.JsonBackReference;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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

  @Serial private static final long serialVersionUID = -8001331328589682452L;

  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;

  @Column(name = "repairment", nullable = false)
  private Boolean repairment;

  @ManyToOne
  @JoinColumn(name = "maintenance_id")
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

  @Enumerated(STRING)
  @ColumnTransformer(read = "UPPER(status)")
  @Column(name = "status", nullable = false)
  private AppointmentStatus status;
}
