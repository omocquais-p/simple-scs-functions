package com.example.scsfunctions.services;

import com.example.scsfunctions.dto.Customer;
import com.example.scsfunctions.dto.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CustomerService {

  public Product process(Customer customer) {
    log.info("process customer:" + customer);
    return new Product(customer.name(), "sample-origin");
  }
}
