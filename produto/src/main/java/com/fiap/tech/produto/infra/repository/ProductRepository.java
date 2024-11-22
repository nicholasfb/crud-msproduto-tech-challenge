package com.fiap.tech.produto.infra.repository;

import com.fiap.tech.produto.infra.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository
		extends JpaRepository<ProductEntity, Long>, JpaSpecificationExecutor<ProductEntity> {
}
