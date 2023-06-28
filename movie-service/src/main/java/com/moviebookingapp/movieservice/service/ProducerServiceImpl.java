package com.moviebookingapp.movieservice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ProducerServiceImpl {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProducerServiceImpl.class);
	public static final String Topic = "Book_Tickets";

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	public void bookTicketMessage(String message) {
		LOGGER.info("Producer START -- Message Send To Consumer");
		this.kafkaTemplate.send(Topic, message);
	}

}
