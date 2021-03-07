package api;

import storageContract.cargo.CargoLager;
import storageContract.cargo.Hazard;
import ui.ConsoleView;

import java.util.Collection;
import java.util.HashSet;

public class GefahrstoffeBeobachter implements Beobachter {
    CargoLager subjekt;
    Collection<Hazard> currentHazard= new HashSet<>();
    ConsoleView view;
    public GefahrstoffeBeobachter(ConsoleView view, CargoLager subjekt){
        this.subjekt=subjekt;
        this.view=view;
        subjekt.meldeAn(this);
        for(Hazard h: subjekt.getIncludedHazard()){
            currentHazard.add(h);
        }
    }
    @Override
    public synchronized void update() {
        Collection<Hazard> newHazard = subjekt.getIncludedHazard();
        if(!(newHazard.containsAll(currentHazard)&& currentHazard.containsAll(newHazard))) {
            view.showOutput("Gefahrstoffenliste hat sich geändert in Lager "+subjekt);
            currentHazard= new HashSet<>();
            for(Hazard h: subjekt.getIncludedHazard()){
                currentHazard.add(h);
            }
        }
    }
}
