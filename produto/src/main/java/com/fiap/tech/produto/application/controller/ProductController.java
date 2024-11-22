package com.fiap.tech.produto.application.controller;

import com.fiap.tech.produto.application.dto.ProductRequestDTO;
import com.fiap.tech.produto.application.dto.ProductResponseDTO;
import com.fiap.tech.produto.domain.mappers.ProductMapper;
import com.fiap.tech.produto.domain.useCase.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

	private final CreateProductUseCase createProductUseCase;

	private final FindProductUseCase findProductUseCase;

	private final FindProductByIdUseCase findProductByIdUseCase;

	private final DeleteProductByIdUseCase deleteProductByIdUseCase;

	private final ProductMapper mapper;
	private final UpdateProductUseCase updateProductUseCase;


	@PostMapping("/create")
	@ResponseStatus(HttpStatus.CREATED)
	public ProductResponseDTO createProduct(@Valid @RequestBody ProductRequestDTO requestDTO) {
		return mapper.toResponse(createProductUseCase.execute(mapper.toDomain(requestDTO)));
	}

	@GetMapping("/get")
	@ResponseStatus(HttpStatus.OK)
	public List<ProductResponseDTO> findProducts(){
		return findProductUseCase.execute().stream().map(mapper::toResponse).toList();
	}

	@GetMapping("/get/{id}")
	@ResponseStatus(HttpStatus.OK)
	public ProductResponseDTO findProductById(@PathVariable Long id) {
		return mapper.toResponse(findProductByIdUseCase.execute(id));
	}

	@PutMapping("/edit/{id}")
	@ResponseStatus(HttpStatus.OK)
	public ProductResponseDTO editProduct(@PathVariable Long id, @Valid @RequestBody ProductRequestDTO requestDTO) {
		return mapper.toResponse(updateProductUseCase.execute(id, mapper.toDomain(requestDTO)));
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void deleteProduct(@PathVariable Long id) {
		deleteProductByIdUseCase.execute(id);
	}
}
