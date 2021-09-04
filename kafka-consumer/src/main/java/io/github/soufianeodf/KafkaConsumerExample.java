package io.github.soufianeodf;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.avro.Schema;
import org.apache.avro.io.BinaryDecoder;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.common.serialization.ByteArrayDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class KafkaConsumerExample {
    private static final String TOPIC = "tracking";
    private static final String BOOTSTRAP_SERVERS = "localhost:9092";


    public static void main(String[] args) {
        try {
            runConsumerAvro();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void runConsumerAvro() throws IOException {
        Consumer<String, byte[]> consumer = createConsumer();
        while (true) {
            ConsumerRecords<String, byte[]> records = consumer.poll(Duration.ofMillis(100));
            Schema.Parser parser = new Schema.Parser();
            final Schema schema = parser.parse(KafkaConsumerExample.class.getClassLoader().getResourceAsStream("MyEventRecord.avsc"));
            records.forEach(record -> {
                SpecificDatumReader<Object> datumReader = new SpecificDatumReader<>(schema);
                ByteArrayInputStream is = new ByteArrayInputStream(record.value());
                BinaryDecoder binaryDecoder = DecoderFactory.get().binaryDecoder(is, null);
                try {
                    Object log = datumReader.read(null, binaryDecoder);

                    Gson gson = new GsonBuilder().setPrettyPrinting().create();
                    UserInfo userInfo = gson.fromJson(log.toString(), UserInfo.class);
                    System.out.println(gson.toJson(userInfo));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    private static Consumer<String, byte[]> createConsumer() {
        final Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "KafkaExampleConsumer");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ByteArrayDeserializer.class);

        // Create the consumer using props.
        final Consumer<String, byte[]> consumer = new KafkaConsumer<>(props);

        // Subscribe to the topic.
        consumer.subscribe(Collections.singletonList(TOPIC));
        return consumer;
    }
}
