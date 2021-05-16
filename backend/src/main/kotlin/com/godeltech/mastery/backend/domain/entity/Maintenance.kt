package com.godeltech.mastery.backend.domain.entity

import com.godeltech.mastery.backend.domain.dto.responce.MaintenanceDto
import java.io.Serializable
import javax.persistence.CascadeType.ALL
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType.LAZY
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.OneToMany
import javax.persistence.Table

@Entity
@Table(name = "m_maintenance")
data class Maintenance(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "operation_number")
    var operationNumber: String? = null,

    @Column(name = "mileage")
    var mileage: Int? = null,

    @Column(name = "time_interval")
    var timeInterval: Int? = null,

    @OneToMany(fetch = LAZY, cascade = [ALL])
    @JoinColumn(name = "maintenance_id")
    var operations: List<MaintenanceOperation>? = null,

    @OneToMany(fetch = LAZY, cascade = [ALL])
    @JoinColumn(name = "maintenance_id")
    var details: List<Detail>? = null
) : Serializable {

    fun toDto() =
        MaintenanceDto(
            id = id, operationNumber = operationNumber, mileage = mileage, timeInterval = timeInterval,
            operations = operations?.map(MaintenanceOperation::toDto),
            details = details?.map(Detail::toDto)
        )
}
