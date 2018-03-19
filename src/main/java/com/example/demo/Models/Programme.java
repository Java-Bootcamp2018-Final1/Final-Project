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

    // CONNECTIONS
    // Connections to Student
    // Qualified
    @ManyToMany (mappedBy = "qualifiedProgram")
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
    @ManyToMany(mappedBy = "approvedProgram")
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
    @ManyToMany(mappedBy = "acceptedProgram")
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
