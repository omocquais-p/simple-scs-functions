package com.example.scsfunctions.functions;

import com.example.scsfunctions.dto.Nationality;
import com.example.scsfunctions.dto.Order;
import com.example.scsfunctions.dto.Product;
import com.example.scsfunctions.services.ProductService;
import io.micrometer.observation.ObservationRegistry;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

@ExtendWith({MockitoExtension.class, OutputCaptureExtension.class})
class AmericanProductProcessorFunctionTest {
  @Mock
  ProductService productService;

  @Mock
  ObservationRegistry registry;

  @Mock
  ObservationRegistry.ObservationConfig observationConfig;

  @Test
  void apply(CapturedOutput output) {
    var product = new Product("Product1", Nationality.USA.toString());
    var order = new Order("order1");

    Mockito.when(productService.processProduct(product)).thenReturn(order);

    Mockito.when(registry.observationConfig()).thenReturn(observationConfig);

    assertEquals(new AmericanProductProcessorFunction(productService, registry).apply(product), order);
    assertThat(output.getAll()).contains("Start parseProductUSA - product = Product[name=Product1, origin=USA]");
    verify(productService).processProduct(product);

  }

  @DisplayName("Given a product wthout a nationality, It should throw an exception")
  @Test
  public void productWithoutNationality(){
    var product = new Product("Product1", null);
    var order = new Order("order1");
  }
}