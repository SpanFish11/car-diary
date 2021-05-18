package com.godeltech.mastery.backend.domain.entity;

import static javax.persistence.GenerationType.IDENTITY;

import com.fasterxml.jackson.annotation.JsonBackReference;
import java.io.Serial;
import java.io.Serializable;
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
public class Guarantee implements Serializable {

  @Serial private static final long serialVersionUID = -4588320465063222818L;

  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;

  @Column(name = "start_date", nullable = false)
  private LocalDate start;

  @Column(name = "end_date", nullable = false)
  private LocalDate end;

  @Column(name = "extended", nullable = false)
  private Boolean extended;

  @OneToOne
  @JoinColumn(name = "car_id", referencedColumnName = "id")
  @JsonBackReference
  private Car car;
}
