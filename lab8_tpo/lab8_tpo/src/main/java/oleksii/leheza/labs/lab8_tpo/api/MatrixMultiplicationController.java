package oleksii.leheza.labs.lab8_tpo.api;

import lombok.RequiredArgsConstructor;
import oleksii.leheza.labs.lab8_tpo.domain.MatrixPair;
import oleksii.leheza.labs.lab8_tpo.domain.ServerRequestDto;
import oleksii.leheza.labs.lab8_tpo.multiplication.Matrix;
import oleksii.leheza.labs.lab8_tpo.service.MatrixMultiplicationService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.time.ZonedDateTime;
import java.util.Map;

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
        modelAndView.addObject("matrix", service.getLastResult());
        return modelAndView;
    }

    @PostMapping("/calculate/server")
    public ResponseEntity<Matrix> calculateMatrixInServer(@RequestBody Map<String, Integer> requestBody) {
        int size = requestBody.get("size");
        ZonedDateTime startTime = ZonedDateTime.now();
        Matrix result = service.multiply(size, startTime);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/results")
    public ModelAndView getResults() {
        ModelAndView modelAndView = new ModelAndView("result_table");
        modelAndView.addObject("results", service.getResult());
        return modelAndView;
    }
}
