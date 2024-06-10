package com.movies.spacecraft;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class SpacecraftApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpacecraftApiApplication.class, args);
    }

}
