package com.fiap.tech.produto.useCase;

import com.fiap.tech.produto.domain.mappers.ProductMapper;
import com.fiap.tech.produto.domain.model.Product;
import com.fiap.tech.produto.domain.useCase.FindProductUseCase;
import com.fiap.tech.produto.infra.entity.ProductEntity;
import com.fiap.tech.produto.infra.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class FindProductUseCaseTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private FindProductUseCase findProductUseCase;

    private ProductEntity productEntity;

    private Product product;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);

        product = Product.builder()
                .description("Produto Exemplo1")
                .quantity(42)
                .purchasePrice(42.2)
                .salePrice(42.2)
                .minimumStock(42)
                .lastPurchasePrice(42.22)
                .build();

        productEntity = ProductEntity.builder()
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
        when(productRepository.findAll()).thenReturn(List.of(productEntity));
        when(productMapper.toDomain(any(ProductEntity.class))).thenReturn(product);

        List<Product> result = findProductUseCase.execute();

        assertEquals(1, result.size());
        assertEquals(product.getId(), result.get(0).getId());
        verify(productRepository, times(1)).findAll();
        verify(productMapper).toDomain(productEntity);
    }

    @Test
    void shouldReturnEmptyListWhenNoProductFound(){
        when(productRepository.findAll()).thenReturn(Collections.emptyList());

        List<Product> result = findProductUseCase.execute();

        assertEquals(0, result.size());
        verify(productRepository, times(1)).findAll();
        verify(productMapper, never()).toDomain(any(ProductEntity.class));
    }
    
}
