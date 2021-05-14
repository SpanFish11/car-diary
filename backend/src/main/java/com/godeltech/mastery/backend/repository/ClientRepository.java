package com.godeltech.mastery.backend.repository;

import com.godeltech.mastery.backend.domain.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {

  Boolean existsByEmailIgnoreCase(String email);

  Optional<Client> getByEmailIgnoreCase(String email);
}
