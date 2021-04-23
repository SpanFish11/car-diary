package com.godeltech.mastery.backend.domain.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
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

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Data
@Table(name = "m_models")
@NoArgsConstructor
@AllArgsConstructor
public class Model implements Serializable {

  @Serial private static final long serialVersionUID = -2104981859549860841L;

  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;

  @Column(name ="name", nullable = false)
  private String name;

  @ManyToOne
  @JoinColumn(name = "brand_id", nullable = false)
  @JsonBackReference
  private Brand brand;
}