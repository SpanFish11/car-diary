package com.godeltech.mastery.backend.domain.entity

import com.godeltech.mastery.backend.domain.dto.responce.ModelDto
import java.io.Serializable
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
@Table(name = "m_models")
data class Model(

    @Id
    @GeneratedValue(strategy = IDENTITY)
    val id: Long? = null,

    @Column(name = "name")
    var name: String? = null,

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "brand_id")
    var brand: Brand? = null
) : Serializable {

    fun toDto() = ModelDto(id = id, name = name)
}