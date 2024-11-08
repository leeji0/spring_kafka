# kafkaSample
 - 이 프로젝트는 Spring Boot Kafka Template 샘플 프로젝트 입니다.
 
 
# 사용 방법 
* Spring Boot 서버를 실행합니다. 
``` 
mvn spring-boot:run 
```
* http://localhost:8080/send 주소를 브라우저로 접속 하면 kafka Producer 가 호출 되고 이를 Consumer 가 받아 로그를 남깁니다. 

```$xslt
Sent message=[medium -> jylee message] with offset=[2]
Sent message=[company -> jylee message] with offset=[2]
Topic: [company-jylee-topic] messageKey Message: [message key company]
Topic: [medium-jylee-topic] messageKey Message: [message key medium]
Topic: [company-jylee-topic] Received Message: [company -> jylee message] from partition: [0]
Topic: [medium-jylee-topic] Received Message: [medium -> jylee message] from partition: [0]
```

# spring-kafka-single-compose.yml
* spring-kafka-single-compose.yml : single broker 기반.
  * KAFKA_ADVERTISED_LISTENERS 는 리스너를 암호화 유무에 따라 다르게 표현된다.
  ```$xslt
  암호화 X : KAFKA_ADVERTISED_LISTENERS: PLAINTEXT://kafka1:9092
  암호화 O : KAFKA_ADVERTISED_LISTENERS: LISTENER_DOCKER_INTERNAL://kafka1:19092,LISTENER_DOCKER_EXTERNAL://${DOCKER_HOST_IP:-127.0.0.1}:9092
  ```
  * 암호화 여부와 상관없이 docker 는 구동 되지만 암호화가 안된 plaintext 일 경우 springBoot 에서 연동이 되지 않음.
  * single broker 일 경우 consumer connectiong 도 정상적.

  ```$xslt
  2024-11-08 18:52:30.339  INFO 53770 --- [           main] o.a.kafka.common.utils.AppInfoParser     : Kafka version : 2.1.1
  2024-11-08 18:52:30.339  INFO 53770 --- [           main] o.a.kafka.common.utils.AppInfoParser     : Kafka commitId : 21234bee31165527
  2024-11-08 18:52:30.410  INFO 53770 --- [           main] org.apache.kafka.clients.Metadata        : Cluster ID: 61TFzWGNRdWpKQ17g3rJhQ
  2024-11-08 18:52:31.409  INFO 53770 --- [ntainer#1-0-C-1] o.a.k.c.c.internals.AbstractCoordinator  : [Consumer clientId=consumer-4, groupId=company-jylee-group] Discovered group coordinator 127.0.0.1:9092 (id: 2147483646 rack: null)
  2024-11-08 18:52:31.410  INFO 53770 --- [ntainer#1-0-C-1] o.a.k.c.c.internals.ConsumerCoordinator  : [Consumer clientId=consumer-4, groupId=company-jylee-group] Revoking previously assigned partitions []
  2024-11-08 18:52:31.410  INFO 53770 --- [ntainer#1-0-C-1] o.s.k.l.KafkaMessageListenerContainer    : partitions revoked: []
  2024-11-08 18:52:31.410  INFO 53770 --- [ntainer#1-0-C-1] o.a.k.c.c.internals.AbstractCoordinator  : [Consumer clientId=consumer-4, groupId=company-jylee-group] (Re-)joining group
  2024-11-08 18:52:31.452  INFO 53770 --- [ntainer#0-0-C-1] o.a.k.c.c.internals.AbstractCoordinator  : [Consumer clientId=consumer-2, groupId=medium-jylee-group] Discovered group coordinator 127.0.0.1:9092 (id: 2147483646 rack: null)
  2024-11-08 18:52:31.453  INFO 53770 --- [ntainer#0-0-C-1] o.a.k.c.c.internals.ConsumerCoordinator  : [Consumer clientId=consumer-2, groupId=medium-jylee-group] Revoking previously assigned partitions []
  2024-11-08 18:52:31.453  INFO 53770 --- [ntainer#0-0-C-1] o.s.k.l.KafkaMessageListenerContainer    : partitions revoked: []
  2024-11-08 18:52:31.453  INFO 53770 --- [ntainer#0-0-C-1] o.a.k.c.c.internals.AbstractCoordinator  : [Consumer clientId=consumer-2, groupId=medium-jylee-group] (Re-)joining group
  2024-11-08 18:52:34.461  INFO 53770 --- [ntainer#1-0-C-1] o.a.k.c.c.internals.AbstractCoordinator  : [Consumer clientId=consumer-4, groupId=company-jylee-group] Successfully joined group with generation 1
  2024-11-08 18:52:34.463  INFO 53770 --- [ntainer#1-0-C-1] o.a.k.c.c.internals.ConsumerCoordinator  : [Consumer clientId=consumer-4, groupId=company-jylee-group] Setting newly assigned partitions [company-jylee-topic-0]
  2024-11-08 18:52:34.469  INFO 53770 --- [ntainer#0-0-C-1] o.a.k.c.c.internals.AbstractCoordinator  : [Consumer clientId=consumer-2, groupId=medium-jylee-group] Successfully joined group with generation 1
  2024-11-08 18:52:34.469  INFO 53770 --- [ntainer#0-0-C-1] o.a.k.c.c.internals.ConsumerCoordinator  : [Consumer clientId=consumer-2, groupId=medium-jylee-group] Setting newly assigned partitions [medium-jylee-topic-0]
  2024-11-08 18:52:34.482  INFO 53770 --- [ntainer#0-0-C-1] o.a.k.c.consumer.internals.Fetcher       : [Consumer clientId=consumer-2, groupId=medium-jylee-group] Resetting offset for partition medium-jylee-topic-0 to offset 0.
  2024-11-08 18:52:34.482  INFO 53770 --- [ntainer#1-0-C-1] o.a.k.c.consumer.internals.Fetcher       : [Consumer clientId=consumer-4, groupId=company-jylee-group] Resetting offset for partition company-jylee-topic-0 to offset 0.
  2024-11-08 18:52:34.482  INFO 53770 --- [ntainer#0-0-C-1] o.s.k.l.KafkaMessageListenerContainer    : partitions assigned: [medium-jylee-topic-0]
  2024-11-08 18:52:34.482  INFO 53770 --- [ntainer#1-0-C-1] o.s.k.l.KafkaMessageListenerContainer    : partitions assigned: [company-jylee-topic-0]
  ```
  * http://localhost:8080/send 로 접속 시 producer에서 topic 보내고, consumer 에서 topic 받는 부분 확인 가능.
  ```$xslt
  2024-11-08 18:57:30.871  INFO 53770 --- [nio-8080-exec-1] o.a.kafka.common.utils.AppInfoParser     : Kafka version : 2.1.1
  2024-11-08 18:57:30.871  INFO 53770 --- [nio-8080-exec-1] o.a.kafka.common.utils.AppInfoParser     : Kafka commitId : 21234bee31165527
  2024-11-08 18:57:30.886  INFO 53770 --- [ad | producer-1] org.apache.kafka.clients.Metadata        : Cluster ID: 61TFzWGNRdWpKQ17g3rJhQ
  2024-11-08 18:57:30.918  INFO 53770 --- [ad | producer-1] jylee.kafka.KafkaProducer                : Sent message=[medium -> jylee message] with offset=[0]
  2024-11-08 18:57:30.918  INFO 53770 --- [ad | producer-1] jylee.kafka.KafkaProducer                : Sent message=[company -> jylee message] with offset=[0]
  2024-11-08 18:57:30.924  INFO 53770 --- [ntainer#0-0-C-1] jylee.kafka.KafkaConsumer                : Topic: [medium-jylee-topic] messageKey Message: [message key medium]
  2024-11-08 18:57:30.924  INFO 53770 --- [ntainer#0-0-C-1] jylee.kafka.KafkaConsumer                : Topic: [medium-jylee-topic] Received Message: [medium -> jylee message] from partition: [0]
  2024-11-08 18:57:30.924  INFO 53770 --- [ntainer#1-0-C-1] jylee.kafka.KafkaConsumer                : Topic: [company-jylee-topic] messageKey Message: [message key company]
  2024-11-08 18:57:30.924  INFO 53770 --- [ntainer#1-0-C-1] jylee.kafka.KafkaConsumer                : Topic: [company-jylee-topic] Received Message: [company -> jylee message] from partition: [0]
  ```
  * 터미널에서 특정 topic 조회 시
  ```$xslt
    $ docker-compose exec kafka1 kafka-topics --describe --topic medium-jylee-topic --bootstrap-server kafka1:9092
    > Topic: medium-jylee-topic	TopicId: m0EUlB2UTO23UYQJDsXFtw	PartitionCount: 1	ReplicationFactor: 1	Configs:
    > Topic: medium-jylee-topic	Partition: 0	Leader: 1	Replicas: 1	Isr: 1
  ```

---

# spring-kafka-multi-compose.yml
* spring-kafka-multi-compose.yml : multi broker 기반.
  * KAFKA_ADVERTISED_LISTENERS 는 리스너를 암호화 유무에 따라 다르게 표현된다. 
  * kafka2 와 kafka3 의 port 가 "9093:9092" 와 "9093:9093" 에 따라서 KAFKA_ADVERTISED_LISTENERS 가 달라지는데 두가지의 차이를 잘 모르겠음....
  * kafka2, kafka3의 port가 "9093:9092", "9094:9092" 이면서 리스너를 암호화 했을 경우 오류 발생. (암호화 안하면 오류 발생하지 않음)
  ```$xslt
  springkafkasample-kafka1-1     | java.lang.IllegalArgumentException: requirement failed: Configured end points 127.0.0.1:9092 in advertised listeners are already registered by broker 3
  ```
  * 암호화 여부와 상관없이 docker 는 구동 되지만 암호화가 안된 plaintext 일 경우 springBoot 에서 연동이 되지 않음.
  * multi broker 이면서 리스너가 암호화 되었을 경우, consumer connectiong 도 정상적. (이때 kafka2, kafka3의 port는 "9093:9093", "9094:9094")
  ```$xslt
  2024-11-08 19:09:09.634  INFO 54529 --- [           main] o.a.kafka.common.utils.AppInfoParser     : Kafka version : 2.1.1
  2024-11-08 19:09:09.634  INFO 54529 --- [           main] o.a.kafka.common.utils.AppInfoParser     : Kafka commitId : 21234bee31165527
  2024-11-08 19:09:09.634  INFO 54529 --- [           main] o.s.s.c.ThreadPoolTaskScheduler          : Initializing ExecutorService
  2024-11-08 19:09:09.654  INFO 54529 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 8080 (http) with context path ''
  2024-11-08 19:09:09.657  INFO 54529 --- [           main] j.a.SpringKafkaSampleApplication         : Started SpringKafkaSampleApplication in 1.45 seconds (JVM running for 1.871)
  2024-11-08 19:09:09.762  INFO 54529 --- [ntainer#1-0-C-1] org.apache.kafka.clients.Metadata        : Cluster ID: 32bX10LKQgiVy3VdOZNxcw
  2024-11-08 19:09:10.212  INFO 54529 --- [ntainer#0-0-C-1] o.a.k.c.c.internals.AbstractCoordinator  : [Consumer clientId=consumer-2, groupId=medium-jylee-group] Discovered group coordinator 127.0.0.1:9092 (id: 2147483646 rack: null)
  2024-11-08 19:09:10.214  INFO 54529 --- [ntainer#0-0-C-1] o.a.k.c.c.internals.ConsumerCoordinator  : [Consumer clientId=consumer-2, groupId=medium-jylee-group] Revoking previously assigned partitions []
  2024-11-08 19:09:10.214  INFO 54529 --- [ntainer#0-0-C-1] o.s.k.l.KafkaMessageListenerContainer    : partitions revoked: []
  2024-11-08 19:09:10.214  INFO 54529 --- [ntainer#0-0-C-1] o.a.k.c.c.internals.AbstractCoordinator  : [Consumer clientId=consumer-2, groupId=medium-jylee-group] (Re-)joining group
  2024-11-08 19:09:10.232  INFO 54529 --- [ntainer#1-0-C-1] o.a.k.c.c.internals.AbstractCoordinator  : [Consumer clientId=consumer-4, groupId=company-jylee-group] Discovered group coordinator 127.0.0.1:9092 (id: 2147483646 rack: null)
  2024-11-08 19:09:10.233  INFO 54529 --- [ntainer#1-0-C-1] o.a.k.c.c.internals.ConsumerCoordinator  : [Consumer clientId=consumer-4, groupId=company-jylee-group] Revoking previously assigned partitions []
  2024-11-08 19:09:10.233  INFO 54529 --- [ntainer#1-0-C-1] o.s.k.l.KafkaMessageListenerContainer    : partitions revoked: []
  2024-11-08 19:09:10.233  INFO 54529 --- [ntainer#1-0-C-1] o.a.k.c.c.internals.AbstractCoordinator  : [Consumer clientId=consumer-4, groupId=company-jylee-group] (Re-)joining group
  2024-11-08 19:09:13.295  INFO 54529 --- [ntainer#1-0-C-1] o.a.k.c.c.internals.AbstractCoordinator  : [Consumer clientId=consumer-4, groupId=company-jylee-group] Successfully joined group with generation 1
  2024-11-08 19:09:13.295  INFO 54529 --- [ntainer#0-0-C-1] o.a.k.c.c.internals.AbstractCoordinator  : [Consumer clientId=consumer-2, groupId=medium-jylee-group] Successfully joined group with generation 1
  2024-11-08 19:09:13.297  INFO 54529 --- [ntainer#1-0-C-1] o.a.k.c.c.internals.ConsumerCoordinator  : [Consumer clientId=consumer-4, groupId=company-jylee-group] Setting newly assigned partitions [company-jylee-topic-0]
  2024-11-08 19:09:13.297  INFO 54529 --- [ntainer#0-0-C-1] o.a.k.c.c.internals.ConsumerCoordinator  : [Consumer clientId=consumer-2, groupId=medium-jylee-group] Setting newly assigned partitions [medium-jylee-topic-0]
  2024-11-08 19:09:13.314  INFO 54529 --- [ntainer#1-0-C-1] o.a.k.c.consumer.internals.Fetcher       : [Consumer clientId=consumer-4, groupId=company-jylee-group] Resetting offset for partition company-jylee-topic-0 to offset 0.
  2024-11-08 19:09:13.314  INFO 54529 --- [ntainer#0-0-C-1] o.a.k.c.consumer.internals.Fetcher       : [Consumer clientId=consumer-2, groupId=medium-jylee-group] Resetting offset for partition medium-jylee-topic-0 to offset 0.
  2024-11-08 19:09:13.314  INFO 54529 --- [ntainer#1-0-C-1] o.s.k.l.KafkaMessageListenerContainer    : partitions assigned: [company-jylee-topic-0]
  2024-11-08 19:09:13.314  INFO 54529 --- [ntainer#0-0-C-1] o.s.k.l.KafkaMessageListenerContainer    : partitions assigned: [medium-jylee-topic-0]
  ```
  * http://localhost:8080/send 로 접속 시 producer에서 topic 보내고, consumer 에서 topic 받는 부분 확인 가능.
  ```$xslt
  2024-11-08 18:33:27.469  INFO 52410 --- [nio-8080-exec-1] o.a.kafka.common.utils.AppInfoParser     : Kafka version : 2.1.1
  2024-11-08 18:33:27.469  INFO 52410 --- [nio-8080-exec-1] o.a.kafka.common.utils.AppInfoParser     : Kafka commitId : 21234bee31165527
  2024-11-08 18:33:27.489  INFO 52410 --- [ad | producer-1] org.apache.kafka.clients.Metadata        : Cluster ID: BdvH4IEzR6i7x_sL95g9Mw
  2024-11-08 18:33:27.547  INFO 52410 --- [ad | producer-1] jylee.kafka.KafkaProducer                : Sent message=[medium -> jylee message] with offset=[0]
  2024-11-08 18:33:27.551  INFO 52410 --- [ntainer#0-0-C-1] jylee.kafka.KafkaConsumer                : Topic: [medium-jylee-topic] messageKey Message: [message key medium]
  2024-11-08 18:33:27.551  INFO 52410 --- [ntainer#0-0-C-1] jylee.kafka.KafkaConsumer                : Topic: [medium-jylee-topic] Received Message: [medium -> jylee message] from partition: [0]
  2024-11-08 18:33:27.552  INFO 52410 --- [ad | producer-1] jylee.kafka.KafkaProducer                : Sent message=[company -> jylee message] with offset=[0]
  2024-11-08 18:33:27.557  INFO 52410 --- [ntainer#1-0-C-1] jylee.kafka.KafkaConsumer                : Topic: [company-jylee-topic] messageKey Message: [message key company]
  2024-11-08 18:33:27.557  INFO 52410 --- [ntainer#1-0-C-1] jylee.kafka.KafkaConsumer                : Topic: [company-jylee-topic] Received Message: [company -> jylee message] from partition: [0]
  ```
  * 터미널에서 특정 topic 조회 시
  ```$xslt
    $ docker-compose exec kafka1 kafka-topics --describe --topic medium-jylee-topic --bootstrap-server kafka1:9092
    > [2024-11-08 09:27:25,614] WARN [AdminClient clientId=adminclient-1] Connection to node 2 (/127.0.0.1:9093) could not be established. Node may not be available. (org.apache.kafka.clients.NetworkClient)
      [2024-11-08 09:27:25,629] WARN [AdminClient clientId=adminclient-1] Connection to node 3 (/127.0.0.1:9094) could not be established. Node may not be available. (org.apache.kafka.clients.NetworkClient)
      [2024-11-08 09:27:25,682] WARN [AdminClient clientId=adminclient-1] Connection to node 3 (/127.0.0.1:9094) could not be established. Node may not be available. (org.apache.kafka.clients.NetworkClient)
      [2024-11-08 09:27:25,774] WARN [AdminClient clientId=adminclient-1] Connection to node 3 (/127.0.0.1:9094) could not be established. Node may not be available. (org.apache.kafka.clients.NetworkClient)
  ```

---
# spring-kafka-compose.yml
  * spring-kafka-compose.yml : github에서 그대로 받은 docker-compose.yml 이다. springApplication 과 별개의 소스이기에 consumer 에 connection 이 안붙는 문제 발생
  ```$xslt
  org.springframework.context.ApplicationContextException: Failed to start bean 'org.springframework.kafka.config.internalKafkaListenerEndpointRegistry'; nested exception is java.lang.IllegalStateException: No entry found for connection 3
	at org.springframework.context.support.DefaultLifecycleProcessor.doStart(DefaultLifecycleProcessor.java:185) ~[spring-context-5.1.16.RELEASE.jar:5.1.16.RELEASE]
	at org.springframework.context.support.DefaultLifecycleProcessor.access$200(DefaultLifecycleProcessor.java:53) ~[spring-context-5.1.16.RELEASE.jar:5.1.16.RELEASE]
	at org.springframework.context.support.DefaultLifecycleProcessor$LifecycleGroup.start(DefaultLifecycleProcessor.java:360) ~[spring-context-5.1.16.RELEASE.jar:5.1.16.RELEASE]
	at org.springframework.context.support.DefaultLifecycleProcessor.startBeans(DefaultLifecycleProcessor.java:158) ~[spring-context-5.1.16.RELEASE.jar:5.1.16.RELEASE]
	at org.springframework.context.support.DefaultLifecycleProcessor.onRefresh(DefaultLifecycleProcessor.java:122) ~[spring-context-5.1.16.RELEASE.jar:5.1.16.RELEASE]
	at org.springframework.context.support.AbstractApplicationContext.finishRefresh(AbstractApplicationContext.java:894) ~[spring-context-5.1.16.RELEASE.jar:5.1.16.RELEASE]
	at org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext.finishRefresh(ServletWebServerApplicationContext.java:162) ~[spring-boot-2.1.15.RELEASE.jar:2.1.15.RELEASE]
	at org.springframework.context.support.AbstractApplicationContext.refresh(AbstractApplicationContext.java:553) ~[spring-context-5.1.16.RELEASE.jar:5.1.16.RELEASE]
	at org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext.refresh(ServletWebServerApplicationContext.java:141) ~[spring-boot-2.1.15.RELEASE.jar:2.1.15.RELEASE]
	at org.springframework.boot.SpringApplication.refresh(SpringApplication.java:744) ~[spring-boot-2.1.15.RELEASE.jar:2.1.15.RELEASE]
	at org.springframework.boot.SpringApplication.refreshContext(SpringApplication.java:391) ~[spring-boot-2.1.15.RELEASE.jar:2.1.15.RELEASE]
	at org.springframework.boot.SpringApplication.run(SpringApplication.java:312) ~[spring-boot-2.1.15.RELEASE.jar:2.1.15.RELEASE]
	at org.springframework.boot.SpringApplication.run(SpringApplication.java:1215) ~[spring-boot-2.1.15.RELEASE.jar:2.1.15.RELEASE]
	at org.springframework.boot.SpringApplication.run(SpringApplication.java:1204) ~[spring-boot-2.1.15.RELEASE.jar:2.1.15.RELEASE]
	at jylee.application.SpringKafkaSampleApplication.main(SpringKafkaSampleApplication.java:28) ~[classes/:na]
  Caused by: java.lang.IllegalStateException: No entry found for connection 3
  ```

# Kafdrop
* http://localhost:9000 으로 접속하면 Kafka UI 확인 가능