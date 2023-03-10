package com.example.scsfunctions.source;

import com.example.scsfunctions.dto.Customer;
import com.example.scsfunctions.dto.Nationality;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

@Slf4j
@Component
public class CustomerDataSource {

  @Bean
  //@Scheduled(fixedRate = 1, timeUnit = TimeUnit.MINUTES)
  public Supplier<Customer> sendCustomerData() {
    return () -> {
      var customer = new Customer();
      customer.setName("Bob-" + LocalDateTime.now() + "-" + UUID.randomUUID());
      if (Instant.now().toEpochMilli() % 2 == 0) {
        customer.setNationality(Nationality.FRA);
      } else {
        customer.setNationality(Nationality.USA);
      }

      log.info("customer = " + customer);
      return customer;
    };
  }

}
