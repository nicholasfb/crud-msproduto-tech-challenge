package com.fiap.tech.produto.useCase;

import com.fiap.tech.produto.core.repository.ProductRepository;
import com.fiap.tech.produto.product.useCase.DeleteProductByIdUseCaseImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class DeleteProductByIdUseCaseTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private DeleteProductByIdUseCaseImpl deleteProductByIdUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldDeleteProductByIdSuccessfully() {
        Long productId = 1L;

        deleteProductByIdUseCase.execute(productId);

        verify(productRepository, times(1)).delete(productId);
    }

}
