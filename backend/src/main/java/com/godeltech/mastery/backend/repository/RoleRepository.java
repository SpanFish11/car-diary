package com.godeltech.mastery.backend.repository;

import com.godeltech.mastery.backend.domain.entity.Role;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

  Optional<Role> findRoleByRoleName(String roleName);
}
