package model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

@Entity (name = "SEMESTERS")
public class Semester {
    //fk
    //link to other classes
    @OneToMany (mappedBy = "semester")
    private List<Section> sections;
    //pk
    @Id
    @Column(name = "semester_id")
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int semesterId;

    //class attributes
    @Column(length = 16, nullable = false)
    private String title;

    @Column(nullable = false)
    private LocalDate date;

    public Semester(){

    }

    public Semester(String title, LocalDate date) {
        this.title = title;
        this.date = date;
        sections = new ArrayList<>();
    }

    public List<Section> getSections() {
        return sections;
    }

    public void setSections(List<Section> sections) {
        this.sections = sections;
    }

    public int getSemesterId() {
        return semesterId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
    public void addSection(Section s){
        this.sections.add(s);
        s.setSemester(this);
    }
}
