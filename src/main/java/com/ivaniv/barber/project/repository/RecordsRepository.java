package com.ivaniv.barber.project.repository;

import com.ivaniv.barber.project.entity.Records;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecordsRepository extends JpaRepository<Records, Integer> {
}
