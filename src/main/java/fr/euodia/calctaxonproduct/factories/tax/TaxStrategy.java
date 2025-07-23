package fr.euodia.calctaxonproduct.factories.tax;

import fr.euodia.calctaxonproduct.model.Product;
import java.math.BigDecimal;

public interface TaxStrategy {
    BigDecimal calculateTax(Product product);
}
