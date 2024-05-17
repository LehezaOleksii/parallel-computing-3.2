package oleksii.leheza.labs.lab8_tpo.api;

import lombok.RequiredArgsConstructor;
import oleksii.leheza.labs.lab8_tpo.domain.MatrixPair;
import oleksii.leheza.labs.lab8_tpo.service.MatrixMultiplicationService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
@RequestMapping(path = "/matrix")
public class MatrixMultiplicationController {

    private final MatrixMultiplicationService service;

    @PostMapping("/calculate/client")
    public ModelAndView calculateMatrix(@RequestBody MatrixPair matrixPair) {
        ModelAndView modelAndView = new ModelAndView("result_matrix");
        modelAndView.addObject("matrix_result", service.multiply(matrixPair.getFirstMatrix(), matrixPair.getSecondMatrix()));
        return modelAndView;
    }

    @PostMapping("/calculate/server")
    public ModelAndView calculateMatrixInServer(@ModelAttribute int matrixSize) {
        ModelAndView modelAndView = new ModelAndView("result_matrix");
        modelAndView.addObject("matrix_result", service.multiply(matrixSize));
        //time
        return modelAndView;
    }

    @GetMapping("/results")
    public ModelAndView getResults() {
        ModelAndView modelAndView = new ModelAndView("result_table");
        modelAndView.addObject("results", service.getResult());
        return modelAndView;
    }
}
