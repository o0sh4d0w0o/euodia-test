package fr.euodia.calctaxonproduct.factories.tax.implementation;

import fr.euodia.calctaxonproduct.factories.tax.TaxStrategy;
import fr.euodia.calctaxonproduct.model.Product;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public class FranceTaxStrategy implements TaxStrategy {

    private static final BigDecimal FRANCE_VAT_RATE = new BigDecimal("0.20");

    @Override
    public BigDecimal calculateTax(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Le produit ne peut pas être null");
        }

        return product.getPrice()
                .multiply(FRANCE_VAT_RATE)
                .setScale(2, RoundingMode.HALF_UP);
    }
}
