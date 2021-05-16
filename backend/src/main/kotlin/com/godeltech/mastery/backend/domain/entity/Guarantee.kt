package com.godeltech.mastery.backend.domain.entity

import java.io.Serializable
import java.time.LocalDate
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType.LAZY
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType.IDENTITY
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.OneToOne
import javax.persistence.Table

@Entity
@Table(name = "m_guarantee")
data class Guarantee(

    @Id
    @GeneratedValue(strategy = IDENTITY)
    val id: Long? = null,

    @Column(name = "start_date")
    var start: LocalDate? = null,

    @Column(name = "end_date")
    var end: LocalDate? = null,

    @Column(name = "extended")
    var extended: Boolean? = null,

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "car_id", referencedColumnName = "id")
    var car: Car? = null
) : Serializable
