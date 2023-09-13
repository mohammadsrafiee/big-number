package com.example.bignumber;

import com.example.bignumber.number.BigIntegerNumber;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        System.out.println(
                new BigIntegerNumber("1398")
                        .sum(new BigIntegerNumber("1355")));
        SpringApplication.run(Application.class, args);
    }

}
