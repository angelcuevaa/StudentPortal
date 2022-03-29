import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

import jakarta.persistence.*;
import model.*;
public class App {
    // These demos show finding, creating, and updating individual objects.
    enum registrationResult{
        SUCCESS, ALREADY_PASSSED(), ENROLLED_IN_SECTION, NO_PREREQUISITES, ENROLLED_IN_ANOTHER, TIME_CONFLICT
    }
    //all database objects created and added to database
    private static void instantiateDatabase(){
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("demoDb");
        EntityManager em = factory.createEntityManager();
        em.getTransaction().begin();

        Department cecsDep = new Department("Computer Engineering and Computer Science", "CECS");
        Department italDep = new Department("Italian Studies", "ITAL");

        Semester sp21 = new Semester("Spring 2021", LocalDate.of(2021, 1, 19));
        Semester fa21 = new Semester("Fall 2021", LocalDate.of(2021, 8, 17));
        Semester sp22 = new Semester("Spring 2022", LocalDate.of(2022, 1, 20));

        Course cecs174 = new Course("Intro to Programming", "174", (byte) 3, cecsDep);
        Course cecs274 = new Course ("Data Structures", "274", (byte) 3, cecsDep);
        Course cecs277 = new Course ("Object Oriented Application Programming", "277", (byte) 3, cecsDep);
        Course cecs282 = new Course ("Advanced C++", "282", (byte) 3, cecsDep);
        Course ital101A = new Course("Fundamentals of Italian", "101A", (byte) 4, italDep);
        Course ital101B = new Course("Fundamentals of Italian", "101B", (byte) 4, italDep);

        Prerequisite cecs1 = new Prerequisite(cecs174, cecs274, "C");
        Prerequisite cecs2 = new Prerequisite(cecs174, cecs277, "C");
        Prerequisite cecs3 = new Prerequisite(cecs274, cecs282, "C");
        Prerequisite cecs4 = new Prerequisite(cecs277, cecs282, "C");
        Prerequisite ital1 = new Prerequisite(ital101A, ital101B, "D");

        TimeSlot mw = new TimeSlot((byte) 40, LocalTime.of(12, 30), LocalTime.of(1,45));
        TimeSlot tthu = new TimeSlot((byte) 20, LocalTime.of(2, 0), LocalTime.of(3,15));
        TimeSlot mwf = new TimeSlot((byte) 42, LocalTime.of(12, 0), LocalTime.of(12,50));
        TimeSlot f = new TimeSlot((byte) 2, LocalTime.of(8, 0), LocalTime.of(10,45));

        Section cecs174_1 = new Section(cecs174, sp21, mw,1, 105);
        Section cecs274_1 = new Section(cecs274, fa21, tthu,1, 140);
        Section cecs277_3 = new Section(cecs277, fa21, f,3, 35);
        Section cecs282_5 = new Section(cecs282, sp22, tthu,5, 35);
        Section cecs277_1 = new Section(cecs277, sp22, mw,1, 35);
        Section cecs282_7 = new Section(cecs282, sp22, mw,7, 35);
        Section ital101A_1 = new Section(ital101A, sp22, mwf,1, 25);

        Student nN = new Student("Naomi Nagata", 123456789);
        Student jH = new Student("James Holden", 987654321);
        Student aB = new Student("Amos Burton", 555555555);

        Transcript nN_174_transcript = new Transcript(cecs174_1, nN, "A");
        Transcript nN_274_transcript = new Transcript(cecs274_1, nN, "A");
        Transcript nN_277_transcript = new Transcript(cecs277_3, nN, "A");

        nN.addTranscript(nN_174_transcript);
        nN.addTranscript(nN_274_transcript);
        nN.addTranscript(nN_277_transcript);

        nN.addSectionsEnrolled(cecs282_5);

        System.out.println(nN.getSectionsEnrolled());

        Transcript jH_174_transcript = new Transcript(cecs174_1, jH, "C");
        Transcript jH_274_transcript = new Transcript(cecs274_1, jH, "C");
        Transcript jH_277_transcript = new Transcript(cecs277_3, jH, "C");

        Transcript aB_174_transcript = new Transcript(cecs174_1, aB, "C");
        Transcript aB_274_transcript = new Transcript(cecs274_1, aB, "B");
        Transcript aB_277_transcript = new Transcript(cecs277_3, aB, "D");

        em.persist(cecsDep);
        em.persist(italDep);
        em.persist(sp21);
        em.persist(fa21);
        em.persist(sp22);
        em.persist(cecs174);
        em.persist(cecs277);
        em.persist(cecs274);
        em.persist(cecs282);
        em.persist(ital101A);
        em.persist(ital101B);
        em.persist(cecs1);
        em.persist(cecs2);
        em.persist(cecs3);
        em.persist(cecs4);
        em.persist(ital1);
        em.persist(mw);
        em.persist(tthu);
        em.persist(mwf);
        em.persist(f);
        em.persist(cecs174_1);
        em.persist(cecs274_1);
        em.persist(cecs277_3);
        em.persist(cecs282_5);
        em.persist(cecs277_1);
        em.persist(cecs282_7);
        em.persist(ital101A_1);
        em.persist(jH);
        em.persist(nN);
        em.persist(aB);
        em.persist(nN_174_transcript);
        em.persist(nN_277_transcript);
        em.persist(nN_274_transcript);
        em.persist(jH_174_transcript);
        em.persist(jH_274_transcript);
        em.persist(jH_277_transcript);
        em.persist(aB_174_transcript);
        em.persist(aB_274_transcript);
        em.persist(aB_277_transcript);

        em.getTransaction().commit();

    }
    //User enters a student to lookup, then run through a paramterized query to ensure a safe entry. If student matches
    //user input, that student object is returned. If not, method returns null
    public static Student studentLookup(){
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("demoDb");
        EntityManager em = factory.createEntityManager();

        Scanner scan = new Scanner(System.in);

        System.out.println("Enter the desired student's name.");
        String userInput = scan.nextLine();

        var namedStudent = em.createQuery("select s from STUDENTS s where s.name = ?1", Student.class);
        namedStudent.setParameter(1, userInput);

        Student requested = null;
        try {
            requested = namedStudent.getSingleResult();
        }
        catch (NoResultException ex) {
            System.out.println("Student with name '" + userInput + "' not found.");
        }
        return requested;
    }

    //Calculates student object's gpa. Retreives total grade points and units from student transcript. Gpa is then outputted
    //calculated by dividing total gpa points by total units. At the same time, course numbers, department names, and semester names
    //are collected to output a formatted transcript that the user can see
    public static void getGpa() {
        Map <String, Integer> gradeConverstion = new HashMap<>();
        gradeConverstion.put("A", 4);
        gradeConverstion.put("B", 3);
        gradeConverstion.put("C", 2);
        gradeConverstion.put("D", 1);
        gradeConverstion.put("F", 0);

        double gpa = 0;
        double totalUnits = 0;
        Student s = studentLookup();
        if (s == null){
            return;
        }
        List <Transcript> t = s.getTranscripts();
        for (Transcript tran : t) {
            String courseNumber = tran.getSection().getCourse().getNumber();
            String departmentName = tran.getSection().getCourse().getDepartment().getAbbreviation();
            String semesterName = tran.getSection().getSemester().getTitle();
            String grade = tran.getGradeEarned();
            String formattedTranscript = (departmentName + " "+ courseNumber + ", " + semesterName + ". Grade Earned: " + grade);
            System.out.println(formattedTranscript);

            String temp = tran.getGradeEarned();
            double tempUnit = tran.getSection().getCourse().getUnits();

            gpa += gradeConverstion.get(temp) * tempUnit;
            totalUnits += tempUnit;
        }
        System.out.println(s.getName() + "'s GPA: " + gpa/totalUnits);
    }

    //User inputs desired semester and course section to be looked up in a specific format. Course section input is then
    //parsed to then be passed to a parameterized query that searches database for a section matching the user's input.
    //If a section is found, it is returned. If not, null is returned
    public static Section getSection(){
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("demoDb");
        EntityManager em = factory.createEntityManager();

        Scanner scan = new Scanner(System.in);
        System.out.println("Enter a semester. Ex: Fall 2021");
        String userInputSem = scan.nextLine();

        System.out.println("Enter name of course section. Ex. CECS 277-05");
        String userInputSection = scan.nextLine();
        if (!userInputSection.contains(" ") && !userInputSection.contains("-")){
            while(!userInputSection.contains(" ") && !userInputSection.contains("-")) {
                System.out.println("Invalid name. Please try again.");
                userInputSection = scan.nextLine();
            }
        }
        String[] firstSplit = userInputSection.split(" ");
        String departmentName = firstSplit[0];
        String[] secondSplit = firstSplit[1].split("-");
        String courseNumber = secondSplit[0];
        int sectionNumber = Integer.parseInt(secondSplit[1]);

        var namedSection = em.createQuery("SELECT s from Sections s " +
                "inner join COURSES c on c.courseId = s.course.courseId " +
                "inner join Departments d on d.departmentId = c.department.departmentId" +
                " where d.abbreviation = ?1 and s.sectionNumber = ?2 and c.number = ?3 and s.semester.title = ?4" , Section.class);

        namedSection.setParameter(1, departmentName);
        namedSection.setParameter(2, sectionNumber);
        namedSection.setParameter(3, courseNumber);
        namedSection.setParameter(4, userInputSem);

        Section requested = null;
        try{
            requested = namedSection.getSingleResult();

        }
        catch (NoResultException e){
            System.out.println("No sections matching those arguments...");
        }
        return requested;

    }

    //Section is passed to method. Method checks various failure cases that prohibit a student to enroll for a course.
    //If a student trying to enroll for a particular section fails a case, the correpsonding failure enumerated value is
    //returned. If the student passes all cases, section is added to student's enrollments and student is added to section's
    //enrollments. Lastly, a success would be returned
    public static registrationResult registerForSection(Section s){
        Student student = studentLookup();
        //ALREADY PASSED
        for (Transcript t : student.getTranscripts()){
            String tranCourseNumber = t.getSection().getCourse().getNumber();
            String sectionCourseNumber = s.getCourse().getNumber();
            String tranCourseTitle = t.getSection().getCourse().getTitle();
            String sectionCourseTitle = s.getCourse().getTitle();

            //if wanted section and taken section equal, check grade. If grade is c or higher, return taken already
            if (tranCourseNumber.equals(sectionCourseNumber) && tranCourseTitle.equals(sectionCourseTitle)){
                String tranGrade = t.getGradeEarned();
                if(! (tranGrade.equals("D") || tranGrade.equals("F"))){
                    System.out.println("Could not complete request...");
                    return registrationResult.ALREADY_PASSSED;
                }
            }
        }
        //ENROLLED IN SECTION
        for (Section section: student.getSectionsEnrolled()){
            int studentSecId = section.getSectionId();
            int otherSecId = s.getSectionId();
            if(studentSecId == otherSecId){
                System.out.println("Could not complete request...");
                return registrationResult.ENROLLED_IN_SECTION;
            }
        }
        //NO_PREREQ
        //student would have needed to take ALL prereqs, defined in transcripts. Get list of prereqs from course

        //get student transcript --> get section --> get course
        //get desired section --> get course --> getPreReq --> get prereq
        //compare the two
        List<String> takenCourses = new ArrayList<>();
        List<String> requiredCourses = new ArrayList<>();
        for(Transcript t : student.getTranscripts()){
            if(!t.getGradeEarned().equals("D") && !t.getGradeEarned().equals("F")) {
                takenCourses.add(t.getSection().getCourse().getTitle() + t.getSection().getCourse().getNumber());
            }
        }
        for (Prerequisite prerequisite : s.getCourse().getPrereq()){
            requiredCourses.add(prerequisite.getPreReq().getTitle() + prerequisite.getPreReq().getNumber());
        }
        if (!takenCourses.containsAll(requiredCourses)){
            System.out.println("Could not complete request...");
            return registrationResult.NO_PREREQUISITES;
        }

        //enrolled in another, just compare course ids
        Integer secCourseId = s.getCourse().getCourseId();
        ArrayList<Integer> studentEnrolledCourseIds = new ArrayList<>();
        for (Section section : student.getSectionsEnrolled()){
            int sectionIds = section.getCourse().getCourseId();
            studentEnrolledCourseIds.add(sectionIds);
        }
        if (studentEnrolledCourseIds.contains(secCourseId)){
            System.out.println("Could not complete request...");
            return registrationResult.ENROLLED_IN_ANOTHER;
        }

        //time conflict
//        LocalTime sectionStartTIme = s.getTimeSlot().getStartTime();
//        LocalTime sectionEndTime = s.getTimeSlot().getEndTime();

        student.addSectionsEnrolled(s);
        s.getEnrollment().add(student);
        return registrationResult.SUCCESS;
    }
    public static int menu(){
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter a menu choice.");
        int menu_choice = scan.nextInt();
        while (menu_choice < 1 || menu_choice > 4) {
            System.out.println("Invalid, enter a menu choice.");
            menu_choice = scan.nextInt();
        }
        return menu_choice;
    }

    public static void main(String[] args) throws Exception {
        while (true){
            System.out.println("Welcome to student portal!");
            System.out.println("\n1. Instantiate Database");
            System.out.println("2. Student Lookup");
            System.out.println("3. Register for a course.");
            int menuChoice = menu();
            if (menuChoice == 1){
                instantiateDatabase();
            }
            if (menuChoice == 2){
                getGpa();
            }
            if (menuChoice == 3){
                Section s = getSection();
                if (s != null){
                    registrationResult r = registerForSection(s);
                    System.out.println(r);
                }
            }
            if (menuChoice == 4){
                break;
            }
        }
    }
}
