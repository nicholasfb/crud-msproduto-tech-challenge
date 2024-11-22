package com.fiap.tech.produto.application.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class ProductRequestDTO {

	@NotNull
	public String description;

	@NotNull
	public Integer quantity;

	@NotNull
	public Double purchasePrice;

	@NotNull
	public Double salePrice;

	@NotNull
	public Integer minimumStock;

	@NotNull
	public Double lastPurchasePrice;

}
