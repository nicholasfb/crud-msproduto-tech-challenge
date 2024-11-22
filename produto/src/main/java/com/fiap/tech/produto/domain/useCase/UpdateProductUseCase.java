package com.fiap.tech.produto.domain.useCase;

import com.fiap.tech.produto.domain.exception.BusinessException;
import com.fiap.tech.produto.domain.mappers.ProductMapper;
import com.fiap.tech.produto.domain.model.Product;
import com.fiap.tech.produto.infra.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UpdateProductUseCase {
    private ProductRepository productRepository;

    private ProductMapper mapper;

    public Product execute(Long idProduct, Product product) {
        return mapper.
                toDomain(productRepository.save(mapper.update(product, productRepository.findById(idProduct)
                        .orElseThrow(() -> new BusinessException("Erro ao atualizar um produto. Produto não encontrado")))));
    }
}
