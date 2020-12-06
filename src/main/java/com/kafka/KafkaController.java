package com.kafka;

import com.kafka.dto.KafkaDto;
import com.kafka.service.KafkaConsumerService;
import com.kafka.service.KafkaProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class KafkaController
{
	@Autowired
	KafkaProducerService kafkaProducerService;

	@RequestMapping("/write")
	public ResponseEntity<String> doWrite(@RequestParam(required = true, defaultValue = "firstTopic") String topic, @RequestParam( required = false) String key, @RequestParam(required = true) String value)
	{
		kafkaProducerService.write(topic, key, value);
		return ResponseEntity.ok().body("success");
	}

	@RequestMapping("/read")
	public List<KafkaDto> doRead(@RequestParam(defaultValue = "earliest") String offset, @RequestParam(required = true, defaultValue = "firstTopic") String topic)
	{
		return new KafkaConsumerService(UUID.randomUUID().toString()+"_group",offset).read(topic);
	}
}
