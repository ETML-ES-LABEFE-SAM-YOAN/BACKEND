package ch.etmles.payroll.Exemple.Controllers;

public class EmployeeNotFoundException extends RuntimeException{

    EmployeeNotFoundException(Long id){
        super("Could not find employee " + id);
    }
}
