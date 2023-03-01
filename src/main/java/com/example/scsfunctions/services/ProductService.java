package com.example.scsfunctions.services;

import com.example.scsfunctions.dto.Order;
import com.example.scsfunctions.dto.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class ProductService {

  public Order processProduct(Product product) {
    log.info("start processProduct:" + product);
    Order order = new Order();
    order.setName(product.getName());

//    Order order2 = new Order();
//    order2.setName(product.getName() + "2");

    return order;

  }
}
