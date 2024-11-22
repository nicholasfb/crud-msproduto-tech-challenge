package com.fiap.tech.produto.useCase;

import com.fiap.tech.produto.domain.exception.BusinessException;
import com.fiap.tech.produto.domain.mappers.ProductMapper;
import com.fiap.tech.produto.domain.model.Product;
import com.fiap.tech.produto.domain.useCase.DeleteProductByIdUseCase;
import com.fiap.tech.produto.infra.entity.ProductEntity;
import com.fiap.tech.produto.infra.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.*;

public class DeleteProductByIdUseCaseTest {
    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private DeleteProductByIdUseCase deleteProductByIdUseCase;

    private Product product;

    private ProductEntity productEntity;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldDeleteProductByIdSuccessfully() {
        Long productId = 1L;
        ProductEntity productEntity = new ProductEntity();
        productEntity.setId(productId);

        when(productRepository.findById(productId)).thenReturn(Optional.of(productEntity));

        deleteProductByIdUseCase.execute(productId);

        verify(productRepository, times(1)).delete(productEntity);
    }

    @Test
    void shouldThrowExceptionWhenProductNotFound() {
        Long productId = 1L;

        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        BusinessException exception = assertThrows(BusinessException.class, () -> deleteProductByIdUseCase.execute(productId));

        assertEquals("Produto não encontrado", exception.getMessage());

        verify(productRepository, never()).delete(any(ProductEntity.class));
    }
}
