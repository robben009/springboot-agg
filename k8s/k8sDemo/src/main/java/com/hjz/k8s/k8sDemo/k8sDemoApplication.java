package com.hjz.k8s.k8sDemo;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class k8sDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(k8sDemoApplication.class, args);
    }


    @RestController
    public class GreetingController {

        @GetMapping("/greeting")
        public String greeting() {
            return "Hello, World!";
        }
    }

}