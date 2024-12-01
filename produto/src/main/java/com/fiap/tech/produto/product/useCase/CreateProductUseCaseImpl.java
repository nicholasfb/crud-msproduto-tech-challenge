package com.fiap.tech.produto.product.useCase;

import com.fiap.tech.produto.core.repository.ProductRepository;
import com.fiap.tech.produto.core.useCase.CreateProductUseCase;
import com.fiap.tech.produto.domain.product.Product;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CreateProductUseCaseImpl implements CreateProductUseCase {

    private ProductRepository productRepository;

    @Override
    public Product execute(Product product) {
        return productRepository.save(product);
    }

}
