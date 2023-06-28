package com.auth.authentication.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;



@Service
public class ConsumerServiceImpl {

	private static final Logger LOGGER = LoggerFactory.getLogger(ConsumerServiceImpl.class);
	
	@KafkaListener(topics="Book_Tickets", groupId="mygroup")
	public void consumeFromTopic(String message)
	{
		System.out.println("Message is received :"+message);
		LOGGER.info(String.format("Message Received From Producer -> %s", message));
	}
}
