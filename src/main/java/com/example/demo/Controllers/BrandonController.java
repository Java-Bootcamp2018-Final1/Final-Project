package com.example.demo.Controllers;

import com.example.demo.Models.*;
import com.example.demo.Repositories.*;
import com.example.demo.Config.*;
import com.example.demo.Controllers.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class BrandonController {
    @Autowired
    AppRoleRepository appRoleRepository;

    @Autowired
    AppUserRepository appUserRepository;
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    ProgrammeRepository programmeRepository;
    @Autowired
    MethodsService methodsService;

    @PostMapping("/register")
    public String addNewUser(@Valid @ModelAttribute("NewStudent") Student student, BindingResult result, Model model)
    {

        if(result.hasErrors())
        {
            System.out.println(result.toString());
            return "registration";
        }
        else{
            methodsService.registerStudent(student);

            /*if(newUser.getUserType().equals("applicant")) {
                //Create a new ordinary user
                model.addAttribute(newUser.getAppUsername() + " created");
                appUserRepository.save(newUser);
                AppRole r = appRoleRepository.findAppRoleByRoleName("APPLICANT");
                newUser.addRole(r);
                appUserRepository.save(newUser);
                siteApplicants = new SiteApplicants();
                siteApplicants.addCredentials(newUser);
                siteApplicantsRepository.save(siteApplicants);
            }else if(newUser.getUserType().equals("employer")){
                model.addAttribute(newUser.getAppUsername() + " created");
                appUserRepository.save(newUser);
                AppRole r = appRoleRepository.findAppRoleByRoleName("EMPLOYER");
                newUser.addRole(r);
                appUserRepository.save(newUser);
            }else if(newUser.getUserType().equals("recruiter")){
                model.addAttribute(newUser.getAppUsername() + " created");
                appUserRepository.save(newUser);
                AppRole r = appRoleRepository.findAppRoleByRoleName("RECRUITER");
                newUser.addRole(r);
                appUserRepository.save(newUser);
            }*/
            return "redirect:/";
        }
    }
}
