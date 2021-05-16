package com.godeltech.mastery.backend.domain.entity

import com.godeltech.mastery.backend.domain.dto.responce.EquipmentDto
import java.io.Serializable
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType.IDENTITY
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "m_equipments")
data class Equipment(

    @Id
    @GeneratedValue(strategy = IDENTITY)
    val id: Long? = null,

    @Column(name = "name")
    var name: String? = null,

    @Column(name = "engine_type")
    var engineType: String? = null,

    @Column(name = "transmission_type")
    var transmissionType: String? = null,

    @Column(name = "engine_size")
    var engineSize: Double? = null,

    @Column(name = "power")
    var horsePower: Int? = null,
) : Serializable {

    fun toDto() = EquipmentDto(
        id = id,
        name = name,
        engineType = engineType,
        transmissionType = transmissionType,
        engineSize = engineSize,
        horsePower = horsePower
    )
}
