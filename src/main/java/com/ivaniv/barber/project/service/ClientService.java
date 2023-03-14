package com.ivaniv.barber.project.service;
import com.ivaniv.barber.project.entity.Client;
import com.ivaniv.barber.project.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientService {
    @Autowired
    private final ClientRepository clientRepository;

    public void createClient(String name, String phone, String email){
        Client client = new Client();
        client.setName(name);
        client.setPhone(phone);
        client.setEmail(email);
        clientRepository.save(client);
    }

    public Client createClient(Client client) {
        return clientRepository.save(client);
    }

    public Client updateClient(Client client) {
        Client entity = clientRepository.findById(client.getId()).orElseThrow();

        if(client.getName() != null) {
            entity.setName(client.getName());
        }

        if(client.getPhone() != null) {
            entity.setPhone(client.getPhone());
        }

        if(client.getEmail() != null) {
            entity.setEmail(client.getEmail());
        }

        return clientRepository.save(entity);
    }

    public List<Client> getClients() {
        return clientRepository.findAll();
    }

    public Client getClient(Integer id) {
        return clientRepository.findById(id).orElseThrow();
    }
    public Client getClient(String email) {
        return clientRepository.findClientByEmail(email);
    }

    public void deleteClient(Integer id) {
        Client entity = clientRepository.findById(id).orElseThrow();
        clientRepository.delete(entity);
    }
}
