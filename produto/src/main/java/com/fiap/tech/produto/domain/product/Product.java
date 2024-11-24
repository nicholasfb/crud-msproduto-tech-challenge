package com.fiap.tech.produto.domain.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {

	private Long id;

	private String description;

	private Integer quantity;

	private Double purchasePrice;

	private Double salePrice;

	private Integer minimumStock;

	private Double lastPurchasePrice;

	private LocalDateTime createdAt;

	private LocalDateTime updatedAt;
}
