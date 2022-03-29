package model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity (name = "COURSES")
@Table(uniqueConstraints={
        @UniqueConstraint(columnNames = {"department_id", "number"})})
public class Course {
    //fk
    @ManyToOne
    @JoinColumn(name = "DEPARTMENT_ID", nullable = false)
    private Department department;

    //linking to other class
    //CHANGE - good
    @OneToMany(mappedBy = "requiredFor")
    private Set<Prerequisite> prereq;

    //pk
    @Id
    @Column(name = "course_id", nullable = false)
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int courseId;

    //class attributes
    @Column (length = 64, nullable = false)
    private String title;

    @Column(length = 8, nullable = false)
    private String number;

    @Column(nullable = false)
    private byte units;

    public Course(){

    }
    public Course (String title, String number, byte units, Department department){
        this.title = title;
        this.number = number;
        this.units = units;
        this.department = department;
        prereq = new HashSet<>();
        //requiredfor = new HashSet<>();
    }

    public int getCourseId() {
        return courseId;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public byte getUnits() {
        return units;
    }

    public void setUnits(byte units) {
        this.units = units;
    }

    public void addDepartment(Department d){
        this.setDepartment(d);
        d.getCourses().add(this);
    }

    public Set<Prerequisite> getPrereq() {
        return prereq;
    }

    public void setPrereq(Set<Prerequisite> prereq) {
        this.prereq = prereq;
    }

    public void addPrereq(Prerequisite p) {
       getPrereq().add(p);
    }
    public void getPreReqs(){
        for(Prerequisite q: prereq){
            System.out.println(q);
        }
    }
    public String toString(){

        return getDepartment().getAbbreviation() + " " + number;
    }

}


