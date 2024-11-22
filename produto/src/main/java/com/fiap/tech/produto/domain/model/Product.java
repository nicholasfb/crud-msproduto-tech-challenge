package com.fiap.tech.produto.domain.model;

import lombok.*;

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
