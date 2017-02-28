package com.ibm.caseteam.monolith.bank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class BankApplicationUI {

    public static void main(String[] args) {
        SpringApplication.run(BankApplicationUI.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
      return new RestTemplate();
    }

}
