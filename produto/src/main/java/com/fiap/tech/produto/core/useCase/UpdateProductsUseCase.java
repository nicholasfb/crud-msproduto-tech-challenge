package com.fiap.tech.produto.core.useCase;

import org.springframework.web.multipart.MultipartFile;

public interface UpdateProductsUseCase {

    void execute(MultipartFile file);

}
