package com.example.scsfunctions;

import com.example.scsfunctions.dto.Customer;
import com.example.scsfunctions.dto.Item;
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

    Message<Customer> message = MessageBuilder.withPayload(customer).build();

    inputDestination.send(message, "processFile-in-0");

    Product product = new ObjectMapper().readValue(outputDestination.receive(100, "processFile-out-0").getPayload(), Product.class);
    Assertions.assertNotNull(product);
    Assertions.assertEquals(customer.getName(), product.getName());
  }

  @Test
  void parseProduct() throws IOException {

    Product product = new Product();
    product.setName("Bob");
    product.setOrigin("web");

    Message<Product> message = MessageBuilder.withPayload(product).build();

    inputDestination.send(message, "parseProduct-in-0");

    Order order = new ObjectMapper().readValue(outputDestination.receive(100, "parseProduct-out-0").getPayload(), Order.class);
    Assertions.assertNotNull(order);
    Assertions.assertEquals(product.getName(), order.getName());
  }

  @Test
  void parseOrder() throws IOException {

    Order order = new Order();
    order.setName("order1");

    Message<Order> message = MessageBuilder.withPayload(order).build();
    inputDestination.send(message, "parseOrder-in-0");

    Item item = new ObjectMapper().readValue(outputDestination.receive(100, "parseOrder-out-0").getPayload(), Item.class);
    Assertions.assertNotNull(item);
    Assertions.assertEquals(item.getName(), order.getName());
    Assertions.assertEquals(item.getPrice(), 100);
  }


}
