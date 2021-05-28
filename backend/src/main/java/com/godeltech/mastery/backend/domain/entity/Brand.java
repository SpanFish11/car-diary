package com.godeltech.mastery.backend.domain.entity;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.io.Serial;
import java.io.Serializable;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "m_brands")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "models")
public class Brand implements Serializable {

  @Serial private static final long serialVersionUID = -7441863235551341799L;

  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;

  @Column(name = "name", nullable = false, unique = true)
  private String name;

  @OneToMany(mappedBy = "brand", fetch = LAZY)
  @JsonManagedReference
  private Set<Model> models;
}
