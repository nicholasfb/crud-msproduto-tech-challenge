package com.fiap.tech.produto.core.useCase;

import com.fiap.tech.produto.domain.product.Product;

import java.util.Set;

public interface FindProductUseCase {

    Set<Product> execute();

}
