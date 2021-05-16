package com.godeltech.mastery.backend.domain.entity

import java.io.Serializable
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType.LAZY
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType.IDENTITY
import javax.persistence.Id
import javax.persistence.ManyToMany
import javax.persistence.Table

@Entity
@Table(name = "m_roles")
data class Role(

    @Id
    @GeneratedValue(strategy = IDENTITY)
    val id: Long? = null,

    @Column(name = "role_name")
    var roleName: String? = null,

    @ManyToMany(mappedBy = "roles", fetch = LAZY)
    var clients: List<Client>? = null
) : Serializable
