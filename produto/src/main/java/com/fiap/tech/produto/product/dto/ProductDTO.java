package com.fiap.tech.produto.product.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class ProductDTO {

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

    private Long id;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private LocalDateTime createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private LocalDateTime updatedAt;

}
