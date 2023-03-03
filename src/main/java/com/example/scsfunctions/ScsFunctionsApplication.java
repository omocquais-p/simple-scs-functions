package com.example.scsfunctions;

import com.example.scsfunctions.dto.*;
import com.example.scsfunctions.services.CustomerService;
import com.example.scsfunctions.services.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.function.Function;
import java.util.function.Supplier;

@SpringBootApplication
@Slf4j
@EnableScheduling
public class ScsFunctionsApplication {

  public static final String BINDING_NAME_PARSE_PRODUCT_FRA_IN = "queue.productFRA-in.messages";
  public static final String BINDING_NAME_PARSE_PRODUCT_USA_IN = "queue.parseProductUSA-in.messages";

  public static void main(String[] args) {
    SpringApplication.run(ScsFunctionsApplication.class, args);
  }

}
