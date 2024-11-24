package com.fiap.tech.produto.useCase;

import com.fiap.tech.produto.core.exception.UnprocessableEntityException;
import com.fiap.tech.produto.core.repository.ProductRepository;
import com.fiap.tech.produto.domain.product.Product;
import com.fiap.tech.produto.product.useCase.UpdateProductUseCaseImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UpdateProductUseCaseTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private UpdateProductUseCaseImpl updateProductUseCase;

    private Product updatedProductDomain;

    private Product existingProduct;

    private Product updatedProduct;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        existingProduct = Product.builder()
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

        updatedProduct = Product.builder()
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
        when(productRepository.findById(1L)).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(any(Product.class))).thenReturn(updatedProduct);

    Product result = updateProductUseCase.execute(1L, updatedProductDomain);

        Assertions.assertEquals(updatedProductDomain.getId(), result.getId());

    verify(productRepository, times(1)).findById(1L);
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void shouldThrowExceptionWhenProductIdNotFound(){
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        UnprocessableEntityException exception = assertThrows(
                UnprocessableEntityException.class,
                () -> updateProductUseCase.execute(1L, updatedProductDomain));

        Assertions.assertEquals(
                "Erro ao atualizar um produto. Produto não encontrado", exception.getMessage());

        verify(productRepository).findById(1L);
        verify(productRepository, never()).save(any(Product.class));
    }
}
