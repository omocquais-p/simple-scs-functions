package com.example.scsfunctions.processor;

import com.example.scsfunctions.dto.Customer;
import com.example.scsfunctions.dto.Product;
import com.example.scsfunctions.services.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.function.Function;

import static com.example.scsfunctions.ScsFunctionsApplication.BINDING_NAME_PARSE_PRODUCT_FRA_IN;
import static com.example.scsfunctions.ScsFunctionsApplication.BINDING_NAME_PARSE_PRODUCT_USA_IN;

@Component
@Slf4j
public class CustomerProcessor {
  @Bean
  public Function<Customer, Message<Product>> processCustomer(CustomerService customerService) {
    return customer -> {
      log.info("---- Start processCustomer - customer = {}", customer);

      Product product = customerService.process(customer);

      switch (customer.nationality()) {
        case FRA -> {
          return MessageBuilder.withPayload(product).setHeader("spring.cloud.stream.sendto.destination", BINDING_NAME_PARSE_PRODUCT_FRA_IN).build();
        }
        case USA -> {
          return MessageBuilder.withPayload(product).setHeader("spring.cloud.stream.sendto.destination", BINDING_NAME_PARSE_PRODUCT_USA_IN).build();
        }
        default -> log.info("No Processor configured for this nationality");
      }

      log.info("---- End processFile - customer = {}", customer);

      throw new IllegalArgumentException("No Processor configured for this nationality");
    };
  }
}
