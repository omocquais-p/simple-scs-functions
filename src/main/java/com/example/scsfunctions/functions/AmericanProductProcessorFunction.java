package com.example.scsfunctions.functions;

import com.example.scsfunctions.dto.Order;
import com.example.scsfunctions.dto.Product;
import com.example.scsfunctions.services.ProductService;
import lombok.extern.slf4j.Slf4j;

import java.util.function.Function;

@Slf4j
public class AmericanProductProcessorFunction implements Function<Product, Order> {

  private final ProductService productService;

  public AmericanProductProcessorFunction(ProductService productService) {
    this.productService = productService;
  }

  @Override
  public Order apply(Product product) {
    log.info("---- Start parseProductUSA - product = {}", product);
    return productService.processProduct(product);
  }
}
