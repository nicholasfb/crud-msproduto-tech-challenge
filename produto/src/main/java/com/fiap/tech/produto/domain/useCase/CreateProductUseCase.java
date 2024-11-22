package com.fiap.tech.produto.domain.useCase;

import com.fiap.tech.produto.domain.mappers.ProductMapper;
import com.fiap.tech.produto.domain.model.Product;
import com.fiap.tech.produto.infra.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CreateProductUseCase {

	private ProductRepository productRepository;

	private ProductMapper mapper;

	public Product execute(Product product) {
		return mapper.toDomain(productRepository.save(mapper.toEntity(product)));
	}
}
