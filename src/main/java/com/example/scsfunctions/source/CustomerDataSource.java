package com.example.scsfunctions.source;

import com.example.scsfunctions.dto.Customer;
import com.example.scsfunctions.dto.Nationality;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.function.Supplier;

@Slf4j
@Component
public class CustomerDataSource {

  @Bean
  public Supplier<Customer> sendCustomerData() {
    return () -> {
      if (Instant.now().toEpochMilli() % 2 == 0) {
        Customer customer = new Customer("Olivier-" + LocalDateTime.now() + "-" + UUID.randomUUID(), "Olivier", Nationality.FRA);
        log.info("customer = " + customer);
        return customer;

      } else {
        Customer customer = new Customer("Bob-" + LocalDateTime.now() + "-" + UUID.randomUUID(), "Bob", Nationality.USA);
        log.info("customer = " + customer);
        return customer;
      }
    };
  }

}
