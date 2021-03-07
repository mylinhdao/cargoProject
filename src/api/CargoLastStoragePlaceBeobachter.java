package api;

import storageContract.cargo.CargoLager;
import ui.ConsoleView;

public class CargoLastStoragePlaceBeobachter implements Beobachter {
    CargoLager lager;
    ConsoleView view;
    int capacity;
    public CargoLastStoragePlaceBeobachter(ConsoleView view,CargoLager lager){
        this.lager=lager;
        lager.meldeAn(this);
        capacity=lager.getCapacity();
        this.view=view;
        update();
    }
    @Override
    public synchronized void update() {

        int numberOfCargos= lager.getNumberOfCargo();
        if(numberOfCargos==capacity-1){
            view.showOutput("Es gibt nur noch einen Platz in Lager "+lager);
        }
    }
}
