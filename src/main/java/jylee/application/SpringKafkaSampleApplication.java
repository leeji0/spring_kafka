package jylee.application;

import jylee.kafka.KafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
@ComponentScan("jylee")
public class SpringKafkaSampleApplication {
    @Autowired
    KafkaProducer kafkaProducer;

    @RequestMapping(method = RequestMethod.GET, path = "/send")
    String send() {
        kafkaProducer.sendMessage("medium-jylee-topic", "message key medium", "medium -> jylee message");
        kafkaProducer.sendMessage("company-jylee-topic", "message key company", "company -> jylee message");
        return "Kafka Produce!!!";
    }

    public static void main(String[] args) {

        SpringApplication.run(SpringKafkaSampleApplication.class, args);
    }
}
