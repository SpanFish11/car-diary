package com.godeltech.mastery.backend.domain.entity;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

import com.fasterxml.jackson.annotation.JsonBackReference;
import java.io.Serial;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

  @Column(name = "name", nullable = false, unique = true)
  private String name;

  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "brand_id", nullable = false)
  @JsonBackReference
  private Brand brand;
}
