package oleksii.leheza.labs.users;

import oleksii.leheza.labs.Class;
import oleksii.leheza.labs.Group;
import oleksii.leheza.labs.enums.TeacherRole;

import java.util.Set;

public class Practitioner extends Teacher {


    public Practitioner(TeacherRole teacherRole, Class clas, String name) {
        super(teacherRole, clas,name);
    }
}
