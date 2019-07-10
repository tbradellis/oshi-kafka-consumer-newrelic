package com.bellis.oshi.nrconsumer;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.FloatDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class SensorConsumer implements Runnable {

    private final KafkaConsumer consumer;

    public SensorConsumer() {

        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaProperties.KAFKA_SERVER_URL +
                KafkaProperties.KAFKA_SERVER_PORT);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, FloatDeserializer.class.getName());
        props.put(ConsumerConfig.GROUP_ID_CONFIG, KafkaProperties.HARDWARE_GROUP);

        consumer = new KafkaConsumer(props);
    }

    public void run() {
        try{
            consumer.subscribe(Collections.singleton(KafkaProperties.TOPIC));
            while(true){
                sendToNewRelic();
            }
        }finally {

        }

    }
    @Trace(dispatcher=true)
    public void sendToNewRelic(){
        ConsumerRecords<String,Float> records =
                consumer.poll(Duration.ofMillis(KafkaProperties.CONNECTION_TIMEOUT));
        for(ConsumerRecord record : records){
            //Do I want metrics or events?  Considering. Could do more with mosaics
            //if I use
            NewRelic.getAgent().getInsights().recordCustomEvent();

        }



    }
}
