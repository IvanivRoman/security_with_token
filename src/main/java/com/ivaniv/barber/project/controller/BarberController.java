package com.ivaniv.barber.project.controller;

import com.ivaniv.barber.project.config.FileUpload;
import com.ivaniv.barber.project.entity.Barber;
import com.ivaniv.barber.project.repository.BarberRepository;
import com.ivaniv.barber.project.service.BarberService;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;


@Controller
@RequestMapping("/barber-shop")
public class BarberController {

    @Autowired
    private BarberRepository barberRepository;
    @Autowired
    private BarberService barberService;

    @GetMapping("/barbers")
    public String barbers(Model model) {
        Iterable<Barber> barbers = barberService.getBarbers();
        model.addAttribute("barbers", barbers);
        return "barber/barbers";
    }

    @GetMapping("/barbers/add")
    public String addBarberPage(Model model) {
        return "barber/barber-add";
    }

    @PostMapping("/barbers/add")
    public String addBarber(@RequestParam String name, @RequestParam String email, @RequestParam String password,
                            @RequestParam("image") MultipartFile multipartFile, Model model) throws IOException {
        Barber barber = new Barber();
        barber.setName(name);
        barber.setEmail(email);
        barber.setPassword(password);

        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        barber.setPhotos(fileName);
        Barber res = barberService.createBarber(barber);
        String uploadDir = "barber-photos/" + res.getId();
        FileUpload.saveFile(uploadDir, fileName, multipartFile);

        return "redirect:/barber-shop/barbers";
    }

    @GetMapping("/barbers/{id}")
    public String barberDetails(@PathVariable(value = "id") int id,  Model model) {
        if(!barberRepository.existsById(id))
            return "redirect:/";

//        Optional<Barber> barber = barberRepository.findById(id);
        Barber barber = barberService.getBarber(id);
//        List<Barber> res = new ArrayList<>();
//        barber.ifPresent(res::add);
        model.addAttribute("barber", barber);
        return "barber/barber-detail";
    }

    @GetMapping("/barbers/{id}/edit")
    public String barberEdit(@PathVariable(value = "id") int id,  Model model) {
        if(!barberRepository.existsById(id))
            return "redirect:/";

        Barber barber = barberService.getBarber(id);
        model.addAttribute("barber", barber);
        return "barber/barber-edit";
    }

    @PostMapping("/barbers/{id}/edit")
    public String barberUpdate(@PathVariable(value = "id") int id, @RequestParam String name, @RequestParam String email,
                               @RequestParam String aboutMe, @RequestParam("image") MultipartFile multipartFile, Model model) throws IOException {
        Barber barber = barberService.getBarber(id);
        barber.setName(name);
        barber.setEmail(email);
        barber.setAboutMe(aboutMe);
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        barber.setPhotos(fileName);
        Barber res = barberService.createBarber(barber);
        String uploadDir = "barber-photos/" + res.getId();
        FileUtils.deleteDirectory(new File(uploadDir));
        FileUpload.saveFile(uploadDir, fileName, multipartFile);
        return "redirect:/barbers";
    }

    @PostMapping("/barbers/{id}/delete")
    public String barberDelete(@PathVariable(value = "id") int id, Model model) throws IOException {
        String uploadDir = "barber-photos/" + barberService.getBarber(id).getId();
        FileUtils.deleteDirectory(new File(uploadDir));
        barberService.deleteBarber(id);;
        return "redirect:/barbers";
    }
}
