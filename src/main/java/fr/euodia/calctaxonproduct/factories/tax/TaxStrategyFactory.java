package fr.euodia.calctaxonproduct.factories.tax;

import fr.euodia.calctaxonproduct.factories.tax.implementation.CanadaTaxStrategy;
import fr.euodia.calctaxonproduct.factories.tax.implementation.FranceTaxStrategy;
import fr.euodia.calctaxonproduct.factories.tax.implementation.UsTaxStrategy;
import fr.euodia.calctaxonproduct.model.Country;

import org.springframework.stereotype.Component;

@Component
public class TaxStrategyFactory {
    private final UsTaxStrategy usTaxStrategy;
    private final CanadaTaxStrategy canadaTaxStrategy;
    private final FranceTaxStrategy franceTaxStrategy;

    public TaxStrategyFactory(UsTaxStrategy usTaxStrategy,
            CanadaTaxStrategy canadaTaxStrategy,
            FranceTaxStrategy franceTaxStrategy) {
        this.usTaxStrategy = usTaxStrategy;
        this.canadaTaxStrategy = canadaTaxStrategy;
        this.franceTaxStrategy = franceTaxStrategy;
    }

    public TaxStrategy getStrategy(Country country) {
        if (country == null) {
            throw new IllegalArgumentException("Le pays ne peut pas être null");
        }

        return switch (country) {
            case UNITED_STATES -> usTaxStrategy;
            case CANADA -> canadaTaxStrategy;
            case FRANCE -> franceTaxStrategy;
        };
    }
}
