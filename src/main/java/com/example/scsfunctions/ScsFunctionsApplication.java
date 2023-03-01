package com.example.scsfunctions;

import com.example.scsfunctions.dto.Customer;
import com.example.scsfunctions.dto.Order;
import com.example.scsfunctions.dto.Product;
import com.example.scsfunctions.services.CustomerService;
import com.example.scsfunctions.services.ProductService;
import com.example.scsfunctions.services.TopicSelectorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;

import java.util.function.Function;

@SpringBootApplication
@Slf4j
public class ScsFunctionsApplication {

  public static void main(String[] args) {
    SpringApplication.run(ScsFunctionsApplication.class, args);
  }

  @Bean
  public Function<Customer, Product> processFile(TopicSelectorService topicSelectorService, StreamBridge streamBridge, CustomerService customerService) {
    return customer -> {
      log.info("---- Start processFile - customer = {}", customer);
      Product product = customerService.process(customer);
      //streamBridge.send(topicSelectorService.getTopic(customer), product);
      log.info("---- End processFile - customer = {}", customer);
      return product;
    };
  }

//  @Bean
//  public Consumer<Customer> processFile(TopicSelectorService topicSelectorService, StreamBridge streamBridge, CustomerService customerService) {
//    return customer -> {
//      log.info("---- Start processFile - customer = {}", customer);
//      Product product = customerService.process(customer);
//      streamBridge.send(topicSelectorService.getTopic(customer), product);
//      log.info("---- End processFile - customer = {}", customer);
//    };
//  }

  @Bean
  public Function<Product, Order> parseProduct(ProductService productService) {
    return product -> {
      log.info("---- Start parseProduct - product = {}", product);
      return productService.processProduct(product);
      //return productService.processProduct(product).stream().peek(order -> log.info("order = {}", order)).map(order -> MessageBuilder.withPayload(order).build()).collect(Collectors.toList());
    };
  }

}
