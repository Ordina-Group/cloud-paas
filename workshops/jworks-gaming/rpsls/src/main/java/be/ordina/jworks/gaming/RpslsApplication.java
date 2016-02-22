package be.ordina.jworks.gaming;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
@EnableEurekaClient
@SpringBootApplication
public class RpslsApplication {

    public static void main(String[] args) {
        SpringApplication.run(RpslsApplication.class, args);
    }
}
