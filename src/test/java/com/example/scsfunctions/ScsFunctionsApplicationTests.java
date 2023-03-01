package com.example.scsfunctions;

import com.example.scsfunctions.dto.Customer;
import com.example.scsfunctions.dto.Order;
import com.example.scsfunctions.dto.Product;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.binder.test.InputDestination;
import org.springframework.cloud.stream.binder.test.OutputDestination;
import org.springframework.cloud.stream.binder.test.TestChannelBinderConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import java.io.IOException;

@Slf4j
@SpringBootTest
@Import(TestChannelBinderConfiguration.class)
class ScsFunctionsApplicationTests {

  @Autowired
  private InputDestination inputDestination;

  @Autowired
  private OutputDestination outputDestination;

  @Test
  void processFile() throws IOException {

    Customer customer = new Customer();
    customer.setFirstName("Olivier");
    customer.setName("Smith");

    //Message<Customer> message = MessageBuilder.withPayload(customer).setHeader("spring.cloud.function.definition", "processFile").build();
    Message<Customer> message = MessageBuilder.withPayload(customer).build();

    inputDestination.send(message, "processFile-in-0");

    Product product = new ObjectMapper().readValue(outputDestination.receive().getPayload(), Product.class);
    Assertions.assertNotNull(product);
    Assertions.assertEquals(customer.getName(), product.getName());
  }

  @Test
  void parseProduct() throws IOException {

    Customer customer = new Customer();
    customer.setFirstName("Olivier");
    customer.setName("Smith");

    Product product = new Product();
    product.setName("Bob");
    product.setOrigin("web");

    Message<Product> message = MessageBuilder.withPayload(product).build();

    inputDestination.send(message, "parseProduct-in-0");

    Order order = new ObjectMapper().readValue(outputDestination.receive().getPayload(), Order.class);
    Assertions.assertNotNull(order);
    Assertions.assertNotNull(product.getName(), order.getName());


  }


}
