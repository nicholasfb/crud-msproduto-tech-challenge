package com.fiap.tech.produto.application.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ProductResponseDTO {

	private Long id;

	private String description;

	private Integer quantity;

	private Double purchasePrice;

	private Double salePrice;

	private Integer minimumStock;

	private Double lastPurchasePrice;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime createdAt;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime updatedAt;

}
