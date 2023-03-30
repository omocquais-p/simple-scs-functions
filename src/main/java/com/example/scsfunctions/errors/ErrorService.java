package com.example.scsfunctions.errors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.support.ErrorMessage;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ErrorService {

  public void handleError(ErrorMessage errorMessage) {
    log.error("--- Error: ", errorMessage.getPayload());
  }
}
