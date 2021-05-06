package com.godeltech.mastery.backend.service.impl;

import com.godeltech.mastery.backend.domain.entity.Role;
import com.godeltech.mastery.backend.exception.EntityNotFoundException;
import com.godeltech.mastery.backend.repository.RoleRepository;
import com.godeltech.mastery.backend.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

  private final RoleRepository roleRepository;

  @Override
  public Role getRoleByRoleName(final String name) {
    return roleRepository
        .findRoleByRoleName(name)
        .orElseThrow(() -> new EntityNotFoundException("role", name));
  }
}
