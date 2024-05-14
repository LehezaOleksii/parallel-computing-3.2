package oleksii.leheza.labs.users;

import lombok.Getter;
import lombok.ToString;
import oleksii.leheza.labs.Class;
import oleksii.leheza.labs.Group;
import oleksii.leheza.labs.Mark;
import oleksii.leheza.labs.Subject;

import java.util.*;

@Getter
@ToString
public class Student {

    private String name;
    private Group group;
    private final Map<Subject, Map<Date, Mark>> marks;
    private Set<Class> classes;


    public Student(String name, Group group) {
        this.name = name;
        this.group = group;
        marks = new HashMap<>();
        classes = new HashSet<>();
    }

    public void setMark(Subject subject, Date date, int mark) {
        if (marks.containsKey(subject)) {
            if (subject.getLessons().contains(date)) {
                Mark markObj;
                synchronized (marks.get(subject).get(date).getId()) {
                    if (!isMarkMoreThanMaxValue(mark, subject)) {
                        markObj = new Mark(subject, date, mark);

                    } else {
                        markObj = new Mark(subject, date, 100 - getTotalMark(subject));
                    }
                    marks.get(subject).put(date, markObj);
                }
            } else {
                System.err.println("Date is not available");
            }
        } else {
            System.err.println("Student does not assign to this subject:" + subject.getName());
        }
    }

    public Mark getMark(Subject subject, Date date) {
        if (marks.containsKey(subject)) {
            if (subject.getLessons().contains(date)) {
                synchronized (marks.get(subject).get(date).getId()) {
                    return marks.get(subject).get(date);
                }
            } else {
                System.err.println("Date is not available");
            }
        } else {
            System.err.println("111Student does not assign to this subject:" + subject.getName());
        }
        throw new RuntimeException();
    }

    public void assignStudentToSubject(Subject subject) {
        if (marks.isEmpty() || marks.get(subject).isEmpty()) {
            Set<Date> dates = subject.getLessons();
            Map<Date, Mark> studentMarks = new HashMap<>();
            for (Date date : dates) {
                studentMarks.put(date, new Mark(subject, date, 0));
            }
            marks.put(subject, studentMarks);
        } else {
            System.err.println("Student has already assigned to this subject:" + subject.getName());
        }
    }

    private synchronized boolean isMarkMoreThanMaxValue(int mark, Subject subject) {
        return getTotalMark(subject) + mark > 100;
    }

    private int getTotalMark(Subject subject) {
        return marks.get(subject).values().stream().mapToInt(Mark::getMark).sum();
    }

    public void assignStudentToClass(Class clas) {
        classes.add(clas);
    }
}
