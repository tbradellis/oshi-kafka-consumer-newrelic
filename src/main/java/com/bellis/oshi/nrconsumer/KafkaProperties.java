package com.bellis.oshi.nrconsumer;

public class KafkaProperties {


    public static final String TOPIC = "sensor-data";
    public static final String HARDWARE_GROUP = "nr-group";

    public static final String KAFKA_SERVER_URL = "localhost";
    public static final int KAFKA_SERVER_PORT = 9092;
    public static final int KAFKA_PRODUCER_BUFFER_SIZE = 64 * 1024;
    public static final int CONNECTION_TIMEOUT = 100000;

    private KafkaProperties() {}

}