package ch.etmles.bidster.Enchere;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandlerEnchere {

    @ExceptionHandler(EnchereMontantInvalideException.class)
    public ResponseEntity<String> handleEnchereMontantInvalide(EnchereMontantInvalideException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(SoldeInsuffisantException.class)
    public ResponseEntity<String> handleSoldeInsuffisant(SoldeInsuffisantException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

}
