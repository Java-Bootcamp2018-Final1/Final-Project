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
        studentRepository.save(student);
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
    public void addAdministrator(AppUser newUser){
        appUserRepository.save(newUser);
        AppRole r = appRoleRepository.findAppRoleByRoleName("ADMIN");
        newUser.addRole(r);
        appUserRepository.save(newUser);
    }

    public void qualifyForProgram(Student student,Programme programme){
        int y = 0;
        y = y + checkEnglish(student.getEnglishLang(),programme.getEnglishLang());
        y = y + checkEmployment(student.getEmployment(),programme.getEmployment());
        y = y + checkComputer(student.getComputerSkill(),programme.getComputerSkill());
        y = y + checkIT(student.getItInterest(),programme.getItInterest());
        y = y + checkEducation(student.getEducation(),programme.getEducation());
        y = y + checkMajor(student.getMajorCS(),programme.getMajorCS());
        y = y + checkUSLegal(student.getLegalUS(),programme.getLegalUS());
        y = y + checkOOPUnderstanding(student.getOopUnderstanding(),programme.getOopUnderstanding());
        y = y + checkOOLExperience(student.getOolExperience(),programme.getOolExperience());
        y = y + checkGraduation(student.getGradYear(),programme.getGradYear());
        y = y + checkEarning(student.getCurrentEarning(),programme.getCurrentEarning());
        if(y >= 1){
            student.addQualified(programme);
        }
        System.out.println(student.getFirstName() + " " + student.getLastName() + " number for " + programme.getProgramName() + " is " + y );

    }

    public void qualifyStudents(StudentRepository studentRepository1,ProgrammeRepository programmeRepository1){
        for (Student student:studentRepository1.findAll()) {
            for (Programme programme:programmeRepository1.findAll()) {
                qualifyForProgram(student,programme);
            }
        }

    }

    public int checkEnglish(Integer student,Integer program){
        int x =0;
        if(student>=program){
            x=1;
        }
        return x;
    }

    public int checkEmployment(Integer student,Integer program){
        int x =0;
        if(student>=program){
            x=1;
        }
        return x;
    }

    public int checkComputer(Integer student,Integer program){
        int x =0;
        if(student>=program){
            x=1;
        }
        return x;
    }

    public int checkIT(Integer student,Integer program){
        int x =0;
        if(student>=program){
            x=1;
        }
        return x;
    }

    public int checkEducation(Integer student,Integer program){
        int x =0;
        if(student>=program){
            x=1;
        }
        return x;
    }

    public int checkMajor(Boolean student,Boolean program){
        int x =0;
        if(student==program){
            x=1;
        }
        return x;
    }
    public int checkUSLegal(Boolean student,Boolean program){
        int x =0;
        if(student==program){
            x=1;
        }
        return x;
    }

    public int checkOOPUnderstanding(Integer student,Integer program){
        int x =0;
        if(student>=program){
            x=1;
        }
        return x;
    }
    public int checkOOLExperience(Integer student,Integer program){
        int x =0;
        if(student>=program){
            x=1;
        }
        return x;
    }
    public int checkGraduation(Integer student,Integer program){
        int x =0;
        if(student>=program){
            x=1;
        }
        return x;
    }
    public int checkEarning(Integer student,Integer program){
        int x =0;
        if(student<=program){
            x=1;
        }
        return x;
    }
}
