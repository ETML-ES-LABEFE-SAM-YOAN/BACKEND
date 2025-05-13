package ch.etmles.bidster;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadDatabase {
    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    /*@Bean
    CommandLineRunner initDatabase(LotRepository repository) throws ParseException {
        //Date fixe explicite
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date1 = format.parse("2025-05-01 18:00");
        return args -> {
            log.info("Préchargement Lot A : " + repository.save(
                    new LotEntity("Vase Ming", "Céramique ancienne", 8500.0, date1,
                            "XVème siècle, état impeccable", "vase.jpg")
            ));
        };
    }*/
}
