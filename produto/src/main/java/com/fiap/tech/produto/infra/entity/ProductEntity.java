package com.fiap.tech.produto.infra.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "products")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, updatable = false)
	private Long id;

	@Column(name = "description", nullable = false)
	private String description;

	@Column(name = "quantity", nullable = false)
	private Integer quantity;

	@Column(name = "purchasePrice", nullable = false)
	private Double purchasePrice;

	@Column(name = "salePrice", nullable = false)
	private Double salePrice;

	@Column(name = "minimumStock", nullable = false)
	private Integer minimumStock;

	@Column(name = "lastPurchasePrice", nullable = false)
	private Double lastPurchasePrice;

	@Column(name = "createdAt", nullable = false, updatable = false)
	private LocalDateTime createdAt;

	@Column(name = "updatedAt", nullable = false)
	private LocalDateTime updatedAt;

	@PrePersist
	protected void onCreate() {
		this.createdAt = LocalDateTime.now();
		this.updatedAt = LocalDateTime.now();
	}

	@PreUpdate
	protected void onUpdate() {
		this.updatedAt = LocalDateTime.now();
	}

}
