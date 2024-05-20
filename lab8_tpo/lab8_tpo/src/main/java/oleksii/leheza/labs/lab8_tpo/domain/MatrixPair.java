package oleksii.leheza.labs.lab8_tpo.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MatrixPair {
    private Matrix firstMatrix;
    private Matrix secondMatrix;
    private String startTimeStr;
}
