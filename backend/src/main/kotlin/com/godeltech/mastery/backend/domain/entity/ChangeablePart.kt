package com.godeltech.mastery.backend.domain.entity

import com.godeltech.mastery.backend.domain.dto.responce.ChangeablePartDto
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
import javax.persistence.Table

@Entity
@Table(name = "m_changable_parts")
data class ChangeablePart(

    @Id
    @GeneratedValue(strategy = IDENTITY)
    val id: Long? = null,

    @Column(name = "replaced")
    var replaced: Boolean? = null,

    @Column(name = "model")
    var model: String? = null,

    @Column(name = "price")
    var price: BigDecimal? = null,

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "service_operation_record_id", referencedColumnName = "id")
    var serviceOperationRecord: ServiceOperationRecord? = null
) : Serializable {

    fun toDto() = ChangeablePartDto(id = id, replaced = replaced, model = model, price = price?.toDouble())
}
