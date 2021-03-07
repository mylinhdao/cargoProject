package storageContract.cargo;

import storageContract.administration.Customer;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Collection;

public class UnitisedCargoImpl extends CargoImpl implements UnitisedCargo {
    boolean isFragile;
    public UnitisedCargoImpl(Customer customer, BigDecimal value, Duration durationOfStorage,
                               Collection<Hazard> hazards, boolean isFragile){
        super(customer,value,durationOfStorage,hazards);
        this.isFragile=isFragile;
    }
    @Override
    public boolean isFragile() {
        return isFragile;
    }
}
