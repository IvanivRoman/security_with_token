package com.ivaniv.barber.project.controller;

import com.ivaniv.barber.project.auth.AuthenticationRequest;
import com.ivaniv.barber.project.auth.AuthenticationService;
import com.ivaniv.barber.project.entity.Barber;
import com.ivaniv.barber.project.entity.Role;
import com.ivaniv.barber.project.service.BarberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/barber-shop")
public class PersonalCabinetController {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    BarberService barberService;

    @GetMapping("/p/login")
    public String loginPage() {
        return "cabinet/login";
    }

    @PostMapping("/p/login")
    public String login(@RequestParam String email, @RequestParam String password, Model model) {
        authenticationService.authenticate(new AuthenticationRequest(email, password));
        Barber barber = barberService.getBarber(email);
        model.addAttribute("barber", barber);
        if (barber.getRole() == Role.ADMIN) {
            return "redirect:/barber-shop/adm";
        }
        return "redirect:/barber-shop/b/" + barber.getId();
    }
    @GetMapping("/b/{id}")
    public String barberCabinet(@PathVariable int id, Model model) {
        Barber barber = barberService.getBarber(id);
        model.addAttribute("barber", barber);
        return "cabinet/cabinet";
    }

    @GetMapping("/adm")
    public String adminCabinet() {
        return "cabinet/admin";
    }
}
