package com.example.scsfunctions;

import com.example.scsfunctions.dto.*;
import com.example.scsfunctions.services.ItemService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.stream.binder.test.InputDestination;
import org.springframework.cloud.stream.binder.test.OutputDestination;
import org.springframework.cloud.stream.binder.test.TestChannelBinderConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

@Slf4j
@SpringBootTest
@Import(TestChannelBinderConfiguration.class)
class ScsFunctionsApplicationTests {

  public static final String BINDING_NAME_PROCESS_CUSTOMER_IN = "queue.processCustomer-in.messages";
  public static final String BINDING_NAME_PARSE_PRODUCT_FRA_OUT = "queue.productFRA-out.messages";
  public static final String BINDING_NAME_PARSE_PRODUCT_USA_OUT = "queue.parseProductUSA-out.messages";

  @Autowired
  private InputDestination inputDestination;

  @Autowired
  private OutputDestination outputDestination;

  @MockBean
  ItemService itemService;

  @DisplayName("Given a customer with a french nationality, it must be processed by the French Product Processor")
  @Test
  void processFrenchCustomer() throws IOException {

    Customer customer = new Customer("Dupond", "Olivier", Nationality.FRA);
    inputDestination.send(MessageBuilder.withPayload(customer).build(), BINDING_NAME_PROCESS_CUSTOMER_IN);

    Product product = new ObjectMapper().readValue(outputDestination.receive(100, ScsFunctionsApplicationTests.BINDING_NAME_PARSE_PRODUCT_FRA_OUT).getPayload(), Product.class);
    Assertions.assertNotNull(product);
    Assertions.assertEquals(customer.name(), product.name());
  }

  @DisplayName("Given a customer with an american nationality, it must be processed by the parseProductUSA function")
  @Test
  void processAmericanCustomer() throws IOException {

    Customer customer = new Customer("Smith", "Olivier", Nationality.USA);
    inputDestination.send(MessageBuilder.withPayload(customer).build(), BINDING_NAME_PROCESS_CUSTOMER_IN);

    Product product = new ObjectMapper().readValue(outputDestination.receive(100, BINDING_NAME_PARSE_PRODUCT_USA_OUT).getPayload(), Product.class);
    Assertions.assertNotNull(product);
    Assertions.assertEquals(customer.name(), product.name());
  }

  @DisplayName("Given a product published, it must be processed by the parseProduct function")
  @Test
  void parseProduct() throws IOException {

    Product product = new Product("Bob", "web");
    Message<Product> message = MessageBuilder.withPayload(product).build();

    inputDestination.send(message, "queue.parseProduct-in.messages");

    Order order = new ObjectMapper().readValue(outputDestination.receive(100, "queue.parseProduct-out.messages").getPayload(), Order.class);
    Assertions.assertNotNull(order);
    Assertions.assertEquals(product.name(), order.name());
  }

  @Test
  void parseProductFRA() throws IOException {

    Product product = new Product("Bob", "web");

    Message<Product> message = MessageBuilder.withPayload(product).build();

    inputDestination.send(message, ScsFunctionsApplication.BINDING_NAME_PARSE_PRODUCT_FRA_IN);

    Order order = new ObjectMapper().readValue(outputDestination.receive(100, BINDING_NAME_PARSE_PRODUCT_FRA_OUT).getPayload(), Order.class);
    Assertions.assertNotNull(order);
    Assertions.assertEquals(product.name(), order.name());
  }

  @Test
  void parseProductUSA() throws IOException {

    Product product = new Product("Bob", "web");

    Message<Product> message = MessageBuilder.withPayload(product).build();

    inputDestination.send(message, ScsFunctionsApplication.BINDING_NAME_PARSE_PRODUCT_USA_IN);

    Order order = new ObjectMapper().readValue(outputDestination.receive(100, BINDING_NAME_PARSE_PRODUCT_USA_OUT).getPayload(), Order.class);
    Assertions.assertNotNull(order);
    Assertions.assertEquals(product.name(), order.name());
  }

  @Test
  void parseOrder() throws IOException {

    Order order = new Order("order1");

    Message<Order> message = MessageBuilder.withPayload(order).build();
    inputDestination.send(message, "queue.parseOrder-in.messages");

    Item item = new ObjectMapper().readValue(outputDestination.receive(100, "queue.parseOrder-out.messages").getPayload(), Item.class);
    Assertions.assertNotNull(item);
    Assertions.assertEquals(item.name(), order.name());
    Assertions.assertEquals(item.price(), 100);
  }

  @Test
  void parseItem() {
    doNothing().when(itemService).processItem(Mockito.any(Item.class));

    Item item = new Item("item1", 100);
    Message<Item> message = MessageBuilder.withPayload(item).build();
    inputDestination.send(message, "queue.parseItem-in.messages");
    verify(itemService).processItem(any(Item.class));
  }

}
