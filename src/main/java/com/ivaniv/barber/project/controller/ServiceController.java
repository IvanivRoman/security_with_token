package com.ivaniv.barber.project.controller;

import com.ivaniv.barber.project.entity.Service;
import com.ivaniv.barber.project.repository.RecordsRepository;
import com.ivaniv.barber.project.service.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/barber-shop")
public class ServiceController {

    @Autowired
    private ServiceService serviceService;
    @Autowired
    private RecordsRepository recordRepository;

    @GetMapping("/services")
    public String services(Model model) {
        Iterable<Service> services = serviceService.getServices();
        model.addAttribute("services", services);
        return "service/services";
    }

    @GetMapping("/services/add")
    public String addServicePage(Model model) {
        return "service/service-add";
    }

    @PostMapping("/services/add")
    public String addService(@RequestParam String serviceName, @RequestParam int price, Model model) {
        Service service = new Service();
        service.setService_name(serviceName);
        service.setPrice(price);
        serviceService.createService(service);
        return "redirect:/barber-shop/services";
    }

    @GetMapping("/services/{id}")
    public String serviceDetails(@PathVariable(value = "id") Integer id, Model model) {
        Service service = serviceService.getService(id);
        model.addAttribute("service", service);
        return "service/service-detail";
    }

    @GetMapping("/services/{id}/edit")
    public String serviceEdit(@PathVariable(value = "id") Integer id,  Model model) {
        Service service= serviceService.getService(id);
        model.addAttribute("service", service);
        return "service/service-edit";
    }

    @PostMapping("/services/{id}/edit")
    public String serviceUpdate(@PathVariable(value = "id") Integer id, @RequestParam String service_name,
                                @RequestParam int price, Model model) {
        Service service = serviceService.getService(id);
        service.setService_name(service_name);
        service.setPrice(price);
        serviceService.createService(service);
        return "redirect:/barber-shop/services";
    }

    @PostMapping("/services/{id}/delete")
    public String serviceDelete(@PathVariable(value = "id") Integer id, Model model) {
        serviceService.deleteService(id);
        return "redirect:/barber-shop/services";
    }
}
