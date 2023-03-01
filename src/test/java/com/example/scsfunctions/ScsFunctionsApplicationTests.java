package com.example.scsfunctions;

import com.example.scsfunctions.dto.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
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

//  public static final String BINDING_NAME_PROCESS_FILE_IN = "processFile-in-0";
//  public static final String BINDING_NAME_PARSE_PRODUCT_FRA_IN = ScsFunctionsApplication.BINDING_NAME_PARSE_PRODUCT_FRA_IN;
//  public static final String BINDING_NAME_PARSE_PRODUCT_FRA_OUT = "parseProductFRA-out-0";
//  public static final String BINDING_NAME_PARSE_PRODUCT_USA_IN = ScsFunctionsApplication.BINDING_NAME_PARSE_PRODUCT_USA_IN;
//  public static final String BINDING_NAME_PARSE_PRODUCT_USA_OUT = "parseProductUSA-out-0";
//  public static final String BINDING_NAME_PARSE_PRODUCT_IN = "parseProduct-in-0";
//  public static final String BINDING_NAME_PARSE_PRODUCT_OUT = "parseProduct-out-0";
//  public static final String BINDING_NAME_PARSE_ORDER_OUT = "parseOrder-out-0";
//  public static final String BINDING_NAME_PARSE_ORDER_IN = "parseOrder-in-0";

  public static final String BINDING_NAME_PROCESS_FILE_IN = "queue.processFile-in.messages";
  public static final String BINDING_NAME_PARSE_PRODUCT_FRA_IN = ScsFunctionsApplication.BINDING_NAME_PARSE_PRODUCT_FRA_IN;
  public static final String BINDING_NAME_PARSE_PRODUCT_FRA_OUT = "queue.productFRA-out.messages";
  public static final String BINDING_NAME_PARSE_PRODUCT_USA_IN = ScsFunctionsApplication.BINDING_NAME_PARSE_PRODUCT_USA_IN;
  public static final String BINDING_NAME_PARSE_PRODUCT_USA_OUT = "queue.parseProductUSA-out.messages";
  public static final String BINDING_NAME_PARSE_PRODUCT_IN = "queue.parseProduct-in.messages";
  public static final String BINDING_NAME_PARSE_PRODUCT_OUT = "queue.parseProduct-out.messages";
  public static final String BINDING_NAME_PARSE_ORDER_OUT = "queue.parseOrder-out.messages";
  public static final String BINDING_NAME_PARSE_ORDER_IN = "queue.parseOrder-in.messages";

  @Autowired
  private InputDestination inputDestination;

  @Autowired
  private OutputDestination outputDestination;


  @DisplayName("Given a customer with a french nationality, it must be processed by the parseProductFRA function")
  @Test
  void processFileFRA() throws IOException {

    Customer customer = new Customer();
    customer.setFirstName("Olivier");
    customer.setName("Dupond");
    customer.setNationality(Nationality.FRA);

    Message<Customer> message = MessageBuilder.withPayload(customer).build();

    inputDestination.send(message, BINDING_NAME_PROCESS_FILE_IN);

    Product product = new ObjectMapper().readValue(outputDestination.receive(100, ScsFunctionsApplicationTests.BINDING_NAME_PARSE_PRODUCT_FRA_OUT).getPayload(), Product.class);
    Assertions.assertNotNull(product);
    Assertions.assertEquals(customer.getName(), product.getName());
  }

  @DisplayName("Given a customer with an american nationality, it must be processed by the parseProductUSA function")
  @Test
  void processFileUSA() throws IOException {

    Customer customer = new Customer();
    customer.setFirstName("Olivier");
    customer.setName("Smith");
    customer.setNationality(Nationality.USA);

    Message<Customer> message = MessageBuilder.withPayload(customer).build();

    inputDestination.send(message, BINDING_NAME_PROCESS_FILE_IN);

    Product product = new ObjectMapper().readValue(outputDestination.receive(100, BINDING_NAME_PARSE_PRODUCT_USA_OUT).getPayload(), Product.class);
    Assertions.assertNotNull(product);
    Assertions.assertEquals(customer.getName(), product.getName());
  }

  @DisplayName("Given a product published, it must be processed by the parseProduct function")
  @Test
  void parseProduct() throws IOException {

    Product product = new Product();
    product.setName("Bob");
    product.setOrigin("web");

    Message<Product> message = MessageBuilder.withPayload(product).build();

    inputDestination.send(message, BINDING_NAME_PARSE_PRODUCT_IN);

    Order order = new ObjectMapper().readValue(outputDestination.receive(100, BINDING_NAME_PARSE_PRODUCT_OUT).getPayload(), Order.class);
    Assertions.assertNotNull(order);
    Assertions.assertEquals(product.getName(), order.getName());
  }


  @Test
  void parseProductFRA() throws IOException {

    Product product = new Product();
    product.setName("Bob");
    product.setOrigin("web");

    Message<Product> message = MessageBuilder.withPayload(product).build();

    inputDestination.send(message, BINDING_NAME_PARSE_PRODUCT_FRA_IN);

    Order order = new ObjectMapper().readValue(outputDestination.receive(100, BINDING_NAME_PARSE_PRODUCT_FRA_OUT).getPayload(), Order.class);
    Assertions.assertNotNull(order);
    Assertions.assertEquals(product.getName(), order.getName());
  }

  @Test
  void parseProductUSA() throws IOException {

    Product product = new Product();
    product.setName("Bob");
    product.setOrigin("web");

    Message<Product> message = MessageBuilder.withPayload(product).build();

    inputDestination.send(message, BINDING_NAME_PARSE_PRODUCT_USA_IN);

    Order order = new ObjectMapper().readValue(outputDestination.receive(100, BINDING_NAME_PARSE_PRODUCT_USA_OUT).getPayload(), Order.class);
    Assertions.assertNotNull(order);
    Assertions.assertEquals(product.getName(), order.getName());
  }

  @Test
  void parseOrder() throws IOException {

    Order order = new Order();
    order.setName("order1");

    Message<Order> message = MessageBuilder.withPayload(order).build();
    inputDestination.send(message, BINDING_NAME_PARSE_ORDER_IN);

    Item item = new ObjectMapper().readValue(outputDestination.receive(100, BINDING_NAME_PARSE_ORDER_OUT).getPayload(), Item.class);
    Assertions.assertNotNull(item);
    Assertions.assertEquals(item.getName(), order.getName());
    Assertions.assertEquals(item.getPrice(), 100);
  }
}
