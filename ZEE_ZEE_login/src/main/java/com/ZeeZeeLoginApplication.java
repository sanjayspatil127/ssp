package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;

@SpringBootApplication
@ComponentScan("com")
public class ZeeZeeLoginApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZeeZeeLoginApplication.class, args);
	}

}

//@Controller
//@EnableAutoConfiguration
//@ComponentScan(basePackages={"com.controller"})