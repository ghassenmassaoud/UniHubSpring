package tn.esprit.pidevarctic;

import jakarta.annotation.Resource;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import tn.esprit.pidevarctic.Service.DocumentService;
import org.springframework.context.annotation.ComponentScan;
@EnableScheduling
@SpringBootApplication
public class PiDevArcticApplication   {
    public static void main(String[] args) {
        SpringApplication.run(PiDevArcticApplication.class, args);
    }

}
