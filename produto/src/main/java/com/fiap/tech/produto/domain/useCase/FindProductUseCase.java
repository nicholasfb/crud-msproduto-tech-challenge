package com.fiap.tech.produto.domain.useCase;

import com.fiap.tech.produto.domain.mappers.ProductMapper;
import com.fiap.tech.produto.domain.model.Product;
import com.fiap.tech.produto.infra.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class FindProductUseCase {

    private ProductRepository productRepository;

    private ProductMapper mapper;

    public List<Product> execute() {
        return productRepository.findAll().stream().map(mapper::toDomain).toList();
    }

}
