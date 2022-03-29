package model;

import jakarta.persistence.*;
import java.util.*;

@Entity (name = "Departments")
public class Department {

    @Id
    @Column(name = "DEPARTMENT_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int departmentId;

    @OneToMany(mappedBy = "Department")
    private List<Course> courses;

    @Column (length = 128, nullable = false)
    private String name;

    @Column (length = 8, nullable = false)
    private String abbreviation;



    public Department(){

    }
    public Department(String name, String abbreviation){
        this.name = name;
        this.abbreviation = abbreviation;
        courses = new ArrayList<>();
    }

    public int getDepartmentId() {
        return departmentId;
    }


    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }
    public void addCourse(Course c){
        courses.add(c);
        c.setDepartment(this);
    }
}
