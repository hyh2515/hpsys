package com.shou.hpsys;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HpsysApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(HpsysApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(HpsysApplication.class, args);
        LOGGER.info(">>> {}", HpsysApplication.class.getSimpleName().toUpperCase() + " STARTING SUCCESS");
    }
}
