package com.hjz.k8s.k8sDemo2;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class k8sDemo2Application {

    public static void main(String[] args) {
        SpringApplication.run(k8sDemo2Application.class, args);
    }


    @RestController
    public class GreetingController {

        @GetMapping("/k8sdemoApi2")
        public String k8sdemoApi2() {
            return "i am k8sdemoApi2";
        }
    }

}