package com.godeltech.mastery.backend.domain.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.LocalDate;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@Data
@Entity
@Table(name = "m_guarantee")
public class Guarantee {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;

  @Column(name = "start_date", nullable = false)
  private LocalDate start;

  @Column(name = "end_date", nullable = false)
  private LocalDate end;

  @Column(name = "extended", nullable = false)
  private Boolean extended;

  @OneToOne(fetch = LAZY)
  @JoinColumn(name = "car_id", referencedColumnName = "id")
  @JsonBackReference
  private Car car;
}
