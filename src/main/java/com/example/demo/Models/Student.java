package com.example.demo.Models;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotEmpty
    private String appPassword;

    @NotEmpty
    @Column(unique = true)
    private String userEmail;

    private String firstName;

    private String lastName;

    @CreationTimestamp
    Timestamp createdAt;

    private Integer englishLang;
    // 1= LOW, 2 = MEDIUM, 3 = HIGH

    private Integer employment;
    // 1= UNEMPLOYED, 2 = UNDEREMPLOYED, 3 = FULLY EMPLOYED

    private Integer computerSkill;
    // 1= LOW, 2 = MEDIUM, 3 = HIGH

    private Integer itInterest;
    // 1= LOW, 2 = MEDIUM, 3 = HIGH

    private Integer Education;
    // 1= NO HS DIPLOMA/GED, 2 = HS DIPLOMA/GED, 3 = SOME COLLEGE, 4 = B.A./B.S, 5 = POSTGRADUATE EXPERIENCE
    private Boolean majorCS;

    private Boolean legalUS;

    private Integer oopUnderstanding;
    // 1= LOW, 2 = MEDIUM, 3 = HIGH

    private Integer oolExperience;
    // 1= LOW, 2 = MEDIUM, 3 = HIGH

    private Integer gradYear;

    private Integer currentEarning;

    // Getters and Setters

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAppPassword() {
        return appPassword;
    }

    public void setAppPassword(String appPassword) {
        this.appPassword = appPassword;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    // Criteria Getters and Setters

    public Integer getEnglishLang() {
        return englishLang;
    }

    public void setEnglishLang(Integer englishLang) {
        this.englishLang = englishLang;
    }


    // CONSTRUCTORS

    public Student() {
        this.qualifiedProgram = new ArrayList<>();
        this.approvedProgram = new ArrayList<>();
        this.acceptedProgram = new ArrayList<>();
    }

    // CONNECTIONS
    // Connection to AppUser
    @OneToOne
    @JoinColumn
    private AppUser appUser;

    public AppUser getAppUser() {
        return appUser;
    }

    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }

    // Connections to Student

    public Student(List<Programme> qualifiedProgram, List<Programme> approvedProgram, List<Programme> acceptedProgram) {
        this.qualifiedProgram = qualifiedProgram;
        this.approvedProgram = approvedProgram;
        this.acceptedProgram = acceptedProgram;
    }

    // Qualified
    @ManyToMany
    private List<Programme> qualifiedProgram;

    public void addQualified(Programme programme){
        this.qualifiedProgram.add(programme);
    }

    public List<Programme> getQualifiedProgram() {
        return qualifiedProgram;
    }

    public void setQualifiedProgram(List<Programme> qualifiedProgram) {
        this.qualifiedProgram = qualifiedProgram;
    }

    // Approved
    @ManyToMany
    private List<Programme> approvedProgram;

    public void addApproved(Programme programme){
        this.approvedProgram.add(programme);
    }

    public List<Programme> getApprovedProgram() {
        return approvedProgram;
    }

    public void setApprovedProgram(List<Programme> approvedProgram) {
        this.approvedProgram = approvedProgram;
    }

    // Accepted
    @ManyToMany
    private List<Programme> acceptedProgram;

    public void addAccepted(Programme programme){
        this.acceptedProgram.add(programme);
    }

    public List<Programme> getAcceptedProgram() {
        return acceptedProgram;
    }

    public void setAcceptedProgram(List<Programme> acceptedProgram) {
        this.acceptedProgram = acceptedProgram;
    }
}
