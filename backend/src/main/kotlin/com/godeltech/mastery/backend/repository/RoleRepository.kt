package com.godeltech.mastery.backend.repository

import com.godeltech.mastery.backend.domain.entity.Role
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface RoleRepository : JpaRepository<Role, Long> {

    fun findRoleByRoleName(roleName: String): Optional<Role>
}