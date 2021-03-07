import api.CargoLastStoragePlaceBeobachter;
import api.GefahrstoffeBeobachter;
import simulation.Auslagern;
import simulation.Einlagern;
import storageContract.administration.Customer;
import storageContract.administration.CustomerImpl;
import storageContract.administration.CustomerList;
import storageContract.cargo.CargoLager;
import ui.ConsoleController;
import ui.ConsoleView;

import java.util.ArrayList;

public class MainSimulation {
    public static void main(String[] args){
        ConsoleView view= new ConsoleView();

        CustomerList customerList = new CustomerList();
        Customer customer = new CustomerImpl("Linh");
        customerList.addCustomer(customer);
        CargoLager ersteLager = new CargoLager(customerList, 5);
        CargoLager zweiteLager = new CargoLager(customerList, 5);
        CargoLager dritteLager= new CargoLager(customerList, 5);
        CargoLager[] neighborVonLager1= {zweiteLager, dritteLager};
        CargoLager[] neighborVonLager2= {ersteLager, dritteLager};
        CargoLager[] neighborVonLager3= {ersteLager, zweiteLager};
        CargoLastStoragePlaceBeobachter storagePlaceBeobachter1= new CargoLastStoragePlaceBeobachter(view,ersteLager);
        CargoLastStoragePlaceBeobachter storagePlaceBeobachter2= new CargoLastStoragePlaceBeobachter(view,zweiteLager);
        CargoLastStoragePlaceBeobachter storagePlaceBeobachter3= new CargoLastStoragePlaceBeobachter(view,dritteLager);

        GefahrstoffeBeobachter gefahrstoffeBeobachter1= new GefahrstoffeBeobachter(view,ersteLager);
        GefahrstoffeBeobachter gefahrstoffeBeobachter2= new GefahrstoffeBeobachter(view,zweiteLager);
        GefahrstoffeBeobachter gefahrstoffeBeobachter3= new GefahrstoffeBeobachter(view, dritteLager);

        CargoLager[] lagerListe={ersteLager, zweiteLager};
        Einlagern einlagerungsthread2 = new Einlagern(lagerListe, customer, 2, view);
        Einlagern einlagerungsthread = new Einlagern(lagerListe, customer, 1, view);

        Auslagern auslagern1= new Auslagern(neighborVonLager1, ersteLager, 1, view);
        Auslagern auslagern2= new Auslagern(neighborVonLager2, zweiteLager, 2, view);
        Auslagern auslagern3= new Auslagern(neighborVonLager3,dritteLager, 3, view);

        einlagerungsthread2.start();
        einlagerungsthread.start();
        auslagern1.start();
        auslagern2.start();
        auslagern3.start();
    }
}
