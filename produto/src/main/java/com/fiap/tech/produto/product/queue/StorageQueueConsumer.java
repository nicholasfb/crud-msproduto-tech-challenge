package com.fiap.tech.produto.product.queue;

import com.fiap.tech.produto.core.useCase.FindProductByIdUseCase;
import com.fiap.tech.produto.core.useCase.UpdateProductUseCase;
import com.fiap.tech.produto.domain.product.Product;
import com.fiap.tech.produto.product.dto.StorageDTO;
import io.awspring.cloud.sqs.annotation.SqsListener;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StorageQueueConsumer {

    private final UpdateProductUseCase updateProductUseCase;

    private final FindProductByIdUseCase findProductByIdUseCase;

    @SqsListener("${queue.product.name}")
    public void updateStorage(StorageDTO storageDTO) {
        Product product = findProductByIdUseCase.execute(storageDTO.getProductId());

        if (storageDTO.getCancelled()) {
            product.setQuantity(product.getQuantity() + storageDTO.getQuantity());
        } else {
            product.setQuantity(product.getQuantity() - storageDTO.getQuantity());
        }

        updateProductUseCase.execute(product.getId(), product);
    }

}
