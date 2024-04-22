package oleksii.leheza.labs.threads;

import oleksii.leheza.labs.Class;
import oleksii.leheza.labs.GradeBook;
import oleksii.leheza.labs.Mark;
import oleksii.leheza.labs.Subject;
import oleksii.leheza.labs.users.Student;
import oleksii.leheza.labs.users.Teacher;

import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class WorkLecThread extends Thread {

    private GradeBook gradeBook;
    private Teacher lector;
    private Subject subject;
    private List<Class> classesObj;

    private List<Student> students;

    public WorkLecThread(Teacher lector, GradeBook gradeBook, Subject subject, List<Class> classesObj) {
        this.lector = lector;
        this.gradeBook = gradeBook;
        this.subject = subject;
        students = classesObj.stream()
                .flatMap(clazz -> clazz.getStudents().stream())
                .collect(Collectors.toList());
    }

    @Override
    public void run() {
        int i = 0;
        Random random = new Random();
        List<Date> dates = subject.getLessons().stream().toList();
        while (i++ < 3000) {
            Student student = students.get(random.nextInt((students.size() - 1)));
            if (random.nextInt() % 2 == 0) {
                int mark = random.nextInt(3) + 7;
                lector.setMark(student, subject, dates.get(random.nextInt((dates.size() - 1))), mark);
                System.out.println(lector.getRole() + " " + lector.getName() + " set mark: " + mark + " for student " + student.getName());
            } else {
                Mark mark = lector.readMark(student, subject, dates.get(i % dates.size()));
                System.out.println(lector.getRole() + " " + lector.getName() + " read mark" + mark.toString() + "for student " + student.getName());
            }
        }
    }
}
