package oleksii.leheza.labs;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
public class Subject {

    private String name;
    private Date finishDate;
    private Set<Date> lessons;
}
