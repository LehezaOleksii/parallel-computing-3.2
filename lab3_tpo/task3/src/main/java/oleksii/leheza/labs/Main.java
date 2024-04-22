package oleksii.leheza.labs;

import com.github.javafaker.Faker;
import oleksii.leheza.labs.enums.TeacherRole;
import oleksii.leheza.labs.threads.WorkLecThread;
import oleksii.leheza.labs.threads.WorkPracThread;
import oleksii.leheza.labs.users.Practitioner;
import oleksii.leheza.labs.users.Student;
import oleksii.leheza.labs.users.Teacher;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) {
        Random random = new Random();
        List<Teacher> teachers = new ArrayList<>();
        int practicesTeacherNumber = 3;
        int groupsNumber = 3;
        int studentsNumber = 90;
        Faker faker = new Faker();
        Teacher lecturer = new Teacher(TeacherRole.LECTURER_ROLE, "lec1");
        lecturer.setName(faker.name().fullName());
        teachers.add(lecturer);

        Group group1 = new Group("IP", 13);
        Group group2 = new Group("IP", 14);
        Group group3 = new Group("IP", 15);

        List<Student> students1 = new ArrayList<>();
        List<Student> students2 = new ArrayList<>();
        List<Student> students3 = new ArrayList<>();

        Teacher practitioner1 = new Practitioner(TeacherRole.PRACTICER_ROLE, group1, "pr1");
        Teacher practitioner2 = new Practitioner(TeacherRole.PRACTICER_ROLE, group2, "pr2");
        Teacher practitioner3 = new Practitioner(TeacherRole.PRACTICER_ROLE, group3, "pr3");

        List<Teacher> lecturers = new ArrayList<>();
        lecturers.add(lecturer);

        List<Teacher> practitioners1 = new ArrayList<>();
        practitioners1.add(practitioner1);
        List<Teacher> practitioners2 = new ArrayList<>();
        practitioners2.add(practitioner2);
        List<Teacher> practitioners3 = new ArrayList<>();
        practitioners3.add(practitioner3);

        Class class1 = new Class(students1, lecturers, practitioners1);
        Class class2 = new Class(students2, lecturers, practitioners2);
        Class class3 = new Class(students3, lecturers, practitioners3);


        GradeBook gradeBook = new GradeBook();
        Set<Date> dates = new HashSet<>();

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        Date startDate;
        Date endDate;
        try {
            startDate = dateFormat.parse("09.02.2024");
            endDate = dateFormat.parse("15.06.2024");
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(startDate);
            dates.add(startDate);
            while (calendar.getTime().before(endDate)) {
                calendar.add(Calendar.WEEK_OF_YEAR, 1);
                Date nextDate = calendar.getTime();
                dates.add(nextDate);
            }
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        Subject subject = new Subject("Subject", endDate, dates);

        for (int i = 0; i < 30; i++) {
            Student student = new Student(faker.name().fullName(), group1);
            student.assignStudentToSubject(subject);
            class1.addStudent(student);
        }
        System.out.println(class1);
        for (int i = 0; i < 30; i++) {
            Student student = new Student(faker.name().fullName(), group2);
            student.assignStudentToSubject(subject);
            class2.addStudent(student);
        }
        System.out.println(class2);
        for (int i = 0; i < 30; i++) {
            Student student = new Student(faker.name().fullName(), group3);
            student.assignStudentToSubject(subject);
            class3.addStudent(student);
        }
        System.out.println(class3);

        List<Class> classes = new ArrayList<>();
        classes.add(class1);
        classes.add(class2);
        classes.add(class3);

        List<Thread> threads = new ArrayList<>();
        threads.add(new WorkLecThread(lecturer, gradeBook, subject, classes));
        threads.add(new WorkPracThread(practitioner1, gradeBook, subject, class1));
        threads.add(new WorkPracThread(practitioner2, gradeBook, subject, class2));
        threads.add(new WorkPracThread(practitioner3, gradeBook, subject, class3));

        for (Thread thread : threads) {
            thread.run();
        }
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println(class1);
        System.out.println(class2);
        System.out.println(class3);
    }
}
