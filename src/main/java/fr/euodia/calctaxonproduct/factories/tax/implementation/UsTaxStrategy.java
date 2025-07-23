package fr.euodia.calctaxonproduct.factories.tax.implementation;

import fr.euodia.calctaxonproduct.factories.tax.TaxStrategy;
import fr.euodia.calctaxonproduct.model.Product;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public class UsTaxStrategy implements TaxStrategy {

    private static final BigDecimal US_TAX_RATE = new BigDecimal("0.085"); // Couldn't found a global rate as it depends
                                                                           // on the state, so using a common rate of
                                                                           // 8.5%

    @Override
    public BigDecimal calculateTax(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Le produit ne peut pas être null");
        }

        return product.getPrice()
                .multiply(US_TAX_RATE)
                .setScale(2, RoundingMode.HALF_UP);
    }
}
