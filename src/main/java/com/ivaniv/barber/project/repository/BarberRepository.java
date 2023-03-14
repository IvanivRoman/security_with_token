package com.ivaniv.barber.project.repository;

import com.ivaniv.barber.project.entity.Barber;
import com.ivaniv.barber.project.entity.Records;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BarberRepository extends JpaRepository<Barber, Integer> {
    Optional<Barber> findByEmail(String email);

}
