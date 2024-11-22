package com.fiap.tech.produto.mocks;

import com.fiap.tech.produto.domain.model.Product;
import com.fiap.tech.produto.infra.entity.ProductEntity;

public class ProductMock {

    public static Product mockDomain() {
        return Product.builder()
                .description("Produto Exemplo1")
                .quantity(42)
                .purchasePrice(42.2)
                .salePrice(42.2)
                .minimumStock(42)
                .lastPurchasePrice(42.22)
                .build();
    }

    public static ProductEntity mockEntity() {
        return ProductEntity.builder()
                .id(1L)
                .description("Produto Exemplo1")
                .quantity(42)
                .purchasePrice(42.2)
                .salePrice(42.2)
                .minimumStock(42)
                .lastPurchasePrice(42.22)
                .build();
    }
}
