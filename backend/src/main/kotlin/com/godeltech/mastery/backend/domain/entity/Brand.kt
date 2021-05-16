package com.godeltech.mastery.backend.domain.entity

import com.godeltech.mastery.backend.domain.dto.responce.BrandDto
import java.io.Serializable
import javax.persistence.CascadeType.ALL
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType.LAZY
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType.IDENTITY
import javax.persistence.Id
import javax.persistence.OneToMany
import javax.persistence.Table

@Entity
@Table(name = "m_brands")
data class Brand(

    @Id
    @GeneratedValue(strategy = IDENTITY)
    val id: Long? = null,

    @Column(name = "name")
    var name: String? = null,

    @OneToMany(mappedBy = "brand", fetch = LAZY, cascade = [ALL])
    var models: List<Model>? = null
) : Serializable {

    fun toDto() = BrandDto(id = id, name = name)
}