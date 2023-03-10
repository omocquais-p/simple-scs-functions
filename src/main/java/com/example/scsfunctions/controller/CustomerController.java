package com.example.scsfunctions.controller;

import com.example.scsfunctions.controller.dto.BatchCommand;
import com.example.scsfunctions.dto.Customer;
import com.example.scsfunctions.dto.Nationality;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class CustomerController {

  private final StreamBridge streamBridge;

  public CustomerController(StreamBridge streamBridge) {
    this.streamBridge = streamBridge;
  }

  @PostMapping("/batch")
  @ResponseStatus(HttpStatus.CREATED)
  public void start(@RequestBody BatchCommand batchCommand) {

    log.info("batchCommand = " + batchCommand);

    var customer = new Customer(batchCommand.name(),
            batchCommand.name(),
            Nationality.valueOf(batchCommand.nationality()));

    streamBridge.send("customer", customer);

    log.info("Customer sent: {} to {}", customer, "customer");

  }
}
