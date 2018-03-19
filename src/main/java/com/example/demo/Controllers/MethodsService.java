package com.example.demo.Controllers;

import com.example.demo.Models.*;
import com.example.demo.Repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MethodsService {
    @Autowired
    AppRoleRepository appRoleRepository;

    @Autowired
    AppUserRepository appUserRepository;
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    ProgrammeRepository programmeRepository;

    public void registerStudent(Student student){
        AppUser newUser= new AppUser();
        newUser.setAppUsername(student.getUserEmail());
        newUser.setAppPassword(student.getAppPassword());
        newUser.setStudent(student);
        appUserRepository.save(newUser);
        AppRole r = appRoleRepository.findAppRoleByRoleName("STUDENT");
        newUser.addRole(r);
        appUserRepository.save(newUser);
        student.setAppUser(newUser);
        studentRepository.save(student);
    }
}
