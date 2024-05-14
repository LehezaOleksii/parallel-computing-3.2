package oleksii.leheza.labs.users;

import oleksii.leheza.labs.Class;
import oleksii.leheza.labs.enums.TeacherRole;

public class Lecturer extends Teacher {

    public Lecturer(TeacherRole teacherRole, Class clas, String name) {
        super(teacherRole, clas, name);
    }
}
