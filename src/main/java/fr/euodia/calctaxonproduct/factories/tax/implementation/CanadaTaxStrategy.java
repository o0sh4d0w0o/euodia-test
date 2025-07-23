package fr.euodia.calctaxonproduct.factories.tax.implementation;

import fr.euodia.calctaxonproduct.factories.tax.TaxStrategy;
import fr.euodia.calctaxonproduct.model.Product;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public class CanadaTaxStrategy implements TaxStrategy {

    private static final BigDecimal GST_RATE = new BigDecimal("0.05");  // Global
    private static final BigDecimal PST_RATE = new BigDecimal("0.07");  // Based on Colombie-Britannique PST, because
                                                                            // that depends of province

    @Override
    public BigDecimal calculateTax(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Le produit ne peut pas être null");
        }

        BigDecimal gst = product.getPrice().multiply(GST_RATE);
        BigDecimal pst = product.getPrice().multiply(PST_RATE);
        BigDecimal hst = gst.add(pst);

        return hst.setScale(2, RoundingMode.HALF_UP);
    }
}
