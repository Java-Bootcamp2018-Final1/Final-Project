package com.example.demo.Controllers;

import com.example.demo.Models.Student;
import com.example.demo.Repositories.ProgrammeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RiriController {

    @Autowired
    ProgrammeRepository programmeRepository;

    @RequestMapping("/login")
    public String showLog(Model model) {

        return "login";
    }


    @RequestMapping("/")
    public String showIndex(Model model) {

        model.addAttribute("stu", new Student());
        model.addAttribute("showprogs", programmeRepository.findAll());
        return "index";
    }
}