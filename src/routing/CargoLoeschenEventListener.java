package routing;

import storageContract.administration.CustomerList;
import storageContract.cargo.CargoLager;

public class CargoLoeschenEventListener {
    CargoLager lager;
    public CargoLoeschenEventListener(CargoLager lager){
        this.lager=lager;
    }
    public void onInputEvent(CargoLoeschenEvent event) {
        lager.deleteCargo(event.getPosition());

    }
}
