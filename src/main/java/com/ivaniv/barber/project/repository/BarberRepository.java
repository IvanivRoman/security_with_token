package com.ivaniv.barber.project.repository;

import com.ivaniv.barber.project.entity.Barber;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BarberRepository extends JpaRepository<Barber, Integer> {
    Optional<Barber> findByEmail(String email);
}
