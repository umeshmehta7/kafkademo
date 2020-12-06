package com.kafka.service;

import com.kafka.MfKafkaConstants;
import com.kafka.dto.KafkaDto;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.*;

@Service
public class KafkaConsumerService
{
    private static Logger LOG = LoggerFactory.getLogger(KafkaConsumerService.class.getName());

    KafkaConsumer<String, String> consumer = null;

    private KafkaConsumerService()
    {}

    public KafkaConsumerService(String groupId, String offsetConfig)
    {
        //Consumer config
        Properties properties = new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, MfKafkaConstants.BOOT_STRAP_SERVER);
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG,groupId);
        properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,offsetConfig);

        //create consumer
        consumer = new KafkaConsumer<String, String>(properties);
    }

    public List<KafkaDto> read(String topic)
    {
        //subscribe to topic
        consumer.subscribe(Collections.singletonList(topic));

        List<KafkaDto> list = new ArrayList<>();
            ConsumerRecords<String, String> poll = consumer.poll(Duration.ofMillis(100));
            for (ConsumerRecord record : poll)
            {
                list.add(convert(record));
                LOG.info(
                        "topic: " + record.topic() + ", partition: " + record.partition() + ", value: " + record.value());
            }
        Collections.reverse(list);
        return list;
    }

    private KafkaDto convert(final ConsumerRecord consumerRecord)
    {
        KafkaDto kafkaDto = new KafkaDto();
        kafkaDto.setTopic(consumerRecord.topic());
        if(consumerRecord.key() != null)
            kafkaDto.setKey(consumerRecord.key().toString());
        kafkaDto.setOffset(String.valueOf(consumerRecord.offset()));
        kafkaDto.setPartition(String.valueOf(consumerRecord.partition()));
        kafkaDto.setTimeStamp(new Date(consumerRecord.timestamp()).toString());
        kafkaDto.setValue(consumerRecord.value().toString());
        return kafkaDto;
    }
}
