package com.godeltech.mastery.backend.domain.entity

import java.io.Serializable
import javax.persistence.CascadeType.ALL
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType.LAZY
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType.IDENTITY
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.JoinTable
import javax.persistence.ManyToMany
import javax.persistence.OneToMany
import javax.persistence.Table

@Entity
@Table(name = "m_clients")
data class Client(

    @Id
    @GeneratedValue(strategy = IDENTITY)
    val id: Long? = null,

    @Column(name = "firstname")
    var firstName: String? = null,

    @Column(name = "lastname")
    var lastName: String? = null,

    @Column(name = "email")
    var email: String? = null,

    @Column(name = "password")
    var password: String? = null,

    @OneToMany(mappedBy = "client")
    var cars: List<Car>? = null,

    @ManyToMany(fetch = LAZY, cascade = [ALL])
    @JoinTable(
        name = "l_clients_roles",
        joinColumns = [JoinColumn(name = "client_id", referencedColumnName = "id", nullable = false)],
        inverseJoinColumns = [JoinColumn(name = "role_id", referencedColumnName = "id", nullable = false)]
    )
    var roles: List<Role>? = null
) : Serializable
