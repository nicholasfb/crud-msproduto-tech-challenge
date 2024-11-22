package com.fiap.tech.produto.useCase;

import com.fiap.tech.produto.domain.exception.BusinessException;
import com.fiap.tech.produto.domain.mappers.ProductMapper;
import com.fiap.tech.produto.domain.model.Product;
import com.fiap.tech.produto.domain.useCase.UpdateProductUseCase;
import com.fiap.tech.produto.infra.entity.ProductEntity;
import com.fiap.tech.produto.infra.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class UpdateProductUseCaseTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private UpdateProductUseCase updateProductUseCase;

    private Product updatedProductDomain;
    private ProductEntity existingProductEntity;
    private ProductEntity updatedProductEntity;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        existingProductEntity = ProductEntity.builder()
                .id(1L)
                .description("Produto Exemplo1 Existente")
                .quantity(42)
                .purchasePrice(42.2)
                .salePrice(42.2)
                .minimumStock(42)
                .lastPurchasePrice(42.22)
                .build();

        updatedProductDomain = Product.builder()
                .id(1L)
                .description("Produto Exemplo1 Atualizado")
                .quantity(84)
                .purchasePrice(84.4)
                .salePrice(84.4)
                .minimumStock(84)
                .lastPurchasePrice(84.44)
                .build();

        updatedProductEntity = ProductEntity.builder()
                .id(1L)
                .description("Produto Exemplo1 Atualizado")
                .quantity(84)
                .purchasePrice(84.4)
                .salePrice(84.4)
                .minimumStock(84)
                .lastPurchasePrice(84.44)
                .build();

    }

    @Test
    void shouldUpdateProductSuccessfully() {
    when(productRepository.findById(1L)).thenReturn(Optional.of(existingProductEntity));
    when(productMapper.update(updatedProductDomain,existingProductEntity)).thenReturn(updatedProductEntity);
    when(productRepository.save(updatedProductEntity)).thenReturn(updatedProductEntity);
    when(productMapper.toDomain(updatedProductEntity)).thenReturn(updatedProductDomain);

    Product result = updateProductUseCase.execute(1L, updatedProductDomain);

    assertEquals(updatedProductDomain.getId(), result.getId());

    verify(productRepository, times(1)).findById(1L);
    verify(productMapper, times(1)).update(updatedProductDomain, existingProductEntity);
    verify(productRepository, times(1)).save(updatedProductEntity);
    }

    @Test
    void shouldThrowExceptionWhenProductIdNotFound(){
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        BusinessException exception = assertThrows(BusinessException.class,
                () -> updateProductUseCase.execute(1L, updatedProductDomain));

        assertEquals("Erro ao atualizar um produto. Produto não encontrado", exception.getMessage());

        verify(productRepository).findById(1L);
        verify(productRepository, never()).save(any(ProductEntity.class));
        verify(productMapper, never()).toDomain(any(ProductEntity.class));
    }
}
