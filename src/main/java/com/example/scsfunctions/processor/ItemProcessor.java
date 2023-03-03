package com.example.scsfunctions.processor;

import com.example.scsfunctions.dto.Item;
import com.example.scsfunctions.services.ItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
@Slf4j
public class ItemProcessor {

  @Bean
  public Consumer<Item> parseItem(ItemService itemService) {
    return item -> {
      log.info("---- Item received:" + item);
      itemService.processItem(item);
    };
  }
}
