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

public class WorkPracThread extends Thread {

    private GradeBook gradeBook;
    private Teacher teacher;
    private Subject subject;
    private Class classObj;

    private List<Student> students;

    public WorkPracThread(Teacher teacher, GradeBook gradeBook, Subject subject, Class classObj) {
        this.teacher = teacher;
        this.gradeBook = gradeBook;
        this.subject = subject;
        this.students = classObj.getStudents();
    }

    @Override
    public void run() {
        int i = 0;
        Random random = new Random();
        List<Date> dates = subject.getLessons().stream().toList();
        while (i++ < 1000) {
            Student student = students.get(random.nextInt((students.size())));
            if (random.nextInt() % 2 == 0) {
                int mark = random.nextInt(3) + 7;
                teacher.setMark(student, subject, dates.get(random.nextInt(dates.size())), mark);
                System.out.println(teacher.getRole() + " " + teacher.getName() + " set mark: " + mark + " for student " + student.getName());
            } else {
                Mark mark = teacher.readMark(student, subject, dates.get(i % dates.size()));
                System.out.println(teacher.getRole() + " " + teacher.getName() + " read mark: " + mark.toString() + "; for student " + student.getName());
            }
        }
    }
}