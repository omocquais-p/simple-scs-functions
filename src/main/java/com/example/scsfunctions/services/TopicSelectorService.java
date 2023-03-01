package com.example.scsfunctions.services;

import com.example.scsfunctions.dto.Customer;
import org.springframework.stereotype.Service;

@Service
public class TopicSelectorService {
  public String getTopic(Customer customer) {
    return switch (customer.getName()) {
      case "Paul" -> "incomingFRQueue";
      case "John" -> "incomingUSQueue";
      default -> throw new IllegalStateException("Unexpected value: " + customer.getName());
    };
  }
}
