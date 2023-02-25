package com.ivaniv.barber.project.service;

import com.ivaniv.barber.project.repository.ServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ServiceService {
    @Autowired
    private final ServiceRepository serviceRepository;

    public void createService(String serviceName, int price){
        com.ivaniv.barber.project.entity.Service service = new com.ivaniv.barber.project.entity.Service();
        service.setService_name(serviceName);
        service.setPrice(price);
        serviceRepository.save(service);
    }

    public com.ivaniv.barber.project.entity.Service createService(com.ivaniv.barber.project.entity.Service service) {
        return serviceRepository.save(service);
    }

    public com.ivaniv.barber.project.entity.Service updateService(com.ivaniv.barber.project.entity.Service service) {
        com.ivaniv.barber.project.entity.Service entity = serviceRepository.findById(service.getId()).orElseThrow();

        if(service.getService_name() != null) {
            entity.setService_name(service.getService_name());
        }

        if(service.getPrice() != 0) {
            entity.setPrice(service.getPrice());
        }

        return serviceRepository.save(entity);
    }

    public List<com.ivaniv.barber.project.entity.Service> getServices() {
        return serviceRepository.findAll();
    }

    public com.ivaniv.barber.project.entity.Service getService(Integer id) {
        return serviceRepository.findById(id).orElseThrow();
    }

    public void deleteService(Integer id) {
        com.ivaniv.barber.project.entity.Service entity = serviceRepository.findById(id).orElseThrow();
        serviceRepository.delete(entity);
    }
}
