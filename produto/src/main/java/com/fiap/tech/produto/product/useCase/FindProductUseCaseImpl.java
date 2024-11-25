package com.fiap.tech.produto.product.useCase;

import com.fiap.tech.produto.core.repository.ProductRepository;
import com.fiap.tech.produto.core.useCase.FindProductUseCase;
import com.fiap.tech.produto.domain.product.Product;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@AllArgsConstructor
public class FindProductUseCaseImpl implements FindProductUseCase {

    private ProductRepository productRepository;

    @Override
    public Set<Product> execute() {
        return productRepository.findAll();
    }

}
