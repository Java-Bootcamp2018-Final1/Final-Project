package com.example.demo.Controllers;

import com.example.demo.Models.*;
import com.example.demo.Repositories.*;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import it.ozimov.springboot.mail.model.Email;
import it.ozimov.springboot.mail.model.ImageType;
import it.ozimov.springboot.mail.model.defaultimpl.DefaultEmail;
import it.ozimov.springboot.mail.model.defaultimpl.DefaultInlinePicture;
import it.ozimov.springboot.mail.service.EmailService;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import it.ozimov.springboot.mail.model.Email;
import it.ozimov.springboot.mail.model.InlinePicture;
import it.ozimov.springboot.mail.model.defaultimpl.DefaultEmail;
import it.ozimov.springboot.mail.service.EmailService;
import it.ozimov.springboot.mail.service.exception.CannotSendEmailException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.internet.InternetAddress;
import java.io.File;
import java.time.LocalDateTime;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.Map;

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

    @Autowired
    public EmailService emailService;

    LocalDateTime localDateTime;

    // Creates and saves a new user as a student
    public void registerStudent(Student student){
        studentRepository.save(student);
        if(student.getAppUser() == null) {
            AppUser newUser = new AppUser();
            newUser.setAppUsername(student.getUserEmail());
            newUser.setAppPassword(student.getAppPassword());
            newUser.setStudent(student);
            appUserRepository.save(newUser);
            AppRole r = appRoleRepository.findAppRoleByRoleName("STUDENT");
            newUser.addRole(r);
            appUserRepository.save(newUser);
            student.setAppUser(newUser);
            studentRepository.save(student);
        }else{
            AppUser appUser = student.getAppUser();
            appUser.setAppUsername(student.getUserEmail());
            appUser.setAppPassword(student.getAppPassword());
            appUserRepository.save(appUser);
        }
    }

    // Admin only method for adding other admins
    public void addAdministrator(AppUser newUser){
        appUserRepository.save(newUser);
        AppRole r = appRoleRepository.findAppRoleByRoleName("ADMIN");
        newUser.addRole(r);
        appUserRepository.save(newUser);
    }

    // Student Only Method: Apply for program
    public void applyForProgramme(Student student, Programme programme){
        programme.addApplied(student);
        programme.setNumberApplicants(programme.getNumberApplicants()+1);
        programmeRepository.save(programme);
    }

    // Admin only method: for approving students
    public void approveStudent(Student student,Programme programme) throws CannotSendEmailException, IOException, URISyntaxException {
        programme.addApproved(student);
        programmeRepository.save(programme);
        sendAdmissionEmailWithThymeleaf(student,programme);
    }

    // Student Only Method: for accepting a program
    public void acceptProgram(Student student, Programme programme){
        programme.addAccepted(student);
        programme.setNumberAccepted(programme.getNumberAccepted()+1);
        programmeRepository.save(programme);
    }

    // Checks to see if A Student Qualifies for a program
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
            programme.addQualified(student);
            programmeRepository.save(programme);
        }
        System.out.println(student.getFirstName() + " " + student.getLastName() + " number for " + programme.getProgramName() + " is " + y );

    }

    // Checks the qualifications for all students for all programs
    public void qualifyStudents(StudentRepository studentRepository1,ProgrammeRepository programmeRepository1){
        for (Programme programme:programmeRepository1.findAll()) {
            for (Student student:studentRepository1.findAll()) {
                qualifyForProgram(student,programme);
            }
        }
    }

    // Sends an Email when a student is admitted
    // Sends an Email(no template) when a student is admitted
    public void sendAddmissionEmailWithoutTemplating(Student student) throws UnsupportedEncodingException {
        final Email email = DefaultEmail.builder()
                .from(new InternetAddress("cicero@mala-tempora.currunt", "Marco Tullio Cicerone "))
                .to(Lists.newArrayList(new InternetAddress("titus@de-rerum.natura", "Pomponius Attĭcus")))
                .subject("Laelius de amicitia")
                .body("Firmamentum autem stabilitatis constantiaeque eius, quam in amicitia quaerimus, fides est.")
                .encoding("UTF-8").build();

        emailService.send(email);
    }

    // Sends an Email(template) when a student is admitted
    public void sendAdmissionEmailWithThymeleaf(Student student, Programme programme) throws IOException, CannotSendEmailException, URISyntaxException {
        InlinePicture inlinePicture = createCatInlinePicture();
        final Email email = DefaultEmail.builder()
                .from(new InternetAddress("bhcodingpractice@gmail.com","Brandon"))
                .to(Lists.newArrayList(new InternetAddress(student.getUserEmail(),student.getFirstName() +" "+student.getLastName())))
                .subject("Admission to " + programme.getProgramName())
                .body("")//this will be overridden by the template, anyway
                .encoding("UTF-8").build();

        String template = "/emailTemplate";

        Map<String, Object> modelObject = ImmutableMap.of(
                "fname", student.getFirstName(),
                "lname", student.getLastName(),
                "pname", programme.getProgramName()
        );

        emailService.send(email, template, modelObject, inlinePicture);
    }

    private InlinePicture createCatInlinePicture() throws URISyntaxException {
        ClassLoader classLoader = getClass().getClassLoader();
        File pictureFile = new File(classLoader.getResource("static" + File.separator + "registertemplate" + File.separator + "img2" + File.separator +"caticon.jpeg").toURI());
        Preconditions.checkState(pictureFile.exists(), "There is not picture %s", pictureFile.getName());

        return DefaultInlinePicture.builder()
                .file(pictureFile)
                .imageType(ImageType.JPG)
                .templateName("caticon.jpeg").build();
    }

    // Start of the individual value checkers
    public int checkEnglish(Integer student,Integer program){
        int x =0;
        if((program != null) && (student !=null)){
            if(student>=program){
                x=1;
            }
        }
        return x;
    }

    public int checkEmployment(Integer student,Integer program){
        int x =0;
        if((program != null) && (student !=null)){
            if(student<=program){
                x=1;
            }
        }
        return x;
    }

    public int checkComputer(Integer student,Integer program){
        int x =0;
        if((program != null) && (student !=null)){
            if(student>=program){
                x=1;
            }
        }
        return x;
    }

    public int checkIT(Integer student,Integer program){
        int x =0;
        if((program != null) && (student !=null)){
            if(student>=program){
                x=1;
            }
        }
        return x;
    }

    public int checkEducation(Integer student,Integer program){
        int x =0;
        if((program != null) && (student !=null)){
            if(student>=program){
                x=1;
            }
        }
        return x;
    }

    public int checkMajor(Boolean student,Boolean program){
        int x =0;
        if((program != null) && (student !=null)){
            if(student==program){
                x=1;
            }
        }
        return x;
    }
    public int checkUSLegal(Boolean student,Boolean program){
        int x =0;
        if((program != null) && (student !=null)){
            if(student==program){
                x=1;
            }
        }
        return x;
    }

    public int checkOOPUnderstanding(Integer student,Integer program){
        int x =0;
        if((program != null) && (student !=null)){
            if(student>=program){
                x=1;
            }
        }
        return x;
    }
    public int checkOOLExperience(Integer student,Integer program){
        int x =0;
        if((program != null) && (student !=null)){
            if(student>=program){
                x=1;
            }
        }
        return x;
    }
    public int checkGraduation(Integer student,Integer program){
        int x =0;
        if((program != null) && (student !=null)){
            if(student>=program){
                x=1;
            }
        }
        return x;
    }
    public int checkEarning(Integer student,Integer program){
        int x =0;
        if((program != null) && (student !=null)){
            if(student<=program){
                x=1;
            }
        }
        return x;
    }
    // End of the Individual value checkers
}
