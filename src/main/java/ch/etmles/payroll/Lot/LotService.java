package ch.etmles.payroll.Lot;

import org.springframework.stereotype.Service;

@Service
public class LotService {
    private final LotRepository lotRepository;

    public LotService(LotRepository lotRepository) {
        this.lotRepository = lotRepository;
    }


}
