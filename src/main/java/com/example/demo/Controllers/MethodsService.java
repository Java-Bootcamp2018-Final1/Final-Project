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
