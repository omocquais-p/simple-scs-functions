package com.example.scsfunctions.services;

import com.example.scsfunctions.dto.Order;
import com.example.scsfunctions.dto.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ProductService {

  public Order processProduct(Product product) {
    log.info("start processProduct:" + product);
    return new Order(product.name());

  }
}
