package com.ivaniv.barber.project.controller;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.ivaniv.barber.project.auth.AuthenticationService;
import com.ivaniv.barber.project.auth.RegisterRequest;
import com.ivaniv.barber.project.config.CloudImages;
import com.ivaniv.barber.project.entity.Barber;
import com.ivaniv.barber.project.entity.Role;
import com.ivaniv.barber.project.service.BarberService;
import jakarta.validation.Valid;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Map;


@Controller
@RequestMapping("/barber-shop")
public class BarberController {
    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private BarberService barberService;

    @GetMapping("/p/barbers")
    public String barbers(Model model) {
        Iterable<Barber> barbers = barberService.getBarbers();
        model.addAttribute("barbers", barbers);
        return "barber/barbers";
    }

    @GetMapping("/adm/barbers")
    public String barbersAdm(Model model) {
        Iterable<Barber> barbers = barberService.getBarbers();
        model.addAttribute("barbers", barbers);
        return "barber/barbers-adm";
    }

    @GetMapping("/adm/barbers/add")
    public String addBarberPage(Model model) {
        return "barber/barber-add";
    }

//    @PostMapping("/barbers/add")
//    public String addBarber(@RequestParam @Valid String name, @RequestParam @Valid String email,
//                            @RequestParam @Valid String password,
//                            Model model, BindingResult result) {
//
//        if (result.hasErrors()) {
//            return "barber/barber-add";
//        }
//
//        RegisterRequest request = new RegisterRequest();
//        request.setName(name);
//        request.setEmail(email);
//        request.setPassword(password);
//        authenticationService.register(request);
//
//        return "redirect:/barber-shop/barbers-adm";
//    }

    @PostMapping("/adm/barbers/add")
    public String addBarber(@ModelAttribute("barber") @Valid Barber barber,
                            BindingResult result) {

        if (result.hasErrors()) {
            return "barber/barber-add";
        }

        RegisterRequest request = new RegisterRequest();
        request.setName(barber.getName());
        request.setEmail(barber.getEmail());
        request.setPassword(barber.getPassword());
        authenticationService.register(request);

        return "redirect:/barber-shop/adm/barbers";
    }

    @GetMapping("/b/barbers/{id}")
    public String barberDetails(@PathVariable(value = "id") int id, Model model) {
        Barber barber = barberService.getBarber(id);
        model.addAttribute("barber", barber);
        model.addAttribute("role", barber.getRole().name());
        return "barber/barber-detail";
    }

    @GetMapping("/b/barbers/{id}/edit")
    public String barberEdit(@PathVariable(value = "id") int id, Model model) {

        Barber barber = barberService.getBarber(id);
        model.addAttribute("barber", barber);
        model.addAttribute("role", barber.getRole().name());
        return "barber/barber-edit";
    }

    @PostMapping("/b/barbers/{id}/edit")
    public String barberUpdate(@PathVariable(value = "id") int id, @RequestParam String name, @RequestParam String email,
                               @RequestParam String aboutMe, @RequestParam("image") MultipartFile multipartFile, Model model) throws IOException {
        Barber barber = barberService.getBarber(id);
        barber.setName(name);
        barber.setEmail(email);
        barber.setAboutMe(aboutMe);

        barberService.createBarber(barber);

        if (multipartFile.getSize() != 0) {
            String fileName = multipartFile.getOriginalFilename();
            File file = new File("D:/project/project/" + fileName);

            // Save the file using transferTo() method
            multipartFile.transferTo(file);

            Cloudinary cloudinary = CloudImages.getInstance().getCloudinary();
            Map uploadResult = cloudinary.uploader().upload(file, ObjectUtils.emptyMap());
            barber.setPhotos((String) uploadResult.get("url"));
            barberService.updateBarber(barber);
            file.delete();
        }
        if (barber.getRole() == Role.USER) {
            return "redirect:/barber-shop/b/" + id;
        } else return "redirect:/barber-shop/adm";
    }

    @PostMapping("/adm/barbers/{id}/delete")
    public String barberDelete(@PathVariable(value = "id") int id, Model model) throws IOException {
        String uploadDir = "barber-photos/" + barberService.getBarber(id).getId();
        FileUtils.deleteDirectory(new File(uploadDir));
        barberService.deleteBarber(id);
        ;
        return "redirect:/barber-shop/adm";
    }
}
