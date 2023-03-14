package com.ivaniv.barber.project.controller;

import com.ivaniv.barber.project.entity.Role;
import com.ivaniv.barber.project.service.BarberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.NoSuchElementException;

@Controller
public class HomeController {

    @Autowired
    private BarberService barberService;

    @GetMapping(value = {  "/barber-shop/p" })
    public String home(Model model) {
        try {
            barberService.getBarber(1);
        } catch (NoSuchElementException e) {
            return "barber/barber-add";
        }
        return"home";
}

}
