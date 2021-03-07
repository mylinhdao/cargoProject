package simulation;

import storageContract.administration.Customer;
import storageContract.cargo.*;
import ui.ConsoleView;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Collection;
import java.util.Collections;

public class Einlagern extends Thread{
    private int threadNumber;
    private Customer mainCustomer;
    ConsoleView view;
    private CargoLager[] lagerListe;
    int cargoNumber;
    public Einlagern(CargoLager[] lagerListe, Customer customer, int threadNumber, ConsoleView view) {
        this.threadNumber= threadNumber;
        this.mainCustomer=customer;
        this.lagerListe=lagerListe;
        this.view=view;
    }
    @Override
    public void run(){
        while(true){
            // create random cargo and choose random storage
            cargoNumber=lagerListe.length;
            int random= (int)(Math.random()*cargoNumber);
            CargoLager currentLager= lagerListe[random];

            synchronized (currentLager) {
                BigDecimal value = new BigDecimal((int) (Math.random() * Integer.MAX_VALUE));
                Duration duration = Duration.ofSeconds((long) (Math.random() * Long.MAX_VALUE));
                Collection<Hazard> hazards = Collections.singletonList(Hazard.toxic);

                Cargo newCargo = new CargoImpl(mainCustomer, value, duration, hazards);
                try {
                    currentLager.addCargo(newCargo);
                    view.showOutput("Einlagerthread "+threadNumber+ " added in Lager "+currentLager);

                } catch (OwnerNotExistException e) {
                } catch (LagerVollException e) {
                    currentLager.notifyAll();
                    try {
                        view.showOutput("Einlagerthread "+threadNumber+ " wartet, weil Lager"+currentLager +" voll");
                        while(currentLager.isFull()){currentLager.wait(0);}
                        if(currentLager.isFull()){
                            throw new IllegalStateException();
                        }

                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
            }
        }}

    }

}
