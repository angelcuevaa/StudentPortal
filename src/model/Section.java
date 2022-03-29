package model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity(name = "Sections")
@Table(uniqueConstraints={
        @UniqueConstraint(columnNames = {"semester_id", "course_id", "sectionNumber"})})
public class Section {
    //many to many
    @JoinTable(
            name = "ENROLLMENT",
            joinColumns = @JoinColumn(name = "SECTION_ID"),
            inverseJoinColumns = @JoinColumn(name = "STUDENT_ID")
    )
    @ManyToMany (fetch = FetchType.EAGER)
    private Set<Student> enrollment;

    //fk
    @ManyToOne
    @JoinColumn (name = "COURSE_ID", nullable = false)
    private Course course;

    @ManyToOne
    @JoinColumn (name = "SEMESTER_ID", nullable = false)
    private Semester semester;

    @ManyToOne
    @JoinColumn (name = "TIMESLOT_ID", nullable = false)
    private TimeSlot timeSlot;

    //link to other classes
    @OneToMany (mappedBy = "section")
    private List<Transcript> transcripts;

    //pk
    @Id
    @Column(name = "section_id", nullable = false)
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int sectionId;

    //class attributes
    private int sectionNumber;
    private int maxCapacity;

    public Section(){

    }
    public Section (Course course, Semester semester, TimeSlot timeSlot, int sectionNumber, int maxCapacity){
        this.course = course;
        this.semester = semester;
        this.timeSlot = timeSlot;
        this.sectionNumber = sectionNumber;
        this.maxCapacity = maxCapacity;
        transcripts = new ArrayList<>();
        enrollment = new HashSet<>();
    }

    public int getSectionId() {
        return sectionId;
    }

    public Set<Student> getEnrollment() {
        return enrollment;
    }

    public void setEnrollment(Set<Student> enrollment) {
        this.enrollment = enrollment;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Semester getSemester() {
        return semester;
    }

    public void setSemester(Semester semester) {
        this.semester = semester;
    }

    public TimeSlot getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(TimeSlot timeSlot) {
        this.timeSlot = timeSlot;
    }

    public List<Transcript> getTranscripts() {
        return transcripts;
    }

    public void setTranscripts(List<Transcript> transcripts) {
        this.transcripts = transcripts;
    }

    public void setSectionId(int sectionId) {
        this.sectionId = sectionId;
    }

    public int getSectionNumber() {
        return sectionNumber;
    }

    public void setSectionNumber(int sectionNumber) {
        this.sectionNumber = sectionNumber;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }
    public void setStudent(Student s){
        this.enrollment.add(s);
        s.getSectionsEnrolled().add(this);
    }
    public void addSemester(Semester s){
        this.setSemester(s);
        s.getSections().add(this);
    }

    @Override
    public String toString() {
        return "Section{" +
                "course=" + getCourse() +
                ", semester=" + getSemester() +
                ", timeSlot=" + getTimeSlot() +
                ", sectionNumber=" + getSectionNumber() +
                ", maxCapacity=" + getMaxCapacity()+
                '}';
    }
}
