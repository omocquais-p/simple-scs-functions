package com.example.scsfunctions.processor;

import com.example.scsfunctions.dto.Item;
import com.example.scsfunctions.dto.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
@Slf4j
public class OrderProcessor {

  @Bean
  public Function<Order, Item> parseOrder() {
    return order -> {
      log.info("---- Start parseOrder - order = {}", order);
      return new Item(order.name(), 100);
    };
  }
}
