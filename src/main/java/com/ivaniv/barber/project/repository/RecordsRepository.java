package com.ivaniv.barber.project.repository;

import com.ivaniv.barber.project.entity.Barber;
import com.ivaniv.barber.project.entity.Records;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Date;
import java.util.List;

public interface RecordsRepository extends JpaRepository<Records, Integer> {
    void deleteAllByBarber(Barber barber);

    List<Records> getRecordsByBarberAndDate(Barber barber, Date date);

    List<Records> getRecordsByDate(Date date);

}
