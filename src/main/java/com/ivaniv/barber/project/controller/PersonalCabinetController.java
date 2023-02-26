package com.ivaniv.barber.project.controller;

import com.ivaniv.barber.project.auth.AuthenticationRequest;
import com.ivaniv.barber.project.auth.AuthenticationService;
import com.ivaniv.barber.project.config.LogoutService;
import com.ivaniv.barber.project.entity.Barber;
import com.ivaniv.barber.project.service.BarberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/barber-shop")
public class PersonalCabinetController {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private LogoutService logoutService;

    @Autowired
    BarberService barberService;

    @GetMapping("/login")
    public String loginPage() {
     return "cabinet/login";
    }

    @PostMapping("/login")
    public String personalCabinet(@RequestParam String email, @RequestParam String password, Model model) {
        authenticationService.authenticate(new AuthenticationRequest(email, password));
        Barber barber = barberService.getBarber(email);
        model.addAttribute("barber", barber);
        return "cabinet/cabinet";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response,
                         @AuthenticationPrincipal Authentication authentication) {
        logoutService.logout(request, response, authentication);
        return "home";
    }
}
