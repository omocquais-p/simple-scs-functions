package com.example.scsfunctions.processor;


import com.example.scsfunctions.dto.Order;
import com.example.scsfunctions.dto.Product;
import com.example.scsfunctions.services.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
@Slf4j
public class AmericanProductProcessor {
  @Bean
  public Function<Product, Order> parseProductUSA(ProductService productService) {
    return product -> {
      log.info("---- Start parseProductUSA - product = {}", product);
      return productService.processProduct(product);
    };
  }
}
