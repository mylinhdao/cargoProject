package routing;


import storageContract.cargo.*;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.*;

public abstract class CargoEinfuegenEventListener {
    CargoLager lager;
    String name;
    String cargoType;
    BigDecimal value;
    Duration duration;
    boolean eigenschaft;
    HashSet<Hazard> newCargoHazard = new HashSet<>();


    public abstract void onInputEvent(CargoEinfuegenEvent event);

    protected void getMembers(CargoEinfuegenEvent event) {
        name = event.getCustomer();
        cargoType = event.getCargoType();
        value = event.getValue();
        duration = event.getDurarion();

        eigenschaft = event.isPressurizedOrFragile();

        Collection<String> newCargoHazardString = event.getHazards();

        for (String h : newCargoHazardString) {
            switch (h) {
                case "explosive":
                    newCargoHazard.add(Hazard.explosive);
                    break;
                case "flammable":
                    newCargoHazard.add(Hazard.flammable);
                    break;
                case "toxic":
                    newCargoHazard.add(Hazard.toxic);
                    break;
                case "radioactive":
                    newCargoHazard.add(Hazard.radioactive);
                    break;

            }

        }

    }
}
