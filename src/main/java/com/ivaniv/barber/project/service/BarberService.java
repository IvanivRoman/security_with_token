package com.ivaniv.barber.project.service;

import com.ivaniv.barber.project.entity.Barber;
import com.ivaniv.barber.project.entity.Records;
import com.ivaniv.barber.project.repository.BarberRepository;
import com.ivaniv.barber.project.repository.RecordsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.sound.midi.Receiver;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class BarberService {

    @Autowired
    private final BarberRepository barberRepository;
    @Autowired
    private final RecordsRepository recordRepository;

    public void createBarber(String name, String email, String password) {
        Barber barber = new Barber();
        barber.setName(name);
        barber.setEmail(email);
        barber.setPassword(password);
        barberRepository.save(barber);
    }

    public Barber createBarber(Barber barber) {
        return barberRepository.save(barber);
    }

    public Barber updateBarber(Barber barber) {
        Barber entity = barberRepository.findById(barber.getId()).orElseThrow();

        if (barber.getName() != null) {
            entity.setName(barber.getName());
        }

        if (barber.getEmail() != null) {
            entity.setEmail(barber.getEmail());
        }

        if (barber.getAboutMe() != null) {
            entity.setAboutMe(barber.getAboutMe());
        }

        if (barber.getPassword() != null) {
            entity.setPassword(barber.getPassword());
        }

        return barberRepository.save(entity);
    }

    public List<Barber> getBarbers() {
        List<Barber> barbers = barberRepository.findAll();
        for (int i = 0; i < barbers.size(); i++) {
            if(barbers.get(i).getId() == 1)
                barbers.remove(i);
        }
        return barbers;
    }

    public Barber getBarber(Integer id) {
        return barberRepository.findById(id).orElseThrow();
    }

    public Barber getBarber(String email) {
        return barberRepository.findByEmail(email).orElseThrow();
    }

    @Transactional
    public void deleteBarber(Integer id) {
        Barber barber = barberRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Barber not found"));

        for (Records record : barber.getRecords()) {
            recordRepository.delete(record);
            System.out.println("delete: " + record.getId());
        }

        barberRepository.delete(barber);
    }

    public Map<Date, List<Time>> getAvailableRecords (Integer barberId) {
        Map<Date, List<Time>> openRecords = new TreeMap<>();
        Barber barber = barberRepository.findById(barberId).orElseThrow();

        // Define the range of dates to check for availability
        LocalDate today = LocalDate.now();
        LocalDate endDate = today.plusDays(14);

        // Iterate through the range of dates (excluding today's date)
        for (LocalDate date = today.plusDays(1); !date.isAfter(endDate); date = date.plusDays(1)) {
            List<Time> availableTimes = getAvailableTimePerDate(barber, Date.valueOf(date));
            openRecords.put(Date.valueOf(date), availableTimes);
        }

        return openRecords;
    }


    public List<Time> getAvailableTimePerDate(Barber barber, Date date) {
        // Define the start and end times of the day
        LocalTime start = LocalTime.of(9, 0);
        LocalTime end = LocalTime.of(17, 0);

        // Create a list of all possible time slots for the day
        List<Time> allTimes = new ArrayList<>();
        for (LocalTime time = start; !time.isAfter(end); time = time.plusMinutes(60)) {
            allTimes.add(Time.valueOf(time));
        }

        // Get the records for the given date
        List<Records> recordsPerDay = recordRepository.getRecordsByBarberAndDate(barber, date);

        // Remove any time slots that are already taken
        for (Records record : recordsPerDay) {
            allTimes.remove(record.getHour());
        }

        return allTimes;
    }

}

