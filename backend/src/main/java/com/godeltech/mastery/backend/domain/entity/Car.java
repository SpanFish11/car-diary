package com.godeltech.mastery.backend.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serial;
import java.io.Serializable;

import static javax.persistence.FetchType.EAGER;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Table(name = "m_cars")
public class Car implements Serializable {

  @Serial private static final long serialVersionUID = -5977596408904041847L;

  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;

  @ManyToOne(fetch = EAGER)
  @JoinColumn(name = "model_id", nullable = false)
  private Model model;

  @Column(name = "year", nullable = false)
  private Integer year;

  @Column(name = "photo_url")
  private String photoUrl;

  @Column(name = "vin_code", nullable = false, length = 17, unique = true)
  private String vin;

  @Column(name = "mileage")
  private Integer mileage;
}
