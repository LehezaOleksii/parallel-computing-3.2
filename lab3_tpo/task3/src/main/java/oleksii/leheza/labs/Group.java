package oleksii.leheza.labs;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Group {

    private String groupName;
    private int groupNumber;

    @Override
    public String toString() {
        return groupName + "-" + groupNumber;
    }
}
