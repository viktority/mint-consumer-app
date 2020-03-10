package com.viktority.mint.config.controller;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.viktority.mint.models.Payload;

@RestController
@RequestMapping("/card-scheme")
public class CardSchemeController {

	@KafkaListener(topics = "com.ng.vela.even.card_verified")
	public void getFromKafka(Payload payload) {
		System.out.println(payload.toString());
	}

}
