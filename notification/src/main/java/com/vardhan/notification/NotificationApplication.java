package com.vardhan.notification;

import com.vardhan.amqp.RabbitMQMessageProducer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(
        scanBasePackages = {
        "com.vardhan.notification",
        "com.vardhan.amqp"
    }
)
public class NotificationApplication {

  public static void main(String[] args) {
    SpringApplication.run(NotificationApplication.class, args);
  }

//  @Bean
//  CommandLineRunner commandLineRunner(RabbitMQMessageProducer producer, NotificationConfig config) {
//    return args -> {
//      producer.publish(
//              new Person("Vardhan", 30)
//              , config.getInternalExchange()
//              , config.getInternalNotificationRoutingKey());
//    };
//  }
//
//  record Person(String name, int age) {}
}
