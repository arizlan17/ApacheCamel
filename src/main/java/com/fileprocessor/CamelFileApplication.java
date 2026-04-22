package com.fileprocessor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CamelFileApplication {
    public static void main(String[] args) {
        SpringApplication.run(CamelFileApplication.class, args);

        synchronized (CamelFileApplication.class) {
            try {
                CamelFileApplication.class.wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}