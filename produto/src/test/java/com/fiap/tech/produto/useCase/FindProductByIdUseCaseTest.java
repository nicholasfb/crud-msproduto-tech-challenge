package com.fiap.tech.produto.useCase;

import com.fiap.tech.produto.core.exception.ResourceNotFoundException;
import com.fiap.tech.produto.core.repository.ProductRepository;
import com.fiap.tech.produto.domain.product.Product;
import com.fiap.tech.produto.product.useCase.FindProductByIdUseCaseImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class FindProductByIdUseCaseTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private FindProductByIdUseCaseImpl findProductByIdUseCase;

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
    void shouldFindProductWithValidId(){
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        Product result = findProductByIdUseCase.execute(1L);

        Assertions.assertEquals(product.getId(), result.getId());

        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    void shouldThrowExceptionWhenFindProductWithInvalidId(){
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> findProductByIdUseCase.execute(1L));

        Assertions.assertEquals("Produto não encontrado", exception.getMessage());
    }
}
