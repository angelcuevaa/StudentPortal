package model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity (name = "STUDENTS")
public class Student {
    //many to many
    @ManyToMany(mappedBy = "enrollment", fetch = FetchType.EAGER)
    private Set<Section> sectionsEnrolled;
    //fk
    //link to other classes
    @OneToMany (mappedBy = "student", fetch=FetchType.EAGER)
    private List<Transcript> transcripts;

    //pk
    @Id
    @Column (name = "student_id", nullable = false)
    private int studentId;

    //class attributes
    @Column (nullable = false)
    private String name;

    public Student(){

    }

    public Set<Section> getSectionsEnrolled() {
        return sectionsEnrolled;
    }

    public void setSectionsEnrolled(Set<Section> sectionsEnrolled) {
        this.sectionsEnrolled = sectionsEnrolled;
    }
    public Section addSectionsEnrolled(Section s) {

        sectionsEnrolled.add(s);
        return s;
    }

    public List<Transcript> getTranscripts() {
        return transcripts;
    }

    public void setTranscripts(List<Transcript> transcripts) {
        this.transcripts = transcripts;
    }

    public void addTranscript (Transcript t){
        transcripts.add(t);
    }


    public int getStudentId() {
        return studentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Student(String name, int studentId) {
        this.studentId = studentId;
        this.name = name;
        sectionsEnrolled = new HashSet<>();
        transcripts = new ArrayList<>();
    }
    public void setSection(Section s){
        this.sectionsEnrolled.add(s);
        s.getEnrollment().add(this);

    }

    @Override
    public String toString() {
        return "Name - " + getName() +", ID# - " + getStudentId();
    }
}
