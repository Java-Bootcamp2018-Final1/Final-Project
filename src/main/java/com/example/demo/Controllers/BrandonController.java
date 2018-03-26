package com.example.demo.Controllers;


import com.example.demo.Models.AppUser;
import com.example.demo.Models.Programme;
import com.example.demo.Models.Student;
import com.example.demo.Repositories.AppRoleRepository;
import com.example.demo.Repositories.AppUserRepository;
import com.example.demo.Repositories.ProgrammeRepository;
import com.example.demo.Repositories.StudentRepository;

import it.ozimov.springboot.mail.service.exception.CannotSendEmailException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/register")
    public String showReg(Model model){

        model.addAttribute("NewStudent", new Student());
        return "register";
    }

    @PostMapping("/register")
    public String addNewUser(@Valid @ModelAttribute("NewStudent") Student student, BindingResult result, Model model)
    {

        if(result.hasErrors())
        {
            System.out.println(result.toString());
            return "register";
        }
        System.out.println(student.getUserEmail());
            methodsService.registerStudent(student);
        for (Programme programme:programmeRepository.findAll()) {
            methodsService.qualifyForProgram(student,programme);
        }
            return "redirect:/login";

    }


@GetMapping("/showprograms")
    public String showListedPrograms(Model model){

        model.addAttribute("showprogs", programmeRepository.findAll());
        return "showprograms";
}
    // This Method returns a model containing a list of all students who have applied for a program
    @RequestMapping("/listapplied/{id}")
    public String showAppliedStudentsForProgram(@PathVariable("id") long id, Model model){

        Programme programme = programmeRepository.findOne(id);
        model.addAttribute("program",programme);
        model.addAttribute("appliedList",programme.getAppliedStudents());
       // model.addAttribute("approvedlist", programme.getApprovedStudents());
        for(Student stu: programme.getApprovedStudents()){

        }

        return "listapplied";
    }

    // This Method returns a model containg a list of all programs
    @GetMapping("/listprograms")
    public String showListPrograms(Model model){
        model.addAttribute("programlist",programmeRepository.findAll());
        return "listprograms";
    }

    // This Method returns a model containing a list of Programs Suggested for the User
    @RequestMapping("/suggestedprograms")
    public String suggestPrograms(Model model, Authentication authentication){
        AppUser appUser = appUserRepository.findAppUserByAppUsername(authentication.getName());
        Student student = appUser.getStudent();
        model.addAttribute("listprograms", student.getQualifiedProgram());

        /*for(Programme stu : student.getQualifiedProgram()){
            model.addAttribute("listprograms", stu.getProgramName());
            System.out.println(stu.getProgramName());
        }*/
        return "suggested";
    }

    // This Method allows a user to accept an admission offer for a program
    @RequestMapping("/acceptstudent/{id}")
    public String acceptProgramOffer(Model model,@PathVariable("id") long id, Authentication authentication){

        String s = "You accepted the program ";
        model.addAttribute("accept", s);
        AppUser appUser = appUserRepository.findAppUserByAppUsername(authentication.getName());
        model.addAttribute("name", appUser);
        Student student = appUser.getStudent();
        Programme programme =programmeRepository.findOne(id);
        methodsService.acceptProgram(student,programme);
        return "showconfirmaion";
    }

    // This Method allows a user to apply for a program
    @RequestMapping("/applyforaprogram/{id}")
    public String applyForAProgram(@PathVariable("id")long id,Authentication authentication){
        AppUser appUser = appUserRepository.findAppUserByAppUsername(authentication.getName());
        Student student = appUser.getStudent();
        Programme programme =programmeRepository.findOne(id);
        methodsService.applyForProgramme(student,programme);
        return "redirect:/";
    }

  /*  //abe approve students

    public String approvestudents(@PathVariable("id")long id,Authentication authentication) throws CannotSendEmailException, IOException, URISyntaxException {
        AppUser appUser = appUserRepository.findAppUserByAppUsername(authentication.getName());
        Student student = appUser.getStudent();
        Programme programme =programmeRepository.findOne(id);
        methodsService.approveStudent(student,programme);
        return "redirect:/";
    }*/
// student accept approval

   //abe accept
    public String acceptStudent(@PathVariable("id")long id,Authentication authentication, Model model){
        String s = "You accepted the program ";
        model.addAttribute("accept", s);
        AppUser appUser = appUserRepository.findAppUserByAppUsername(authentication.getName());
        Student student = appUser.getStudent();
        Programme programme =programmeRepository.findOne(id);
        model.addAttribute("name", appUser);
        methodsService.acceptProgram(student,programme);
        return "showconfirmaion";
    }
    // This Method returns a model showing a user programs that they have been approved for
    @RequestMapping("/showapprove")
    public String showApprovedPrograms(Model model, Authentication authentication){
        AppUser appUser = appUserRepository.findAppUserByAppUsername(authentication.getName());
        Student student = appUser.getStudent();
        model.addAttribute("listprograms", student.getApprovedProgram());

        return "showapprove";
    }

    // show students who accept a program

    @RequestMapping("/showacccept")
    public String listacceptstudents(Model model, Authentication authentication){
        AppUser appUser = appUserRepository.findAppUserByAppUsername(authentication.getName());
        Student student = appUser.getStudent();

        for(Programme pro : student.getAcceptedProgram()){
            model.addAttribute("acceptlist", pro.getAcceptedStudents());
        }
        return "showaccept";
    }

    // This method allows an Admin to add another Admin
    @GetMapping("/registeradmin")
    public String addAnAdmin(Model model){
        model.addAttribute("newAdmin", new AppUser());
        return "registeradmin";
    }

    //This method processes and saves an added Admin
    @PostMapping("/registeradmin")
    public String processAddedAdmin(@Valid @ModelAttribute("newAdmin") AppUser appUser, BindingResult result){
        methodsService.addAdministrator(appUser);
        return "redirect:/";
    }

    //This method allows an Admin to Approve a student for a program
    @RequestMapping("/approvestudent/{id1}/{id2}")
    public String approveStudentForProgram(@PathVariable("id1") long id1,@PathVariable("id2")long id2) throws CannotSendEmailException, IOException, URISyntaxException {
        Student student = studentRepository.findOne(id1);
        Programme programme = programmeRepository.findOne(id2);
        methodsService.approveStudent(student,programme);
        return "redirect:/showprograms";
    }
    @GetMapping("/listprogramstoaccept")
    public String showListProgramstoaccept(Model model){
        model.addAttribute("programlist",programmeRepository.findAll());
        return "listprogramaccepted";
    }

    // This Method returns a model showing students who have accepted admission to a program
    @RequestMapping("/listprogramaccepted/{id}")
    public String showAcceptedStudentsForProgram(@PathVariable("id") long id,Model model){
        Programme programme =programmeRepository.findOne(id);
        model.addAttribute("listaccepted",programme.getAcceptedStudents());
        return "showacceptedforprogram";
    }

}
