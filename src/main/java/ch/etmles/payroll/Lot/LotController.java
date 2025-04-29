package ch.etmles.payroll.Lot;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class LotController {

    private final LotRepository lotRepository;

    LotController(LotRepository lotRepository) {
        this.lotRepository = lotRepository;
    }

    /* curl sample :
    curl -i localhost:8080/lots
    */
    @GetMapping("/lots")
    List<LotEntity> all(){
        return lotRepository.findAll();
    }


}
