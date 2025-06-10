package ch.etmles.bidster;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BidsterApplication {

	public static void main(String[] args) {
		SpringApplication.run(BidsterApplication.class, args);
	}

}
