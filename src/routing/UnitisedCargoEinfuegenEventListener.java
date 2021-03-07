package routing;

import storageContract.administration.CustomerImpl;
import storageContract.cargo.CargoLager;
import storageContract.cargo.LagerVollException;
import storageContract.cargo.LiquidBulkCargoImpl;
import storageContract.cargo.OwnerNotExistException;

public class UnitisedCargoEinfuegenEventListener extends CargoEinfuegenEventListener {
    public UnitisedCargoEinfuegenEventListener(CargoLager lager){
        this.lager=lager;
    }
    @Override
    public void onInputEvent(CargoEinfuegenEvent event) {
        getMembers(event);
        if(cargoType.equals("UnitisedCargo")){
            this.getMembers(event);
            try {
                lager.addCargo(new LiquidBulkCargoImpl(new CustomerImpl(name), value, duration, newCargoHazard, eigenschaft));
            } catch (OwnerNotExistException e) {
            } catch (LagerVollException e) {
            }
        }
    }
}
