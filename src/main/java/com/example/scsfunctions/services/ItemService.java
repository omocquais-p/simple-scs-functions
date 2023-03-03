package com.example.scsfunctions.services;

import com.example.scsfunctions.dto.Item;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ItemService {

  public void processItem(Item item) {
    log.info("Item:" + item);
  }
}
