package com.fiap.tech.produto.core.useCase;

import com.fiap.tech.produto.domain.product.Product;

public interface FindProductByIdUseCase {

    Product execute(Long id);

}
