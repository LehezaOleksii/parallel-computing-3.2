package oleksii.leheza.labs;

import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class GradeBook {

    private Set<Class> classes = new HashSet<>();

    @Override
    public String toString() {
        return classes.toString();
    }
}
