package com.example.scsfunctions.functions;

import com.example.scsfunctions.dto.Order;
import com.example.scsfunctions.dto.Product;
import com.example.scsfunctions.services.ProductService;
import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import lombok.extern.slf4j.Slf4j;

import java.util.function.Function;

@Slf4j
public class AmericanProductProcessorFunction implements Function<Product, Order> {

  private final ProductService productService;
  private final ObservationRegistry registry;

  public AmericanProductProcessorFunction(ProductService productService, ObservationRegistry registry) {
    this.productService = productService;
    this.registry = registry;
  }

  @Override
  public Order apply(Product product) {
    log.info("---- Start parseProductUSA - product = {}", product);
    return Observation
            .createNotStarted("byProductUSA", this.registry)
            .observe(() -> productService.processProduct(product));
  }
}
