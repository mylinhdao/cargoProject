package storageContract.cargo;

import storageContract.administration.Customer;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Collection;

public class LiquidBulkCargoImpl extends CargoImpl implements LiquidBulkCargo {
    boolean isPressurized;
    public LiquidBulkCargoImpl(Customer customer, BigDecimal value, Duration durationOfStorage,
                               Collection<Hazard> hazards, boolean isPressurized){
        super(customer,value,durationOfStorage,hazards);
        this.isPressurized=isPressurized;
    }
    @Override
    public boolean isPressurized() {
        return isPressurized;
    }

}
