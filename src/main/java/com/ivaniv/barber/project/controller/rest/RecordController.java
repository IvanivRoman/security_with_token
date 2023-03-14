package com.ivaniv.barber.project.controller.rest;

import com.ivaniv.barber.project.entity.Barber;
import com.ivaniv.barber.project.entity.Records;
import com.ivaniv.barber.project.repository.BarberRepository;
import com.ivaniv.barber.project.repository.RecordsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("barber-shop/")
public class RecordController {
    @Autowired
    private final RecordsRepository recordsRepository;
    @Autowired
    private final BarberRepository barberRepository;

    @GetMapping("/records")
    public ResponseEntity<List<Records>> getRecords(@RequestParam Integer barberId, @RequestParam Date date) {
        Barber barber = barberRepository.findById(barberId).orElse(null);
        if (barber == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        List<Records> records = recordsRepository.getRecordsByBarberAndDate(barber, date);
        return new ResponseEntity<>(records, HttpStatus.OK);
    }

    @GetMapping("/recordsDate")
    public ResponseEntity<List<Records>> getRecordsByDate(@RequestParam Date date) {
        List<Records> records = recordsRepository.getRecordsByDate(date);
        return new ResponseEntity<>(records, HttpStatus.OK);
    }

    @GetMapping("/AllRecords")
    public ResponseEntity<List<Records>> getAllRecords() {
        List<Records> allRecords = recordsRepository.findAll();
        return new ResponseEntity<>(allRecords, HttpStatus.OK);
    }

}
