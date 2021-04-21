package com.godeltech.mastery.backend.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnTransformer;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Car {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(nullable = false)
  private Model model;

  @Enumerated(STRING)
  @ColumnTransformer(read = "UPPER(colour)")
  @Column(nullable = false)
  private Brand brand;

  @Column(nullable = false)
  private LocalDate year;

  private String photoUrl;

  @Column(nullable = false)
  private String vin;

  private Integer mileage;
}
