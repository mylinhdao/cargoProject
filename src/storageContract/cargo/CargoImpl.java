package storageContract.cargo;

import storageContract.administration.Customer;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Collection;
import java.util.Date;

public class CargoImpl implements Cargo {

    private Customer owner;
    private BigDecimal value;
    private Duration durationOfStorage;
    private Collection<Hazard> listOfHazards;
    private CargoLager lager;

    public CargoImpl(Customer owner, BigDecimal value, Duration durationOfStorage,
                     Collection<Hazard> listOfHazards){
        this.owner=owner;
        this.value= value;
        this.durationOfStorage= durationOfStorage;
        this.listOfHazards= listOfHazards;
    }

    @Override
    public Customer getOwner() {
        return owner;
    }

    @Override
    public BigDecimal getValue() {
        return value;
    }

    @Override
    public Duration getDurationOfStorage() {
        return durationOfStorage;
    }

    @Override
    public Collection<Hazard> getHazards() {
        return listOfHazards;
    }

    @Override
    public Date getLastInspectionDate() {
        return new Date();
    }

}
