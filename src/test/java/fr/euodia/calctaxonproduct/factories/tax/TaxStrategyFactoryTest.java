package fr.euodia.calctaxonproduct.factories.tax;

import fr.euodia.calctaxonproduct.factories.tax.implementation.CanadaTaxStrategy;
import fr.euodia.calctaxonproduct.factories.tax.implementation.FranceTaxStrategy;
import fr.euodia.calctaxonproduct.factories.tax.implementation.UsTaxStrategy;
import fr.euodia.calctaxonproduct.model.Country;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Test de la factory pour les stratégies de taxation")
class TaxStrategyFactoryTest {

    @Mock
    private UsTaxStrategy usTaxStrategy;

    @Mock
    private CanadaTaxStrategy canadaTaxStrategy;

    @Mock
    private FranceTaxStrategy franceTaxStrategy;

    private TaxStrategyFactory taxStrategyFactory;

    @BeforeEach
    void setUp() {
        taxStrategyFactory = new TaxStrategyFactory(usTaxStrategy, canadaTaxStrategy, franceTaxStrategy);
    }

    @Test
    @DisplayName("Doit retourner la stratégie US pour le pays UNITED_STATES")
    void shouldReturnUsStrategyForUnitedStates() {
        TaxStrategy strategy = taxStrategyFactory.getStrategy(Country.UNITED_STATES);

        assertSame(usTaxStrategy, strategy);
    }

    @Test
    @DisplayName("Doit retourner la stratégie Canada pour le pays CANADA")
    void shouldReturnCanadaStrategyForCanada() {
        TaxStrategy strategy = taxStrategyFactory.getStrategy(Country.CANADA);

        assertSame(canadaTaxStrategy, strategy);
    }

    @Test
    @DisplayName("Doit retourner la stratégie France pour le pays FRANCE")
    void shouldReturnFranceStrategyForFrance() {
        TaxStrategy strategy = taxStrategyFactory.getStrategy(Country.FRANCE);

        assertSame(franceTaxStrategy, strategy);
    }

    @Test
    @DisplayName("Doit lever une exception si le pays est null")
    void shouldThrowExceptionWhenCountryIsNull() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> taxStrategyFactory.getStrategy(null));

        assertEquals("Le pays ne peut pas être null", exception.getMessage());
    }
}
