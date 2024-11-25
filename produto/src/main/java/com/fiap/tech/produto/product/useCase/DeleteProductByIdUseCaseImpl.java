package com.fiap.tech.produto.product.useCase;

import com.fiap.tech.produto.core.repository.ProductRepository;
import com.fiap.tech.produto.core.useCase.DeleteProductByIdUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DeleteProductByIdUseCaseImpl implements DeleteProductByIdUseCase {

    private ProductRepository productRepository;

    @Override
    public void execute(Long id) {
        productRepository.delete(id);
    }
}
