package com.consumer.service;

import java.util.ArrayList;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerApp {

	public void receiveMessage() {
	     Properties props = new Properties();
	     props.put("bootstrap.servers", "localhost:9092,localhost:9093");
	     props.put("group.id", "test");
	     props.put("enable.auto.commit", "true");
	     props.put("auto.commit.interval.ms", "1000");
	     props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
	     props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
	     
	     KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
	     
	     ArrayList<String> topics = new ArrayList<>();
	     topics.add("inputData");
	     consumer.subscribe(topics);
	     
	     try {
	    
		     while (true) {
		         ConsumerRecords<String, String> records = consumer.poll(100);
		         for (ConsumerRecord<String, String> record : records)
		             System.out.printf("offset = %d, key = %s, value = %s%n", record.offset(), record.key(), record.value());
		     }
	     } catch (Exception e) {
	    	 e.printStackTrace();
	     } finally {
	    	 consumer.close();
	     }
	}
	
}
