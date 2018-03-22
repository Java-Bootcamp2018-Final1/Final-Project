package com.example.demo.Controllers;

import com.example.demo.Models.*;
import com.example.demo.Repositories.*;
import com.example.demo.Config.*;
import com.example.demo.Controllers.*;
import it.ozimov.springboot.mail.service.exception.CannotSendEmailException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.io.IOException;
import java.net.URISyntaxException;

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
            return "redirect:/";
        }
    }

    // This Method returns a model containing a list of all students who have applied for a program
    public String showAppliedStudentsForProgram(@PathVariable("id") long id, Model model){
        Programme programme = programmeRepository.findOne(id);
        model.addAttribute("appliedList", programme.getAppliedStudents());
        return "";
    }

    // This Method returns a model containg a list of all programs
    public String showListPrograms(Model model){
        model.addAttribute("programlist",programmeRepository.findAll());
        return "";
    }

    // This Method returns a model containing a list of Programs Suggested for the User
    public String suggestPrograms(Model model, Authentication authentication){
        AppUser appUser = appUserRepository.findAppUserByAppUsername(authentication.getName());
        Student student = appUser.getStudent();
        model.addAttribute("listprograms", student.getQualifiedProgram());
        return "";
    }

    // This Method allows a user to accept an admission offer for a program
    public String acceptProgramOffer(@PathVariable("id") long id, Authentication authentication){
        AppUser appUser = appUserRepository.findAppUserByAppUsername(authentication.getName());
        Student student = appUser.getStudent();
        Programme programme =programmeRepository.findOne(id);
        methodsService.acceptProgram(student,programme);
        return "";
    }

    // This Method allows a user to apply for a program
    public String applyForAProgram(@PathVariable("id")long id,Authentication authentication){
        AppUser appUser = appUserRepository.findAppUserByAppUsername(authentication.getName());
        Student student = appUser.getStudent();
        Programme programme =programmeRepository.findOne(id);
        methodsService.applyForProgramme(student,programme);
        return "";
    }

    // This Method returns a model showing a user programs that they have been approved for
    public String showApprovedPrograms(Model model, Authentication authentication){
        AppUser appUser = appUserRepository.findAppUserByAppUsername(authentication.getName());
        Student student = appUser.getStudent();
        model.addAttribute("listprograms", student.getApprovedProgram());
        return "";
    }

    // This method allows an Admin to add another Admin
    public String addAnAdmin(Model model){
        model.addAttribute("newAdmin", new AppUser());
        return "";
    }

    //This method processes and saves an added Admin
    public String processAddedAdmin(@Valid @ModelAttribute("newAdmin") AppUser appUser, BindingResult result){
        methodsService.addAdministrator(appUser);
        return "";
    }

    //This method allows an Admin to Approve a student for a program
    public String approveStudentForProgram(@PathVariable("id1") long id1,@PathVariable("id2")long id2) throws CannotSendEmailException, IOException, URISyntaxException {
        Student student = studentRepository.findOne(id1);
        Programme programme = programmeRepository.findOne(id2);
        methodsService.approveStudent(student,programme);
        return "";
    }

    // This Method returns a model showing students who have accepted admission to a program
    public String showAcceptedStudentsForProgram(@PathVariable("id") long id,Model model){
        Programme programme =programmeRepository.findOne(id);
        model.addAttribute(programme.getAcceptedStudents());
        return "";
    }

}
