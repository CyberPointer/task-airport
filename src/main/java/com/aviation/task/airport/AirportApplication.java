package com.aviation.task.airport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AirportApplication {
    static Logger log = LoggerFactory.getLogger(AirportApplication.class);

    public static void main(String[] args) {
        log.info("Starting application");
        SpringApplication.run(AirportApplication.class, args);
    }
}
