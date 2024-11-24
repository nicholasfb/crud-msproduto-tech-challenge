package com.fiap.tech.produto.core.useCase;

import com.fiap.tech.produto.domain.product.Product;

public interface UpdateProductUseCase {

    Product execute(Long idProduct, Product product);

}
