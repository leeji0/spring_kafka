package jylee.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

/**
 * Topic을 Receive (=Consume) 하는 Kafka Listener
 */
@Slf4j
@Component
public class KafkaConsumer {

    /*
    @KafkaLister : 자동으로 Kafka에서 Topic을 polling
    producer에서 setHeader() 로 설정한 Kafka Header 에 MessageKey를 Consumer에서 @Header로 받을 수 있음. 해당 방식으로 message, messageKey 받을 수 있음.
     */
    @KafkaListener(topics = "${spring.kafka.template.medium-jylee-topic}", containerFactory = "meJJKafkaListenerContainerFactory", groupId = "${spring.kafka.consumer.medium-jylee-group-id}")
    public void listenMeJJTopic(@Payload String message, @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition, @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String messageKey) throws Exception {
        log.info("Topic: [medium-jylee-topic] messageKey Message: [" + messageKey + "]");
        log.info("Topic: [medium-jylee-topic] Received Message: [" + message + "] from partition: [" + partition + "]");
    }

    @KafkaListener(topics = "${spring.kafka.template.company-jylee-topic}", containerFactory = "diJJKafkaListenerContainerFactory", groupId = "${spring.kafka.consumer.company-jylee-group-id}")
    public void listenDiJJTopic(
            @Payload String message,
            @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition, @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String messageKey) throws Exception {
        log.info("Topic: [company-jylee-topic] messageKey Message: [" + messageKey + "]");
        log.info("Topic: [company-jylee-topic] Received Message: [" + message + "] from partition: [" + partition + "]");
    }
}