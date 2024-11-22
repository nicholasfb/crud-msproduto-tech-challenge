package com.fiap.tech.produto.useCase;

import com.fiap.tech.produto.domain.mappers.ProductMapper;
import com.fiap.tech.produto.domain.model.Product;
import com.fiap.tech.produto.domain.useCase.CreateProductUseCase;
import com.fiap.tech.produto.infra.entity.ProductEntity;
import com.fiap.tech.produto.infra.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CreateProductUseCaseTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private CreateProductUseCase createProductUseCase;

    private Product product;

    private ProductEntity productEntity;

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
    void shouldCreateProductSuccessfully() {
        when(productMapper.toEntity(any(Product.class))).thenReturn(productEntity);
        when(productRepository.save(any(ProductEntity.class))).thenReturn(productEntity);
        when(productMapper.toDomain(any(ProductEntity.class))).thenReturn(product);

        Product result = createProductUseCase.execute(product);

        assertNotNull(result);
        assertEquals(product.getId(), result.getId());
        assertEquals(product.getDescription(), result.getDescription());
        assertEquals(product.getQuantity(), result.getQuantity());
        assertEquals(product.getPurchasePrice(), result.getPurchasePrice());
        assertEquals(product.getSalePrice(), result.getSalePrice());
        assertEquals(product.getMinimumStock(), result.getMinimumStock());
        assertEquals(product.getLastPurchasePrice(), result.getLastPurchasePrice());


        verify(productRepository, times(1)).save(productEntity);
        verify(productMapper).toEntity(product);
        verify(productMapper).toDomain(productEntity);
    }
}
