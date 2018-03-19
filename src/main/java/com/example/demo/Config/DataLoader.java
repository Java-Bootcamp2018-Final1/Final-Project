package com.example.demo.Config;

import com.example.demo.Controllers.MethodsService;
import com.example.demo.Models.*;
import com.example.demo.Repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

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



    @Override
    public void run(String... strings) throws Exception {
        System.out.println("Loading data ...");
        AppRole role = new AppRole();
        role.setRoleName("STUDENT");
        appRoleRepository.save(role);

        role = new AppRole();
        role.setRoleName("ADMIN");
        appRoleRepository.save(role);

        // USERS
        // Students
        Student student = new Student();
        // Student 1
        student.setFirstName("John");
        student.setLastName("Doe");
        student.setUserEmail("g1@gmail.com");
        student.setAppPassword("password1");
        methodsService.registerStudent(student);
        // Student 2
        student = new Student();
        student.setFirstName("Jacob");
        student.setLastName("Smith");
        student.setUserEmail("g2@gmail.com");
        student.setAppPassword("password2");
        methodsService.registerStudent(student);
        // Student 3
        student = new Student();
        student.setFirstName("Jane");
        student.setLastName("Pane");
        student.setUserEmail("g3@gmail.com");
        student.setAppPassword("password3");
        methodsService.registerStudent(student);
        // Student 4
        student = new Student();
        student.setFirstName("Mary");
        student.setLastName("Kerry");
        student.setUserEmail("g4@gmail.com");
        student.setAppPassword("password4");
        methodsService.registerStudent(student);
        // Student 5
        student = new Student();
        student.setFirstName("William");
        student.setLastName("Williamson");
        student.setUserEmail("g5@gmail.com");
        student.setAppPassword("password5");
        methodsService.registerStudent(student);
        // Student 6
        student = new Student();
        student.setFirstName("Jill");
        student.setLastName("Hill");
        student.setUserEmail("g6@gmail.com");
        student.setAppPassword("password6");
        methodsService.registerStudent(student);

        // Admins
        AppUser user = new AppUser();
        // Admin 1
        user.setAppUsername("Brandon@bmail.com");
        user.setAppPassword("password");
        methodsService.addAdministrator(user);

        // Admin 1
        user = new AppUser();
        user.setAppUsername("Dave@dmail.com");
        user.setAppPassword("password7");
        methodsService.addAdministrator(user);





    }
}