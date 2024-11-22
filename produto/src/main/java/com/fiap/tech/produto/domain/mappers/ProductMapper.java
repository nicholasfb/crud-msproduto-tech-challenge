package com.fiap.tech.produto.domain.mappers;

import com.fiap.tech.produto.application.dto.ProductRequestDTO;
import com.fiap.tech.produto.application.dto.ProductResponseDTO;
import com.fiap.tech.produto.domain.model.Product;
import com.fiap.tech.produto.infra.entity.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Product toDomain(ProductRequestDTO dto);

    Product toDomain(ProductEntity entity);

    ProductResponseDTO toResponse(Product domain);

    ProductEntity toEntity(Product domain);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    ProductEntity update(Product domain, @MappingTarget ProductEntity entity);
}