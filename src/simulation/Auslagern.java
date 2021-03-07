package simulation;

import storageContract.cargo.Cargo;
import storageContract.cargo.CargoLager;
import storageContract.cargo.LagerVollException;
import storageContract.cargo.OwnerNotExistException;
import ui.ConsoleView;

public class Auslagern extends Thread {
    private CargoLager[] cargoLagers;
    private CargoLager mainCargoLager;
    private int threadnummer;
    private ConsoleView view;
    public Auslagern(CargoLager[] cargoLagers, CargoLager mainCargoLager, int threadnummer , ConsoleView view){
        this.cargoLagers=cargoLagers;
        this.mainCargoLager=mainCargoLager;
        this.threadnummer= threadnummer;
        this.view=view;
    }


    /**
     * umlagert den älteste Cargo in neue Ziellager
     * @param ziel
     * @throws LagerVollException
     * @throws OwnerNotExistException
     */
    public void umlagern(CargoLager ziel)throws LagerVollException, OwnerNotExistException {
        int old = 0;
        for (int i = 0; i < mainCargoLager.getCapacity() - 1; i++) {
            if (mainCargoLager.getStorageDate(i) == null) {
                continue;
            }
            //wenn der frachtplatz nicht leer ist

            if (mainCargoLager.getStorageDate(old) == null || mainCargoLager.getStorageDate(old).after(mainCargoLager.getStorageDate(i))) {
                old = i;
            }
        }
        Cargo cargo= mainCargoLager.getCargo(old);
        mainCargoLager.deleteCargo(old);
        view.showOutput("Auslagerthread "+threadnummer + " delete aus Lager "+mainCargoLager);
        ziel.addCargo(cargo);
        view.showOutput("Auslagerthread "+threadnummer+" added to Lager "+ziel);
    }

    @Override
    public void run(){
        while(true) {
            synchronized (mainCargoLager) {
                while (!mainCargoLager.isFull()) {
                    try {
                        view.showOutput("Auslagerthread "+threadnummer + " wartet");
                        mainCargoLager.wait(0);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if(!mainCargoLager.isFull()){ throw new IllegalStateException();}

                int random= (int)(Math.random()*cargoLagers.length);
                try {
                    umlagern(cargoLagers[random]);
                } catch (OwnerNotExistException e) {
                } catch (LagerVollException e) {
                    view.showOutput("Auslagerthread "+threadnummer+" not succesful with adding in Lager "+cargoLagers[random]);
                }
                mainCargoLager.notifyAll();

            }
        }
    }
}
