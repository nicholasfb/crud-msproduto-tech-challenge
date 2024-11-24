package com.fiap.tech.produto.product.useCase;

import com.fiap.tech.produto.core.exception.ResourceNotFoundException;
import com.fiap.tech.produto.core.repository.ProductRepository;
import com.fiap.tech.produto.core.useCase.FindProductByIdUseCase;
import com.fiap.tech.produto.domain.product.Product;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class FindProductByIdUseCaseImpl implements FindProductByIdUseCase {

    private final ProductRepository productRepository;

    @Override
    public Product execute(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado"));
    }
}
