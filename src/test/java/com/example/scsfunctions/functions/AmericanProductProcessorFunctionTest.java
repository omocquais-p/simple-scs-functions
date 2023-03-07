package com.example.scsfunctions.functions;

import com.example.scsfunctions.dto.Nationality;
import com.example.scsfunctions.dto.Order;
import com.example.scsfunctions.dto.Product;
import com.example.scsfunctions.services.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith({MockitoExtension.class, OutputCaptureExtension.class})
class AmericanProductProcessorFunctionTest {
  @Mock
  ProductService productService;

  @Test
  void apply(CapturedOutput output) {
    Product product = new Product();
    product.setOrigin(Nationality.USA.toString());
    product.setName("Product1");

    Order order = new Order();
    order.setName("order1");

    Mockito.when(productService.processProduct(product)).thenReturn(order);

    assertEquals(new AmericanProductProcessorFunction(productService).apply(product), order);
    assertThat(output.getAll()).contains("Start parseProductUSA - product = Product(name=Product1, origin=USA)");

  }
}