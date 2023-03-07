package com.example.scsfunctions.source;

import com.example.scsfunctions.dto.Customer;
import com.example.scsfunctions.dto.Nationality;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

@Slf4j
@Component
public class CustomerDataSource {

  @Bean
  @Scheduled(fixedDelay = 5, timeUnit = TimeUnit.SECONDS)
  public Supplier<Customer> sendCustomerData() {
    return () -> {

      var customer = new Customer();
      UUID uuid = UUID.randomUUID();

      customer.setName("Bob-" + uuid);
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
