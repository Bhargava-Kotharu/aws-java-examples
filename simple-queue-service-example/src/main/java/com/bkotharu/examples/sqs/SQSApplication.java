package com.bkotharu.examples.sqs;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SQSApplication {

	public static void main(String[] args) {
		SpringApplication.run(SQSApplication.class, args);
	}
}
