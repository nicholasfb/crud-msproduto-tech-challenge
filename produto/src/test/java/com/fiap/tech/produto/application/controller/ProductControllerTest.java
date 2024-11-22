package com.fiap.tech.produto.application.controller;

import com.fiap.tech.produto.application.dto.ProductRequestDTO;
import com.fiap.tech.produto.application.dto.ProductResponseDTO;
import com.fiap.tech.produto.domain.mappers.ProductMapper;
import com.fiap.tech.produto.domain.model.Product;
import com.fiap.tech.produto.domain.useCase.CreateProductUseCase;
import com.fiap.tech.produto.domain.useCase.DeleteProductByIdUseCase;
import com.fiap.tech.produto.domain.useCase.FindProductUseCase;
import com.fiap.tech.produto.domain.useCase.UpdateProductUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ProductControllerTest {

    @Mock
    private CreateProductUseCase createProductUseCase;

    @Mock
    private FindProductUseCase findProductUseCase;

    @Mock
    private UpdateProductUseCase updateProductUseCase;

    @Mock
    private DeleteProductByIdUseCase deleteProductByIdUseCase;

    @Mock
    private ProductMapper productMapper;

    private ProductRequestDTO requestDTO;

    private ProductResponseDTO responseDTO;

    private Product product;

    @InjectMocks
    private ProductController productController;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);

        requestDTO = new ProductRequestDTO("Produto Exemplo", 42,
                42.2, 42.2, 42, 42.22);

        product = new Product(1L, "Produto Exemplo", 42, 42.2,
                42.2, 42, 42.22, LocalDateTime.now(), LocalDateTime.now());

        responseDTO = new ProductResponseDTO();
        responseDTO.setId(1L);
        responseDTO.setQuantity(42);
        responseDTO.setPurchasePrice(42.2);
        responseDTO.setSalePrice(42.2);
        responseDTO.setMinimumStock(42);
        responseDTO.setLastPurchasePrice(42.22);
        responseDTO.setCreatedAt(LocalDateTime.now());
        responseDTO.setUpdatedAt(LocalDateTime.now());
    }

    @Test
    void shouldCreateProductSuccessfully(){
        when(createProductUseCase.execute(any(Product.class))).thenReturn(product);
        when(productMapper.toDomain(any(ProductRequestDTO.class))).thenReturn(product);
        when(productMapper.toResponse(any(Product.class))).thenReturn(responseDTO);

        ProductResponseDTO result = productController.createProduct(requestDTO);

        assertNotNull(result);
        assertEquals(responseDTO.getId(), result.getId());
        assertEquals(responseDTO.getDescription(), result.getDescription());

        verify(createProductUseCase).execute(any(Product.class));
        verify(productMapper).toDomain(any(ProductRequestDTO.class));
        verify(productMapper).toResponse(any(Product.class));
    }

    @Test
    void shouldUpdateProductSuccessfully(){
        when(updateProductUseCase.execute(anyLong(), any(Product.class))).thenReturn(product);
        when(productMapper.toDomain(any(ProductRequestDTO.class))).thenReturn(product);
        when(productMapper.toResponse(any(Product.class))).thenReturn(responseDTO);

        ProductResponseDTO result = productController.editProduct(1L, requestDTO);

        assertNotNull(result);
        assertEquals(responseDTO.getId(), result.getId());
        assertEquals(responseDTO.getDescription(), result.getDescription());

        verify(updateProductUseCase).execute(anyLong(), any(Product.class));
        verify(productMapper).toDomain(any(ProductRequestDTO.class));
        verify(productMapper).toResponse(any(Product.class));
    }

    @Test
    void shouldFindProductSuccessfully(){
        when(findProductUseCase.execute()).thenReturn(Collections.singletonList(product));
        when(productMapper.toResponse(any(Product.class))).thenReturn(responseDTO);

        List<ProductResponseDTO> result = productController.findProducts();

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());

        verify(findProductUseCase).execute();
        verify(productMapper).toResponse(any(Product.class));
    }

    @Test
    void shouldDeleteProductSuccessfully(){
        productController.deleteProduct(1L);
        verify(deleteProductByIdUseCase).execute(anyLong());
    }
}
