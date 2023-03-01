package com.example.scsfunctions;

import com.example.scsfunctions.dto.Customer;
import com.example.scsfunctions.dto.Item;
import com.example.scsfunctions.dto.Order;
import com.example.scsfunctions.dto.Product;
import com.example.scsfunctions.services.CustomerService;
import com.example.scsfunctions.services.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.function.Function;

@SpringBootApplication
@Slf4j
public class ScsFunctionsApplication {

  public static void main(String[] args) {
    SpringApplication.run(ScsFunctionsApplication.class, args);
  }

  @Bean
  public Function<Customer, Product> processFile(CustomerService customerService) {
    return customer -> {
      log.info("---- Start processFile - customer = {}", customer);
      Product product = customerService.process(customer);
      log.info("---- End processFile - customer = {}", customer);
      return product;
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
