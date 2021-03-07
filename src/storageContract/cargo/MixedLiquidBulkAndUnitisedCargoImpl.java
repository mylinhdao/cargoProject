package storageContract.cargo;

import storageContract.administration.Customer;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Collection;

public class MixedLiquidBulkAndUnitisedCargoImpl extends UnitisedCargoImpl implements MixedLiquidBulkAndUnitisedCargo {
    boolean isPressurized;
    public MixedLiquidBulkAndUnitisedCargoImpl(Customer customer, BigDecimal value,
                                               Duration durationOfStorage, Collection<Hazard> hazards,
                                               boolean isFragile, boolean isPressurized){
        super(customer,value,durationOfStorage,hazards,isFragile);
        this.isPressurized=isPressurized;
    }

    @Override
    public boolean isPressurized() {
        return isPressurized;
    }

}
