package com.fiap.tech.produto.mock;

import com.fiap.tech.produto.product.dto.ProductDTO;

import java.time.LocalDateTime;

public class ProductDTOMock {

    public static ProductDTO mockRequest() {
        return new ProductDTO(
                null, "Produto Exemplo", 42,
                42.2, 42.2, 42, 42.22, LocalDateTime.now(), LocalDateTime.now()
        );
    }

    public static ProductDTO mockResponse() {
        return new ProductDTO(
                1L, "Produto Exemplo", 42, 42.2,
                42.2, 42, 42.22, LocalDateTime.now(), LocalDateTime.now()
        );
    }

}
