package com.godeltech.mastery.backend.domain.entity

import java.io.Serializable
import java.time.LocalDate
import javax.persistence.CascadeType.ALL
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType.LAZY
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType.IDENTITY
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.OneToMany
import javax.persistence.Table

@Entity
@Table(name = "m_service_records")
data class ServiceOperationRecord(

    @Id
    @GeneratedValue(strategy = IDENTITY)
    val id: Long? = null,

    @Column(name = "operation_number")
    var serviceOperationNumber: String? = null,

    @Column(name = "date")
    var date: LocalDate? = null,

    @Column(name = "mileage")
    var mileage: String? = null,

    @OneToMany(fetch = LAZY, cascade = [ALL])
    @JoinColumn(name = "service_operation_record_id")
    var serviceWorks: List<ServiceWork>? = null,

    @OneToMany(fetch = LAZY, cascade = [ALL])
    @JoinColumn(name = "service_operation_record_id")
    var changeableParts: List<ChangeablePart>? = null,

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "car_id")
    var car: Car? = null
) : Serializable
