package com.ivaniv.barber.project.controller;

import com.ivaniv.barber.project.config.EmailService;
import com.ivaniv.barber.project.entity.Barber;
import com.ivaniv.barber.project.entity.Client;
import com.ivaniv.barber.project.entity.Records;
import com.ivaniv.barber.project.entity.Service;
import com.ivaniv.barber.project.repository.RecordsRepository;
import com.ivaniv.barber.project.service.BarberService;
import com.ivaniv.barber.project.service.ClientService;
import com.ivaniv.barber.project.service.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/barber-shop/p")
public class OrderController {

    @Autowired
    private ClientService clientService;
    @Autowired
    private BarberService barberService;
    @Autowired
    private RecordsRepository recordRepository;
    @Autowired
    private ServiceService serviceService;
    @Autowired
    private EmailService mailSender;

    private Records record = new Records();

    @GetMapping("/order")
    public String chooseBarberAndServices(Model model) {
        Iterable<Barber> barbers = barberService.getBarbers();
        model.addAttribute("barbers", barbers);
        Iterable<Service> services = serviceService.getServices();
        model.addAttribute("services", services);

        return "order/order";
    }

    @PostMapping("/order")
    public String chooseBarberAndServicesPost(@RequestParam int id, Model model, @RequestParam int... serv) {
        List<Service> services = new ArrayList<>();
        for (int i = 0; i < serv.length; i++) {
            services.add(serviceService.getService(serv[i]));
        }

        record.setBarber(barberService.getBarber(id));
        record.setServices(services);

        return "redirect:/barber-shop/p/date/" + id;
    }

    @GetMapping("/date/{id}")
    public String chooseDateAndTime(@PathVariable int id, Model model) {
        model.addAttribute("availableRecords", barberService.getAvailableRecords(id));
        model.addAttribute("barber", barberService.getBarber(id));
        return "order/order-date";
    }

    @PostMapping("/date/{id}")
    public String chooseDateAndTimePost(@RequestParam("date") String date,
                                        @RequestParam("time") String time, Model model) throws ParseException {
        record.setDate(Date.valueOf(date));
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        java.util.Date utilTime = format.parse(time);
        Time sqlTime = new Time(utilTime.getTime());
        record.setHour(sqlTime);
        return "redirect:/barber-shop/p/client";
    }

    @GetMapping("/client")
    public String getPersonalData() {
        return "order/order-client";
    }

//    @PostMapping("/client")
//    public String getPersonDataPost(@RequestParam String name, @RequestParam String phone, @RequestParam String email) {
//        Client client;
//        if (clientService.getClient(email) == null) {
//            client = new Client();
//            client.setName(name);
//            client.setEmail(email);
//            client.setPhone(phone);
//            record.setClient(client);
//            clientService.createClient(client);
//        } else {
//            client = clientService.getClient(email);
//            record.setClient(client);
//        }
//
//        recordRepository.save(record);
//
//        return "home";
//    }

    @PostMapping("/client")
    public String processClientForm(@ModelAttribute("client") Client client) {
        if (clientService.getClient(client.getEmail()) == null) {
            clientService.createClient(client);
            record .setClient(client);
        } else {
            client = clientService.getClient(client.getEmail());
            record .setClient(client);
        }

        recordRepository.save(record);
        return "home";
    }

}