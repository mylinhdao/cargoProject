package storageContract.administration;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Duration;

public interface Customer extends Serializable {
    /**
     *
     * @return Name der Kunden
     */
    String getName();

    /**
     *
     * @return Anzahl der dem Kunden zugehörige Cargos
     */
    BigDecimal getMaxValue();

    /**
     *
     * @return längste Lagerdauer von dem Kunden zugehörige Cargos
     */
    Duration getMaxDurationOfStorage();
}
