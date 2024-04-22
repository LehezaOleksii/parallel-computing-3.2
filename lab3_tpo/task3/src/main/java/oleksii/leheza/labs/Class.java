package oleksii.leheza.labs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import oleksii.leheza.labs.users.Student;
import oleksii.leheza.labs.users.Teacher;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
public class Class {

    private List<Student> students;
    private List<Teacher> lecturerTeachers;
    private List<Teacher> practicalTeachers;

    public void addStudent(Student student) {
        students.add(student);
    }

    public void addLecturer(Teacher lecturer) {
        lecturerTeachers.add(lecturer);
    }

    public void addPractitioner(Teacher practitioner) {
        practicalTeachers.add(practitioner);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

        sb.append("Classroom Grade Book\n");

        // Table header
        sb.append("+----------------------+-------------------+------------+-------+\n");
        sb.append("|      Student         |      Subject      |    Date    |  Mark |\n");
        sb.append("+----------------------+-------------------+------------+-------+\n");

        // Table data
        for (Student student : students) {
            for (Map.Entry<Subject, Map<Date, Mark>> entry : student.getMarks().entrySet()) {
                Subject subject = entry.getKey();
                Map<Date, Mark> marks = entry.getValue();
                for (Map.Entry<Date, Mark> markEntry : marks.entrySet()) {
                    Mark mark = markEntry.getValue();
                    String formattedDate = dateFormat.format(markEntry.getKey());
                    sb.append(String.format("| %-20s | %-17s | %-10s | %5d |\n",
                            student.getName(), subject.getName(), formattedDate, mark.getMark()));
                }
            }
        }

        // Table footer
        sb.append("+----------------------+-------------------+------------+-------+\n");

        return sb.toString();
    }
}
