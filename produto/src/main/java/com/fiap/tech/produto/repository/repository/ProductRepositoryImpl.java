package com.fiap.tech.produto.repository.repository;

import com.fiap.tech.produto.core.exception.ResourceNotFoundException;
import com.fiap.tech.produto.core.repository.ProductRepository;
import com.fiap.tech.produto.domain.product.Product;
import com.fiap.tech.produto.repository.adapter.ProductAdapter;
import com.fiap.tech.produto.repository.jpa.ProductJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepository {

    private final ProductJpaRepository productJpaRepository;

    private final ProductAdapter productAdapter = ProductAdapter.INSTANCE;

    @Override
    public Optional<Product> findById(final Long id) {
        return Optional.ofNullable(
                productAdapter.fromEntity(
                        productJpaRepository.findById(id).orElse(null)
                )
        );
    }

    @Override
    public Product save(final Product product) {
        return productAdapter.fromEntity(
                productJpaRepository.save(
                        productAdapter.toEntity(product)
                )
        );
    }

    @Override
    public Product update(final Product product) {
        return productAdapter.fromEntity(
                productJpaRepository.save(
                        productAdapter.update(
                                productJpaRepository.findById(product.getId()).orElseThrow(
                                        () -> new ResourceNotFoundException("Pedido não encontrado")),
                                product
                        )
                )
        );
    }

    @Override
    public Set<Product> findAll() {
        return productJpaRepository.findAll().stream().map(productAdapter::fromEntity).collect(
                Collectors.toSet());
    }

}
