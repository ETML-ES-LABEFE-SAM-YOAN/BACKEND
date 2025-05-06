package ch.etmles.payroll.Categorie;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class CategorieNotFoundAdvice {
    @ResponseBody
    @ExceptionHandler(CategorieNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String categorieNotFoundHandler(CategorieNotFoundException ex) {
        return ex.getMessage();
    }
}
