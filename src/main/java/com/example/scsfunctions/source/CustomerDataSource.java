package com.example.scsfunctions.source;

import com.example.scsfunctions.dto.Customer;
import com.example.scsfunctions.dto.Nationality;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
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
      customer.setName("Bob-" + UUID.randomUUID());
      customer.setNationality(Nationality.FRA);
      log.info("customer = " + customer);
      return customer;
    };
  }

}
