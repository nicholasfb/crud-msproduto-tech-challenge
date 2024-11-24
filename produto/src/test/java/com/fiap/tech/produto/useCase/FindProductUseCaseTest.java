package com.fiap.tech.produto.useCase;

import com.fiap.tech.produto.core.repository.ProductRepository;
import com.fiap.tech.produto.domain.product.Product;
import com.fiap.tech.produto.product.useCase.FindProductUseCaseImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.Set;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class FindProductUseCaseTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private FindProductUseCaseImpl findProductUseCase;

    private Product product;

    @BeforeEach
    void setUp(){
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
    void shouldFindProductsSuccessfully(){
        when(productRepository.findAll()).thenReturn(Set.of(product));

        Set<Product> result = findProductUseCase.execute();

        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(product.getId(), result.stream().findFirst().get().getId());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void shouldReturnEmptyListWhenNoProductFound(){
        when(productRepository.findAll()).thenReturn(Collections.emptySet());

        Set<Product> result = findProductUseCase.execute();

        Assertions.assertEquals(0, result.size());
        verify(productRepository, times(1)).findAll();
    }
    
}
