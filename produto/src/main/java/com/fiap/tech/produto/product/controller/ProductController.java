package com.fiap.tech.produto.product.controller;

import com.fiap.tech.produto.core.useCase.CreateProductUseCase;
import com.fiap.tech.produto.core.useCase.DeleteProductByIdUseCase;
import com.fiap.tech.produto.core.useCase.FindProductByIdUseCase;
import com.fiap.tech.produto.core.useCase.FindProductUseCase;
import com.fiap.tech.produto.core.useCase.UpdateProductUseCase;
import com.fiap.tech.produto.core.useCase.UpdateProductsUseCase;
import com.fiap.tech.produto.product.adapter.ProductAdapter;
import com.fiap.tech.produto.product.dto.ProductDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class ProductController {

    private final CreateProductUseCase createProductUseCase;

    private final FindProductUseCase findProductUseCase;

    private final FindProductByIdUseCase findProductByIdUseCase;

    private final DeleteProductByIdUseCase deleteProductByIdUseCase;

    private final ProductAdapter productAdapter = ProductAdapter.INSTANCE;

    private final UpdateProductUseCase updateProductUseCase;

    private final UpdateProductsUseCase updateProductsUseCase;


    @PostMapping("/product")
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDTO createProduct(@Valid @RequestBody ProductDTO requestDTO) {
        return productAdapter.fromDomain(createProductUseCase.execute(
                productAdapter.toDomain(requestDTO)));
    }

    @GetMapping("/product")
    @ResponseStatus(HttpStatus.OK)
    public List<ProductDTO> findProducts() {
        return findProductUseCase.execute().stream().map(productAdapter::fromDomain).toList();
    }

    @GetMapping("/product/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductDTO findProductById(@PathVariable Long id) {
        return productAdapter.fromDomain(findProductByIdUseCase.execute(id));
    }

    @PutMapping("/product/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductDTO editProduct(@PathVariable Long id, @Valid @RequestBody
    ProductDTO requestDTO) {
        return productAdapter.fromDomain(
                updateProductUseCase.execute(id, productAdapter.toDomain(requestDTO)));
    }

    @DeleteMapping("/product/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteProduct(@PathVariable Long id) {
        deleteProductByIdUseCase.execute(id);
    }

    @PutMapping("/product/batch")
    @ResponseStatus(HttpStatus.OK)
    public void editProducts(
            @RequestParam(name = "file") final MultipartFile file
    ) {
        updateProductsUseCase.execute(file);
    }

}
