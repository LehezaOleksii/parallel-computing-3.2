package oleksii.leheza.labs.users;

import oleksii.leheza.labs.Group;
import oleksii.leheza.labs.enums.TeacherRole;

public class Practitioner extends Teacher {

    private Group group;

    public Practitioner(TeacherRole teacherRole, Group group, String name) {
        super(teacherRole, name);
        this.group = group;
    }
}
