package model;

import jakarta.persistence.*;

@Entity (name = "TRANSCRIPTS")
public class Transcript {
    //fk & pk
    @Id
    @ManyToOne
    @JoinColumn (name = "SECTION_ID", nullable = false)
    private Section section;

    @Id
    @ManyToOne
    @JoinColumn (name = "STUDENT_ID", nullable = false)
    private Student student;

    //link to other classes

    //class attributes
    @Column(length = 2, nullable = false)
    private String gradeEarned;

    public Transcript(){

    }

    public Transcript(Section section, Student student, String gradeEarned) {
        this.section = section;
        this.student = student;
        this.gradeEarned = gradeEarned;
    }

    public Section getSection() {
        return section;
    }

    public Student getStudent() {
        return student;
    }

    public String getGradeEarned() {
        return gradeEarned;
    }

    public void setGradeEarned(String gradeEarned) {
        this.gradeEarned = gradeEarned;
    }

    @Override
    public String toString() {
        return "Transcript{" +
                "section=" + getSection().getCourse().getNumber() +
                ", student=" + getStudent() +
                ", gradeEarned='" + getGradeEarned() + '\'' +
                '}';
    }
}
