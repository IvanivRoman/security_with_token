package com.ivaniv.barber.project.repository;

import com.ivaniv.barber.project.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Integer> {
    Client findClientByEmail(String email);
}
