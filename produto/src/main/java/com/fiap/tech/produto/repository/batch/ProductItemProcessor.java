package com.fiap.tech.produto.repository.batch;

import com.fiap.tech.produto.repository.model.ProductEntity;
import org.springframework.batch.item.ItemProcessor;

import java.time.LocalDateTime;

public class ProductItemProcessor implements ItemProcessor<ProductEntity, ProductEntity> {

    @Override
    public ProductEntity process(ProductEntity item) {
        item.setUpdatedAt(LocalDateTime.now());
        return item;
    }

}
