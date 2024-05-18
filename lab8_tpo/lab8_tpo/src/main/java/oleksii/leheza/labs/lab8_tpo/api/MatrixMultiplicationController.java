package oleksii.leheza.labs.lab8_tpo.api;

import lombok.RequiredArgsConstructor;
import oleksii.leheza.labs.lab8_tpo.domain.MatrixPair;
import oleksii.leheza.labs.lab8_tpo.domain.ServerRequestDto;
import oleksii.leheza.labs.lab8_tpo.multiplication.Matrix;
import oleksii.leheza.labs.lab8_tpo.service.MatrixMultiplicationService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.Duration;
import java.time.ZonedDateTime;

@Controller
@RequiredArgsConstructor
@RequestMapping(path = "/matrix")
public class MatrixMultiplicationController {

    private final MatrixMultiplicationService service;

    @PostMapping("/calculate/client")
    public ResponseEntity<Matrix> calculateMatrix(@RequestBody MatrixPair matrixPair) {
        ZonedDateTime startTime = ZonedDateTime.parse(matrixPair.getStartTimeStr());
        Matrix result = service.multiply(matrixPair.getFirstMatrix(), matrixPair.getSecondMatrix(), startTime);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/result_matrix")
    public ModelAndView calculateMatrixInServer() {
        ModelAndView modelAndView = new ModelAndView("result_matrix");
//        modelAndView.addObject("matrix", matrix);
        return modelAndView;
    }

    @PostMapping("/calculate/server")
    public ModelAndView calculateMatrixInServer(@RequestBody ServerRequestDto dto) {
        ModelAndView modelAndView = new ModelAndView("result_matrix");
        modelAndView.addObject("matrix_result", service.multiply(dto.getMatrixSize()));

        return modelAndView;
    }

    @GetMapping("/results")
    public ModelAndView getResults() {
        ModelAndView modelAndView = new ModelAndView("result_table");
        modelAndView.addObject("results", service.getResult());
        return modelAndView;
    }
}
