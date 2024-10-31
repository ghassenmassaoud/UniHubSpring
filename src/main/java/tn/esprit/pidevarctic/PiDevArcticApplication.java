package tn.esprit.pidevarctic;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
@EnableScheduling
@SpringBootApplication
public class PiDevArcticApplication   {
    public static void main(String[] args) {
        SpringApplication.run(PiDevArcticApplication.class, args);
    }

}
