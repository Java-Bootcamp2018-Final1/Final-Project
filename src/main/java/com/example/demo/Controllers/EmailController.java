package com.example.demo.Controllers;

import com.example.demo.Config.MailService;
import com.example.demo.Models.AppUser;
import com.example.demo.Models.Student;
import com.example.demo.Repositories.AppUserRepository;
import com.example.demo.Repositories.ProgrammeRepository;
import com.example.demo.Repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class EmailController {
    @Autowired
    private JavaMailSender sender;
    @Autowired
    MailService mailService;
    @Autowired
    AppUserRepository appUserRepository;
   @Autowired
    StudentRepository studentRepository;
   @Autowired
    ProgrammeRepository programmeRepository;
    //send email for all approved students
   @RequestMapping("/approve")
    public String Email(Model model , Authentication auth) {
       AppUser appUser = new AppUser();
       Student students = appUser.getStudent();
     Iterable<Student> student = studentRepository.findAllByApprovedProgram(students);

       for (Student stu : student) {

           try {
                System.out.println(stu.getUserEmail());
                mailService.sendEmail(stu.getUserEmail());
                return "Email Sent!";
            } catch (Exception ex) {
                return "Error in sending email: " + ex;
            }
        }


        return "";

    }


}
