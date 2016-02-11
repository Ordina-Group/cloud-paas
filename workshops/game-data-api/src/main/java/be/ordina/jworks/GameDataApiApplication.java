package be.ordina.jworks;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
@SpringBootApplication
public class GameDataApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(GameDataApiApplication.class, args);
    }
}
