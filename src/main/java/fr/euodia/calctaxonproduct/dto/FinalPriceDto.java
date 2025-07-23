package fr.euodia.calctaxonproduct.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;

@Schema(description = "DTO contenant les informations de prix final avec taxes calculées")
public class FinalPriceDto {

    @Schema(description = "ID du produit", example = "1")
    private final Long productId;

    @Schema(description = "Prix original du produit (hors taxes)", example = "100.00")
    private final BigDecimal originalPrice;

    @Schema(description = "Montant des taxes calculées", example = "20.00")
    private final BigDecimal taxAmount;

    @Schema(description = "Prix final avec taxes incluses", example = "120.00")
    private final BigDecimal finalPrice;

    public FinalPriceDto(Long productId,
            BigDecimal originalPrice,
            BigDecimal taxAmount,
            BigDecimal finalPrice) {
        this.productId = productId;
        this.originalPrice = originalPrice;
        this.taxAmount = taxAmount;
        this.finalPrice = finalPrice;
    }

    public Long getProductId() {
        return productId;
    }

    public BigDecimal getOriginalPrice() {
        return originalPrice;
    }

    public BigDecimal getTaxAmount() {
        return taxAmount;
    }

    public BigDecimal getFinalPrice() {
        return finalPrice;
    }
}
