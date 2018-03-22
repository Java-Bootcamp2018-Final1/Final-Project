package com.example.demo.Controllers;

import com.example.demo.Config.MailService;
import com.example.demo.Models.AppUser;
import com.example.demo.Models.Programme;
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
       Iterable<Programme> p = programmeRepository.findAll();
       Iterable<Programme> p2 = programmeRepository.findAll();
       for (Programme st :p){
           model.addAttribute("kk", st.getQualifiedStudents());
           for(Student sst : st.getQualifiedStudents()){
               System.out.println(sst.getUserEmail());
           }
       }

       for (Programme sst :p2){
           model.addAttribute("kk", sst.getApprovedStudents());
           for(Student ssst : sst.getApprovedStudents()){
               System.out.println(ssst.getUserEmail());
               try {
                   System.out.println(ssst.getUserEmail());
                   mailService.sendEmail(ssst.getUserEmail());
                   return "Email Sent!";
               } catch (Exception ex) {
                   return "Error in sending email: " + ex;
               }
           }
       }
      /* Student students = appUser.getStudent();
     Iterable<Student> student = studentRepository.findAllByApprovedProgram(students);

       for (Student stu : student) {

           try {
                System.out.println(stu.getUserEmail());
                mailService.sendEmail(stu.getUserEmail());
                return "Email Sent!";
            } catch (Exception ex) {
                return "Error in sending email: " + ex;
            }
        }*/


        return "login";

    }


}
