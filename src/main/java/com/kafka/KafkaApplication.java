package com.kafka;

import com.kafka.dto.KafkaDto;
import com.kafka.service.KafkaConsumerService;
import com.kafka.service.KafkaProducerService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@SpringBootApplication
public class KafkaApplication {

	public static void main(String[] args)
	{
		SpringApplication app = new SpringApplication(KafkaApplication.class);
//		app.setDefaultProperties(Collections
//										 .singletonMap("server.port", "8088"));
		app.run(args);
	}
}
