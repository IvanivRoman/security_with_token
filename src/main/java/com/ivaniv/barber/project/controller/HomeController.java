package com.ivaniv.barber.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/barber-shop")
    public String home(Model model) {
        return "home";
    }

}
