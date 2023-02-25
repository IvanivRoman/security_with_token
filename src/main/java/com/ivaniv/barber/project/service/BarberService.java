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
import java.util.List;

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
        return barberRepository.findAll();
    }

    public Barber getBarber(Integer id) {
        return barberRepository.findById(id).orElseThrow();
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

}

