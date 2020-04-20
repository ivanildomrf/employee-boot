package br.com.imrf.employee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"br.com.imrf"})
public class EmployeeBootApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmployeeBootApplication.class, args);
	}

}
