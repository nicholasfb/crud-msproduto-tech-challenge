package com.fiap.tech.produto.product.queue;

import com.fiap.tech.produto.core.useCase.FindProductByIdUseCase;
import com.fiap.tech.produto.core.useCase.UpdateProductUseCase;
import com.fiap.tech.produto.domain.product.Product;
import com.fiap.tech.produto.product.dto.ProductQuantityChangeDTO;
import io.awspring.cloud.sqs.annotation.SqsListener;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StorageQueueConsumer {

    private final UpdateProductUseCase updateProductUseCase;

    private final FindProductByIdUseCase findProductByIdUseCase;

    @SqsListener("${queue.product.name}")
    public void updateStorage(ProductQuantityChangeDTO productQuantityChangeDTO) {
        Product product = findProductByIdUseCase.execute(productQuantityChangeDTO.getProductId());

        if (productQuantityChangeDTO.getCancelled()) {
            product.setQuantity(product.getQuantity() + productQuantityChangeDTO.getQuantity());
        } else {
            product.setQuantity(product.getQuantity() - productQuantityChangeDTO.getQuantity());
        }

        updateProductUseCase.execute(product.getId(), product);
    }

}
