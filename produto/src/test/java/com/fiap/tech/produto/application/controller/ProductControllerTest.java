package com.fiap.tech.produto.application.controller;

import com.fiap.tech.produto.domain.product.Product;
import com.fiap.tech.produto.mock.ProductDTOMock;
import com.fiap.tech.produto.mock.ProductMock;
import com.fiap.tech.produto.product.controller.ProductController;
import com.fiap.tech.produto.product.dto.ProductDTO;
import com.fiap.tech.produto.product.useCase.CreateProductUseCaseImpl;
import com.fiap.tech.produto.product.useCase.FindProductUseCaseImpl;
import com.fiap.tech.produto.product.useCase.UpdateProductUseCaseImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ProductControllerTest {

    @Mock
    private CreateProductUseCaseImpl createProductUseCase;

    @Mock
    private FindProductUseCaseImpl findProductUseCase;

    @Mock
    private UpdateProductUseCaseImpl updateProductUseCase;

    private ProductDTO requestDTO;

    private Product product;

    private ProductDTO responseDTO;

    @InjectMocks
    private ProductController productController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        requestDTO = ProductDTOMock.mockRequest();

        product = ProductMock.mock();

        responseDTO = ProductDTOMock.mockResponse();
    }

    @Test
    void shouldCreateProductSuccessfully() {
        when(createProductUseCase.execute(any(Product.class))).thenReturn(product);

        ProductDTO result = productController.createProduct(requestDTO);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(responseDTO.getId(), result.getId());
        Assertions.assertEquals(responseDTO.getDescription(), result.getDescription());

        verify(createProductUseCase).execute(any(Product.class));
    }

    @Test
    void shouldUpdateProductSuccessfully() {
        when(updateProductUseCase.execute(anyLong(), any(Product.class))).thenReturn(product);

        ProductDTO result = productController.editProduct(1L, requestDTO);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(responseDTO.getId(), result.getId());
        Assertions.assertEquals(responseDTO.getDescription(), result.getDescription());

        verify(updateProductUseCase).execute(anyLong(), any(Product.class));
    }

    @Test
    void shouldFindProductSuccessfully() {
        when(findProductUseCase.execute()).thenReturn(Collections.singleton(product));

        List<ProductDTO> result = productController.findProducts();

        Assertions.assertNotNull(result);
        Assertions.assertFalse(result.isEmpty());
        Assertions.assertEquals(1, result.size());

        verify(findProductUseCase).execute();
    }

}
