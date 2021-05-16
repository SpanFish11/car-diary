package com.godeltech.mastery.backend.domain.entity

import java.io.Serializable
import java.math.BigDecimal
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType.LAZY
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType.IDENTITY
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.OneToMany
import javax.persistence.OneToOne
import javax.persistence.Table

@Entity
@Table(name = "m_cars")
data class Car(

    @Id
    @GeneratedValue(strategy = IDENTITY)
    val id: Long? = null,

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "model_id")
    var model: Model? = null,

    @Column(name = "year")
    var year: Int? = null,

    @Column(name = "photo_url")
    var photoUrl: String? = null,

    @Column(name = "vin_code")
    var vin: String? = null,

    @Column(name = "mileage")
    var mileage: Int? = null,

    @Column(name = "ours")
    var ours: Boolean? = null,

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "client_id")
    var client: Client? = null,

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "equipment_id")
    var equipment: Equipment? = null,

    @Column(name = "price")
    var price: BigDecimal? = null,

    @Column(name = "used")
    var used: Boolean? = null,

    @OneToOne(mappedBy = "car", fetch = LAZY)
    var guarantee: Guarantee? = null,

    @OneToMany(mappedBy = "car", fetch = LAZY)
    var serviceOperations: List<ServiceOperationRecord>? = null
) : Serializable
