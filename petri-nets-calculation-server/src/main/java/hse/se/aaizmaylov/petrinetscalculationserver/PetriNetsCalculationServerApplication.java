package hse.se.aaizmaylov.petrinetscalculationserver;

import hse.se.aaizmaylov.petrinetscalculationserver.properties.FileStorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({FileStorageProperties.class})
public class PetriNetsCalculationServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(PetriNetsCalculationServerApplication.class, args);
    }

}

