package com.fiap.tech.produto.domain.useCase;

import com.fiap.tech.produto.domain.exception.BusinessException;
import com.fiap.tech.produto.infra.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DeleteProductByIdUseCase {

    private ProductRepository productRepository;

    public void execute(Long id) {
        productRepository.delete(productRepository.findById(id)
        .orElseThrow(() -> new BusinessException("Produto não encontrado")));
    }
}
