package com.godeltech.mastery.backend.repository;

import com.godeltech.mastery.backend.domain.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {

  Boolean existsByEmailIgnoreCase(String email);
}
