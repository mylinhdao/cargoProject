package routing;

import storageContract.administration.CustomerImpl;
import storageContract.cargo.CargoLager;
import storageContract.cargo.LagerVollException;
import storageContract.cargo.OwnerNotExistException;
import storageContract.cargo.UnitisedCargoImpl;

public class LiquidBulkCargoEinfuegenEventListener extends CargoEinfuegenEventListener{
    public LiquidBulkCargoEinfuegenEventListener(CargoLager lager){
        this.lager=lager;
    }

    @Override
    public void onInputEvent(CargoEinfuegenEvent event) {
        this.getMembers(event);
        if(cargoType.equals("LiquidBulkCargo")){
            try {
                lager.addCargo(new UnitisedCargoImpl(new CustomerImpl(name),value,duration, newCargoHazard,eigenschaft));
            } catch (OwnerNotExistException e) {
            } catch (LagerVollException ex) {
            }
        }
    }
}
