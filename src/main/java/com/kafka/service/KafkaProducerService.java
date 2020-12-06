package com.kafka.service;

import com.kafka.MfKafkaConstants;
import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Properties;

@Service
public class KafkaProducerService
{
    private static Logger LOG = LoggerFactory.getLogger(KafkaProducerService.class.getName());
    KafkaProducer<String, String> producer = null;
    public KafkaProducerService()
    {
        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, MfKafkaConstants.BOOT_STRAP_SERVER);
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,StringSerializer.class.getName());

        //create producer
        producer = new KafkaProducer<String, String>(properties);
    }
    public void write(String topicName, String key, String message)
    {
        //create data
        ProducerRecord<String, String>
                record = (StringUtils.isNotBlank(key))?new ProducerRecord<String, String>(topicName, key, message):new ProducerRecord<String, String>(topicName, message);

        //send record
        producer.send(record, new Callback() {
            @Override
            public void onCompletion(RecordMetadata metadata, Exception exception)
            {
                if(metadata != null)
                    LOG.info(MessageFormat.format("Topic: {0}, partition: {1}, offset: {2}", metadata.topic(), metadata.partition(), metadata.offset()));
            }
        });

        producer.flush();

        LOG.info("data sent and connection closed");
    }

}
