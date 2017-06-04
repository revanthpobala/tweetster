package com.revanth.twitter.thousandeyes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.revanth.twitter")
@SpringBootApplication
public class ThousandEyesApplication {

    /**
     * Start the application.
     *
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(ThousandEyesApplication.class, args);
    }
}
