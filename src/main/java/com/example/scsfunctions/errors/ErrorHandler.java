package com.example.scsfunctions.errors;

import org.springframework.context.annotation.Bean;
import org.springframework.messaging.support.ErrorMessage;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
public class ErrorHandler {
  private final ErrorService errorService;

  public ErrorHandler(ErrorService errorService) {
    this.errorService = errorService;
  }

  @Bean
  public Consumer<ErrorMessage> myErrorHandler() {
    return errorService::handleError;
  }
}
