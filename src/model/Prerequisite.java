package model;

import jakarta.persistence.*;

@Entity (name = "PREREQUISITES")
public class Prerequisite {

    @Id
    @ManyToOne
    @JoinColumn (name = "COURSE_ID", nullable = false)
    private Course preReq;

    @Id
    @ManyToOne
    @JoinColumn (name = "preReq", nullable = false)
    private Course requiredFor;

    @Column (length = 2)
    private String minimumGrade;


    public Prerequisite(Course preReq, Course requiredFor, String minimumGrade) {
        this.preReq = preReq;
        this.requiredFor = requiredFor;
        this.minimumGrade = minimumGrade;
    }

    public Prerequisite() {

    }

    @Override
    public String toString() {
        return "Prerequisite{" +
                "preReq=" + preReq +
                ", requiredFor=" + requiredFor +
                ", minimumGrade='" + minimumGrade + '\'' +
                '}';
    }

    public Course getPreReq() {
        return preReq;
    }


    public Course getRequiredFor() {
        return requiredFor;
    }

    public String getMinimumGrade() {
        return minimumGrade;
    }

    public void setMinimumGrade(String minimumGrade) {
        this.minimumGrade = minimumGrade;
    }
}
