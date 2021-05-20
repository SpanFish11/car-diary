package com.godeltech.mastery.backend.domain.entity;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.EAGER;
import static javax.persistence.GenerationType.IDENTITY;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.io.Serial;
import java.io.Serializable;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder(toBuilder = true)
@Table(name = "m_clients")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "cars")
public class Client implements Serializable {

  @Serial private static final long serialVersionUID = 7304392248566928742L;

  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;

  @Column(name = "firstname", nullable = false)
  private String firstName;

  @Column(name = "lastname", nullable = false)
  private String lastName;

  @Column(name = "email", nullable = false)
  private String email;

  @Column(name = "password", nullable = false)
  private String password;

  @OneToMany(mappedBy = "client")
  @JsonManagedReference
  private Set<Car> cars;

  @ManyToMany(fetch = EAGER, cascade = ALL)
  @JoinTable(
      name = "l_clients_roles",
      joinColumns = {
        @JoinColumn(name = "client_id", referencedColumnName = "id", nullable = false)
      },
      inverseJoinColumns = {
        @JoinColumn(name = "role_id", referencedColumnName = "id", nullable = false)
      })
  private Set<Role> roles;
}
