package com.fiap.tech.produto.repository.adapter;

import com.fiap.tech.produto.domain.product.Product;
import com.fiap.tech.produto.repository.model.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductAdapter {

    ProductAdapter INSTANCE = Mappers.getMapper(ProductAdapter.class);

    Product fromEntity(ProductEntity entity);

    ProductEntity toEntity(Product domain);

}