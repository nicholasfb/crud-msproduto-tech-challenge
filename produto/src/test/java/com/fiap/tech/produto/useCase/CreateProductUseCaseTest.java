package com.fiap.tech.produto.useCase;

import com.fiap.tech.produto.core.repository.ProductRepository;
import com.fiap.tech.produto.domain.product.Product;
import com.fiap.tech.produto.product.useCase.CreateProductUseCaseImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CreateProductUseCaseTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private CreateProductUseCaseImpl createProductUseCase;

    private Product product;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        product = Product.builder()
                .id(1L)
                .description("Produto Exemplo1")
                .quantity(42)
                .purchasePrice(42.2)
                .salePrice(42.2)
                .minimumStock(42)
                .lastPurchasePrice(42.22)
                .build();

    }

    @Test
    void shouldCreateProductSuccessfully() {
        when(productRepository.save(any(Product.class))).thenReturn(product);

        Product result = createProductUseCase.execute(product);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(product.getId(), result.getId());
        Assertions.assertEquals(product.getDescription(), result.getDescription());
        Assertions.assertEquals(product.getQuantity(), result.getQuantity());
        Assertions.assertEquals(product.getPurchasePrice(), result.getPurchasePrice());
        Assertions.assertEquals(product.getSalePrice(), result.getSalePrice());
        Assertions.assertEquals(product.getMinimumStock(), result.getMinimumStock());
        Assertions.assertEquals(product.getLastPurchasePrice(), result.getLastPurchasePrice());


        verify(productRepository, times(1)).save(product);
    }

}
