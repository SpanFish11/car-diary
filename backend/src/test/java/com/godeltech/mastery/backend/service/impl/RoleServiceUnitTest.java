package com.godeltech.mastery.backend.service.impl;

import static java.lang.String.format;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.only;

import com.godeltech.mastery.backend.domain.entity.Role;
import com.godeltech.mastery.backend.exception.EntityNotFoundException;
import com.godeltech.mastery.backend.repository.RoleRepository;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class RoleServiceUnitTest {

  @Mock RoleRepository roleRepository;

  @InjectMocks RoleServiceImpl roleService;

  @Test
  void getRoleByCorrectRoleName() {
    final var name = "USER";
    final Role expected = new Role(1L, name, Set.of());

    given(roleRepository.findRoleByRoleName(name)).willReturn(Optional.of(expected));

    final var actual = roleService.getRoleByRoleName(name);
    assertThat(actual, is(expected));

    then(roleRepository).should(only()).findRoleByRoleName(eq(name));
  }

  @Test
  void getRoleByIncorrectRoleName() {
    final var incorrectRoleName = "SUPER_USER";
    final var message = format("Role is not found with name = %s", incorrectRoleName);
    final EntityNotFoundException excepted = new EntityNotFoundException(message);

    given(roleRepository.findRoleByRoleName(incorrectRoleName)).willThrow(excepted);

    final var actual =
        assertThrows(
            EntityNotFoundException.class, () -> roleService.getRoleByRoleName(incorrectRoleName));
    assertThat(actual.getMessage(), is(message));

    then(roleRepository).should(only()).findRoleByRoleName(eq(incorrectRoleName));
  }
}
