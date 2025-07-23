package fr.euodia.calctaxonproduct.dto;

import java.math.BigDecimal;

public class FinalPriceDto {
    
    private final Long productId;
    private final BigDecimal originalPrice;
    private final BigDecimal taxAmount;
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
