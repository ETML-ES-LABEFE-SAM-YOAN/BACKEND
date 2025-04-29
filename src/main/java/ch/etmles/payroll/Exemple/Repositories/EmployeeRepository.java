package ch.etmles.payroll.Exemple.Repositories;

import ch.etmles.payroll.Exemple.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long>{

}
