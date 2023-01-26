package com.vardhan.customer;

import com.vardhan.amqp.RabbitMQMessageProducer;
import com.vardhan.clients.fraud.FraudCheckResponse;
import com.vardhan.clients.fraud.FraudClient;
import com.vardhan.clients.notification.NotificationClient;
import com.vardhan.clients.notification.NotificationRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
public class CustomerService {

  private final CustomerRepository customerRepository;
  private final RestTemplate restTemplate;
  private final FraudClient fraudClient;
  private final RabbitMQMessageProducer rabbitMQMessageProducer;

  public void registerCustomer(CustomerRegistrationRequest registrationRequest) {
    Customer customer = Customer.builder()
        .firstName(registrationRequest.firstName())
        .lastName(registrationRequest.lastName())
        .email(registrationRequest.email())
        .build();
    // TODO: check if email is valid
    // TODO: check if email is not taken
    customerRepository.saveAndFlush(customer);
    // TODO: check if fraudster
    FraudCheckResponse fraudCheckResponse = fraudClient.isFraudster(customer.getId());

    if (fraudCheckResponse.isFraudster()) {
      throw new IllegalStateException(
          String.format("The customer with id: %s is fraudster", customer.getId()));
    }

    NotificationRequest notificationRequest = new NotificationRequest(
            customer.getId(),
            customer.getEmail(),
            String.format("Hi %s, welcome to Govardhan's repository...",
                          customer.getFirstName())
    );
    rabbitMQMessageProducer.publish(notificationRequest,
                                    "internal.exchange",
                                    "internal.notification.routing-key");
  }
}
