package com.example.learnForAll;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableDubbo(scanBasePackages = "com.example.learnForAll.dubbo.impl")
public class LearnForAllApplication {

	public static void main(String[] args) {
		try {
			SpringApplication.run(LearnForAllApplication.class, args);
			System.out.println("===========================");
		}catch (Exception err){
			System.out.println(err.fillInStackTrace());
		}

	}

}
