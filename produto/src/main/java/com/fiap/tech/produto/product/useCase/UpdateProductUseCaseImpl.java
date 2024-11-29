package com.fiap.tech.produto.product.useCase;

import com.fiap.tech.produto.core.exception.UnprocessableEntityException;
import com.fiap.tech.produto.core.repository.ProductRepository;
import com.fiap.tech.produto.core.useCase.UpdateProductUseCase;
import com.fiap.tech.produto.domain.product.Product;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UpdateProductUseCaseImpl implements UpdateProductUseCase {

    private ProductRepository productRepository;

    @Override
    public Product execute(Long idProduct, Product product) {
        Product persistedProduct = productRepository.findById(idProduct).orElseThrow(
                () -> new UnprocessableEntityException(
                        "Erro ao atualizar um produto. Produto não encontrado"));

        product.setId(persistedProduct.getId());

        return productRepository.save(product);
    }

}
