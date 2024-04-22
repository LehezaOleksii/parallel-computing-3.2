package oleksii.leheza.labs;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Mark {

    private static Long generalId = 0l;

    private Long id;
    private Subject subject;
    private int mark;
    private Date date;

    public Mark(Subject subject, Date date, int mark) {
        this.subject = subject;
        this.date = date;
        this.mark = mark;
        id = ++generalId;
    }

    @Override
    public String toString() {
        return "(" + id + " Mark) " + subject.getName() + " " + mark + date;
    }
}
