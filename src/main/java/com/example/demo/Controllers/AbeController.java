package com.example.demo.Controllers;

import com.example.demo.Models.AppUser;
import com.example.demo.Models.Student;
import com.example.demo.Repositories.AppUserRepository;
import com.example.demo.Repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class AbeController {
    @Autowired
    StudentRepository studentRepository;

    @Autowired
    AppUserRepository appUserRepository;

    @Autowired
    MethodsService methodsService;

    @Autowired
    AppUserRepository userRepository;
@GetMapping("/save")
public String getSavePage(Model model){
    model.addAttribute("stu", new Student());

    return "register";
}
    @RequestMapping("/save")
    public String saveUser(@ModelAttribute("stu") Student student ){

        //String
        methodsService.registerStudent(student);

        return "redirect:/";
    }
   /* @GetMapping("/login")
    public String login(){

    return "login";
    }*/
}
