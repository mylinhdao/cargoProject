package routing;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Collection;
import java.util.EventObject;
import java.util.HashSet;

public class CargoEinfuegenEvent extends EventObject {
    String cargoType;
    String customer;
    BigDecimal value;
    Duration durarion;
    Collection<String> hazards;
    boolean pressurizedOrFragile;

    public String getCustomer() {
        return customer;
    }

    public CargoEinfuegenEvent(Object o, String cargoType, String customer, BigDecimal value, Duration duration, Collection<String> hazards, boolean last){
        super(o);
        this.cargoType=cargoType;
        this.customer=customer;
        this.value=value;
        this.hazards=hazards;
        this.durarion=duration;
        this.pressurizedOrFragile=last;
    }

    public String getCargoType() {
        return cargoType;
    }

    public BigDecimal getValue() {
        return value;
    }

    public Duration getDurarion() {
        return durarion;
    }

    public Collection<String> getHazards() {
        return hazards;
    }

    public boolean isPressurizedOrFragile() {
        return pressurizedOrFragile;
    }
}
