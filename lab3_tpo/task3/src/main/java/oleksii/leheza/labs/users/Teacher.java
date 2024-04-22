package oleksii.leheza.labs.users;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import oleksii.leheza.labs.Mark;
import oleksii.leheza.labs.Subject;
import oleksii.leheza.labs.enums.TeacherRole;

import java.util.Date;


@Getter
@Setter
@ToString
public class Teacher {

    private String name;
    private TeacherRole role;

    public Teacher(TeacherRole teacherRole, String name) {
        role = teacherRole;
        this.name = name;
    }

    public Mark readMark(Student student, Subject subject, Date date) {
        return student.getMark(subject, date);
    }

    public void setMark(Student student, Subject subject, Date date, int mark) {
        synchronized (student.getMarks().get(subject).get(date)) {
            student.setMark(subject, date, mark);
        }
    }
}
