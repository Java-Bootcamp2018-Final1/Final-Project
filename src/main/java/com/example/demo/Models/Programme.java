package com.example.demo.Models;

import javax.persistence.*;
import java.util.List;

@Entity
public class Programme {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String programName;

    private String programDescription;

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


    public Programme() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }

    public String getProgramDescription() {
        return programDescription;
    }

    public void setProgramDescription(String programDescription) {
        this.programDescription = programDescription;
    }

    public Integer getEnglishLang() {
        return englishLang;
    }

    public void setEnglishLang(Integer englishLang) {
        this.englishLang = englishLang;
    }

    public Integer getEmployment() {
        return employment;
    }

    public void setEmployment(Integer employment) {
        this.employment = employment;
    }

    public Integer getComputerSkill() {
        return computerSkill;
    }

    public void setComputerSkill(Integer computerSkill) {
        this.computerSkill = computerSkill;
    }

    public Integer getItInterest() {
        return itInterest;
    }

    public void setItInterest(Integer itInterest) {
        this.itInterest = itInterest;
    }

    public Integer getEducation() {
        return Education;
    }

    public void setEducation(Integer education) {
        Education = education;
    }

    public Boolean getMajorCS() {
        return majorCS;
    }

    public void setMajorCS(Boolean majorCS) {
        this.majorCS = majorCS;
    }

    public Boolean getLegalUS() {
        return legalUS;
    }

    public void setLegalUS(Boolean legalUS) {
        this.legalUS = legalUS;
    }

    public Integer getOopUnderstanding() {
        return oopUnderstanding;
    }

    public void setOopUnderstanding(Integer oopUnderstanding) {
        this.oopUnderstanding = oopUnderstanding;
    }

    public Integer getOolExperience() {
        return oolExperience;
    }

    public void setOolExperience(Integer oolExperience) {
        this.oolExperience = oolExperience;
    }

    public Integer getGradYear() {
        return gradYear;
    }

    public void setGradYear(Integer gradYear) {
        this.gradYear = gradYear;
    }

    public Integer getCurrentEarning() {
        return currentEarning;
    }

    public void setCurrentEarning(Integer currentEarning) {
        this.currentEarning = currentEarning;
    }

    // CONNECTIONS
    // Connections to Student
    // Qualified
    @ManyToMany
    private List<Student> qualifiedStudents;

    public void addQualified(Student student){
        this.qualifiedStudents.add(student);
    }

    public List<Student> getQualifiedStudents() {
        return qualifiedStudents;
    }

    public void setQualifiedStudents(List<Student> qualifiedStudents) {
        this.qualifiedStudents = qualifiedStudents;
    }

    // Approved
    @ManyToMany
    private List<Student> approvedStudents;

    public void addApproved(Student student){
        this.approvedStudents.add(student);
    }

    public List<Student> getApprovedStudents() {
        return approvedStudents;
    }

    public void setApprovedStudents(List<Student> approvedStudents) {
        this.approvedStudents = approvedStudents;
    }

    // Accepted
    @ManyToMany
    private List<Student> acceptedStudents;

    public void addAccepted(Student student){
        this.acceptedStudents.add(student);
    }

    public List<Student> getAcceptedStudents() {
        return acceptedStudents;
    }

    public void setAcceptedStudents(List<Student> acceptedStudents) {
        this.acceptedStudents = acceptedStudents;
    }
}
