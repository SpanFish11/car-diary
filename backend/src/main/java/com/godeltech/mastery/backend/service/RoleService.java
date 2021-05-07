package com.godeltech.mastery.backend.service;

import com.godeltech.mastery.backend.domain.entity.Role;

public interface RoleService {

  Role getRoleByRoleName(String name);
}
