package com.godeltech.mastery.backend.domain.entity;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

import com.fasterxml.jackson.annotation.JsonBackReference;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "m_guarantee")
@EqualsAndHashCode(exclude = "car")
public class Guarantee {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;

  @Column(name = "start_date", nullable = false)
  private LocalDate start;

  @Column(name = "end_date", nullable = false)
  private LocalDate end;

  @Column(name = "mileage", nullable = false)
  private Integer mileage;

  @Column(name = "extended", nullable = false)
  private Boolean extended;

  @OneToOne(fetch = LAZY)
  @JoinColumn(name = "car_id", referencedColumnName = "id")
  @JsonBackReference
  private Car car;
}
