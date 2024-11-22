package com.fiap.tech.produto.useCase;

import com.fiap.tech.produto.domain.exception.ResourceNotFoundException;
import com.fiap.tech.produto.domain.mappers.ProductMapper;
import com.fiap.tech.produto.domain.model.Product;
import com.fiap.tech.produto.domain.useCase.FindProductByIdUseCase;
import com.fiap.tech.produto.infra.entity.ProductEntity;
import com.fiap.tech.produto.infra.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class FindProductByIdUseCaseTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private FindProductByIdUseCase findProductByIdUseCase;

    private Long validId;

    private Long invalidId;

    private ProductEntity productEntity;

    private Product product;

    @BeforeEach
    void setUp() {
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
    void shouldFindProductWithValidId(){
        when(productRepository.findById(1L)).thenReturn(Optional.of(productEntity));
        when(productMapper.toDomain(productEntity)).thenReturn(product);

        Product result = findProductByIdUseCase.execute(1L);

        assertEquals(product.getId(), result.getId());

        verify(productRepository, times(1)).findById(1L);
        verify(productMapper, times(1)).toDomain(productEntity);
    }

    @Test
    void shouldThrowExceptionWhenFindProductWithInvalidId(){
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> findProductByIdUseCase.execute(1L));

        assertEquals("Produto não encontrado", exception.getMessage());

        verify(productMapper, never()).toDomain(any(ProductEntity.class));
    }
}
