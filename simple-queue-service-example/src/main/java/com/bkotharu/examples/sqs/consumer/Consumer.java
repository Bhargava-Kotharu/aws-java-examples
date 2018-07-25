package com.bkotharu.examples.sqs.consumer;

import javax.jms.JMSException;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class Consumer {

	@JmsListener(destination = "example-queue")
	public void consumeMessage(String requestJSON) throws JMSException {
		System.out.println("Received ");

	}
}
