package com.godeltech.mastery.backend.repository;

import com.godeltech.mastery.backend.domain.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

  Optional<Role> findRoleByRoleName(String roleName);
}
