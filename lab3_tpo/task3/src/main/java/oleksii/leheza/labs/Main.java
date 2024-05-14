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

public class Main {

    public static void main(String[] args) {
        List<Teacher> teachers = new ArrayList<>();
        Faker faker = new Faker();
        List<Teacher> lecturers = new ArrayList<>();


        Group group1 = new Group("IP", 13);
        Group group2 = new Group("IP", 14);
        Group group3 = new Group("IP", 15);

        List<Teacher> practitioners1 = new ArrayList<>();
        List<Teacher> practitioners2 = new ArrayList<>();
        List<Teacher> practitioners3 = new ArrayList<>();
        List<Student> students1 = new ArrayList<>();
        List<Student> students2 = new ArrayList<>();
        List<Student> students3 = new ArrayList<>();

        Class class1 = new Class(students1, lecturers, practitioners1);
        Class class2 = new Class(students2, lecturers, practitioners2);
        Class class3 = new Class(students3, lecturers, practitioners3);

        Teacher practitioner1 = new Practitioner(TeacherRole.PRACTICER_ROLE, class1, "pr1");
        practitioners1.add(practitioner1);
        practitioners2 = new ArrayList<>();
        Teacher practitioner2 = new Practitioner(TeacherRole.PRACTICER_ROLE, class2, "pr2");
        practitioners2.add(practitioner2);
        practitioners3 = new ArrayList<>();
        Teacher practitioner3 = new Practitioner(TeacherRole.PRACTICER_ROLE, class3, "pr3");
        practitioners3.add(practitioner3);
        Teacher lecturer = new Teacher(TeacherRole.LECTURER_ROLE, class1, "lec1");
        lecturer.assignClass(class2);
        lecturer.assignClass(class3);
        lecturer.setName(faker.name().fullName());
        teachers.add(lecturer);
        lecturers.add(lecturer);


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
            student.assignStudentToClass(class1);
        }
        System.out.println(class1);
        for (int i = 0; i < 30; i++) {
            Student student = new Student(faker.name().fullName(), group2);
            student.assignStudentToSubject(subject);
            class2.addStudent(student);
            student.assignStudentToClass(class2);
        }
        System.out.println(class2);
        for (int i = 0; i < 30; i++) {
            Student student = new Student(faker.name().fullName(), group3);
            student.assignStudentToSubject(subject);
            class3.addStudent(student);
            student.assignStudentToClass(class3);
        }
        System.out.println(class3);

        List<Class> classes = new ArrayList<>();
        classes.add(class1);
        classes.add(class2);
        classes.add(class3);

        List<Thread> threads = new ArrayList<>();
        threads.add(new WorkLecThread(lecturer, subject));
        threads.add(new WorkPracThread(practitioner1, subject));
        threads.add(new WorkPracThread(practitioner2, subject));
        threads.add(new WorkPracThread(practitioner3, subject));

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
