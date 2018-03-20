package com.example.demo.Controllers;

import com.example.demo.Models.Student;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RiriController {

    @RequestMapping("/")
    public String showReg(Model model){

        model.addAttribute("stu", new Student());
        return "register";
    }

}
