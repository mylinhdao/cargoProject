package uiTest;

import api.CargoLastStoragePlaceBeobachter;
import api.GefahrstoffeBeobachter;
import org.junit.jupiter.api.Test;
import storageContract.administration.Customer;
import storageContract.administration.CustomerImpl;
import storageContract.administration.CustomerList;
import storageContract.cargo.*;
import ui.ConsoleView;

import java.io.Console;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.Collections;

import static org.mockito.Mockito.*;

public class BeobachterTest {
    ConsoleView view= new ConsoleView();

    @Test
    // benutzt nur getIncludedHazards()
    public void hazardBeobachter(){
        CargoLager lager= mock(CargoLager.class);
        when(lager.getCapacity()).thenReturn(2);
        GefahrstoffeBeobachter beobachter= new GefahrstoffeBeobachter(view,lager);
        verify(lager, times(1)).meldeAn(beobachter);
        verify(lager,atLeastOnce()).getIncludedHazard();
        verifyNoMoreInteractions(lager);
    }
    @Test
    // wird bei der Initialisierung angemeldet
    public void hazardBeobachterAnmelden(){

        CargoLager lager= mock(CargoLager.class);
        when(lager.getCapacity()).thenReturn(2);
        GefahrstoffeBeobachter beobachter= new GefahrstoffeBeobachter(view,lager);
        verify(lager, times(1)).meldeAn(beobachter);

    }
    @Test
    // prüft ob der Beobachter die Änderung meldet
    public void hazardBeobachterLagerGeaendert() throws LagerVollException, OwnerNotExistException {
        PrintStream ps= System.out;

        PrintStream currentPS= mock(PrintStream.class);
        System.setOut(currentPS);
        CustomerList customerList= new CustomerList();
        Customer customer= new CustomerImpl("Linh");
        customerList.addCustomer(customer);
        CargoLager lager= new CargoLager(customerList, 2);
        GefahrstoffeBeobachter beobachter= new GefahrstoffeBeobachter(view,lager);
        lager.addCargo(new UnitisedCargoImpl(customer, new BigDecimal(1), Duration.ofSeconds(1), Collections.singletonList(Hazard.explosive), true));
        verify(currentPS).println("Gefahrstoffenliste hat sich geändert");

    }
    @Test
    //keine Änderung -> keine Ausgabe
    public void hazardBeobachterNoChange() throws LagerVollException, OwnerNotExistException {
        PrintStream ps= System.out;

        PrintStream currentPS= mock(PrintStream.class);
        System.setOut(currentPS);
        CustomerList customerList= new CustomerList();
        Customer customer= new CustomerImpl("Linh");
        customerList.addCustomer(customer);
        CargoLager lager= new CargoLager(customerList, 3);
        GefahrstoffeBeobachter beobachter= new GefahrstoffeBeobachter(view,lager);
        lager.addCargo(new UnitisedCargoImpl(customer, new BigDecimal(1), Duration.ofSeconds(1), Collections.EMPTY_LIST, true));
        verifyZeroInteractions(currentPS);

    }
    @Test
    //eine Änderung -> eine Meldung
    public void hazardOneChange() throws LagerVollException, OwnerNotExistException {
        PrintStream ps= System.out;

        PrintStream currentPS= mock(PrintStream.class);
        System.setOut(currentPS);
        CustomerList customerList= new CustomerList();
        Customer customer= new CustomerImpl("Linh");
        customerList.addCustomer(customer);
        CargoLager lager= new CargoLager(customerList, 3);
        GefahrstoffeBeobachter beobachter= new GefahrstoffeBeobachter(view,lager);
        lager.addCargo(new UnitisedCargoImpl(customer, new BigDecimal(1), Duration.ofSeconds(1), Collections.singletonList(Hazard.toxic), true));
        lager.addCargo(new LiquidBulkCargoImpl(customer, new BigDecimal(1), Duration.ofSeconds(2), Collections.singletonList(Hazard.toxic), false));
        verify(currentPS, times(1)).println("Gefahrstoffenliste hat sich geändert");
        verifyNoMoreInteractions(currentPS);
    }
    @Test
    // benutzt nur getCapacity()
    public void lastStoragePlaceBeobachter() throws LagerVollException, OwnerNotExistException {
        CargoLager lager= mock(CargoLager.class);
        when(lager.getCapacity()).thenReturn(2);
        CargoLastStoragePlaceBeobachter beobachter= new CargoLastStoragePlaceBeobachter(view,lager);
        lager.addCargo(mock(Cargo.class));
        verify(lager, times(1)).meldeAn(beobachter);
        verify(lager,times(1)).getCapacity();
        verify(lager,times(1)).addCargo(any());
        verify(lager,times(3)).showCargos(anyInt());
        verifyNoMoreInteractions(lager);
    }
    @Test
    // wird bei der Initialisierung angemeldet
    public void lastStorageBeobachterAnmelden(){
        CargoLager lager= mock(CargoLager.class);
        when(lager.getCapacity()).thenReturn(2);
        GefahrstoffeBeobachter beobachter= new GefahrstoffeBeobachter(view,lager);
        verify(lager, times(1)).meldeAn(beobachter);

    }
    @Test
    // prüft ob der Beobachter die Änderung meldet
    public void lastStorageBeobachterLagerGeaendert() throws LagerVollException, OwnerNotExistException {
        PrintStream ps= System.out;

        PrintStream currentPS= mock(PrintStream.class);
        System.setOut(currentPS);
        CustomerList customerList= new CustomerList();
        Customer customer= new CustomerImpl("Linh");
        customerList.addCustomer(customer);
        CargoLager lager= new CargoLager(customerList, 2);
        CargoLastStoragePlaceBeobachter beobachter= new CargoLastStoragePlaceBeobachter(view,lager);
        lager.addCargo(new UnitisedCargoImpl(customer, new BigDecimal(1), Duration.ofSeconds(1), Collections.singletonList(Hazard.explosive), true));
        verify(currentPS).println("Es gibt nur noch einen Platz");

    }
    @Test
    //keine Änderung -> keine Ausgabe
    public void lastStorageBeobachterNoChange() throws LagerVollException, OwnerNotExistException {
        PrintStream ps= System.out;

        PrintStream currentPS= mock(PrintStream.class);
        System.setOut(currentPS);
        CustomerList customerList= new CustomerList();
        Customer customer= new CustomerImpl("Linh");
        customerList.addCustomer(customer);
        CargoLager lager= new CargoLager(customerList, 3);
        CargoLastStoragePlaceBeobachter beobachter= new CargoLastStoragePlaceBeobachter(view,lager);
        lager.addCargo(new UnitisedCargoImpl(customer, new BigDecimal(1), Duration.ofSeconds(1), Collections.EMPTY_LIST, true));
        verifyZeroInteractions(currentPS);

    }
}
