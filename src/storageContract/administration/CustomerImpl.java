package storageContract.administration;

import storageContract.cargo.Cargo;
import storageContract.cargo.CargoLager;

import java.math.BigDecimal;
import java.time.Duration;

public class CustomerImpl implements Customer {
    String name;
    public CustomerImpl(String name) {
        this.name=name;
    }
    @Override
    public String getName() {
        return name;
    }

    @Override
    public BigDecimal getMaxValue() {
        return new BigDecimal(0);
    }

    @Override
    public Duration getMaxDurationOfStorage() {
        return Duration.ofSeconds(0);
    }
}
