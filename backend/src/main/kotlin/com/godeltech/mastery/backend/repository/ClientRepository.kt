package com.godeltech.mastery.backend.repository

import com.godeltech.mastery.backend.domain.entity.Client
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface ClientRepository : JpaRepository<Client, Long> {

    fun existsByEmailIgnoreCase(email: String): Boolean

    fun getByEmailIgnoreCase(email: String): Optional<Client>
}