package com.ohgiraffers.refactorial;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@MapperScan("com.ohgiraffers.refactorial.approval.model.dao")
public class ReFactorialApplication {

	public static void main(String[] args) {

		SpringApplication.run(ReFactorialApplication.class, args);
	}

}
