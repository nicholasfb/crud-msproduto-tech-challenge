package com.fiap.tech.produto.domain.useCase;

import com.fiap.tech.produto.domain.exception.ResourceNotFoundException;
import com.fiap.tech.produto.domain.mappers.ProductMapper;
import com.fiap.tech.produto.domain.model.Product;
import com.fiap.tech.produto.infra.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class FindProductByIdUseCase {

    private final ProductRepository productRepository;

    private final ProductMapper mapper;

    public Product execute(Long id) {
        return mapper.toDomain(productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado")));
    }
}
