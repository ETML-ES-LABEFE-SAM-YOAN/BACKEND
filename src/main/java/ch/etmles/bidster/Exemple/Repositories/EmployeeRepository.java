package ch.etmles.bidster.Exemple.Repositories;

import ch.etmles.bidster.Exemple.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long>{

}
