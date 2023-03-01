package com.example.scsfunctions;

import com.example.scsfunctions.dto.*;
import com.example.scsfunctions.services.CustomerService;
import com.example.scsfunctions.services.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;

import java.util.function.Function;

@SpringBootApplication
@Slf4j
public class ScsFunctionsApplication {

  public static void main(String[] args) {
    SpringApplication.run(ScsFunctionsApplication.class, args);
  }

  @Bean
  public Function<Customer, Message<Product>> processFile(CustomerService customerService) {
    return customer -> {
      log.info("---- Start processFile - customer = {}", customer);
      Product product = customerService.process(customer);
      if (customer.getNationality().equals(Nationality.FRA)) {
        return MessageBuilder.withPayload(product)
                .setHeader("spring.cloud.stream.sendto.destination", "parseProductFRA-in-0").build();
      }
      log.info("---- End processFile - customer = {}", customer);
      return MessageBuilder.withPayload(product)
              .setHeader("spring.cloud.stream.sendto.destination", "parseProductUSA-in-0").build();
    };
  }

  @Bean
  public Function<Product, Order> parseProduct(ProductService productService) {
    return product -> {
      log.info("---- Start parseProduct - product = {}", product);
      return productService.processProduct(product);
    };
  }

  @Bean
  public Function<Product, Order> parseProductFRA(ProductService productService) {
    return product -> {
      log.info("---- Start parseProductFRA - product = {}", product);
      return productService.processProduct(product);
    };
  }

  @Bean
  public Function<Product, Order> parseProductUSA(ProductService productService) {
    return product -> {
      log.info("---- Start parseProductUSA - product = {}", product);
      return productService.processProduct(product);
    };
  }

  @Bean
  public Function<Order, Item> parseOrder() {
    return order -> {
      log.info("---- Start parseOrder - order = {}", order);
      Item item = new Item();
      item.setName(order.getName());
      item.setPrice(100);
      return item;
    };
  }

}
