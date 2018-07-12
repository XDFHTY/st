package com.cj.stserver;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan({"com.cj.*.mapper"})
@ComponentScan(basePackages = {"com.cj"})
public class StServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(StServerApplication.class, args);
	}
}
