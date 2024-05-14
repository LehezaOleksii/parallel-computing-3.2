package oleksii.leheza.labs.users;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import oleksii.leheza.labs.Class;
import oleksii.leheza.labs.Mark;
import oleksii.leheza.labs.Subject;
import oleksii.leheza.labs.enums.TeacherRole;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@ToString
public class Teacher {

    private String name;
    private TeacherRole role;
    private Set<Class> classes = new HashSet<>();

    public Teacher(TeacherRole teacherRole, Class clas, String name) {
        role = teacherRole;
        this.name = name;
        classes.add(clas);
    }

    public Mark readMark(Student student, Subject subject, Date date) {
        if (isStudentInTeacherClasses(student)) {
            return student.getMark(subject, date);
        } else return new Mark(subject, new Date("01.01.1000"), 0);
    }

    public void setMark(Student student, Subject subject, Date date, int mark) {
        if (isStudentInTeacherClasses(student)) {
            synchronized (student.getMarks().get(subject).get(date)) {
                student.setMark(subject, date, mark);
            }
        }
    }

    public void assignClass(Class clas) {
        classes.add(clas);
    }

    private boolean isStudentInTeacherClasses(Student student) {
        return student.getClasses().stream().anyMatch(classes::contains);
    }
}
