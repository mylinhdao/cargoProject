package uiTest;

import api.CargoLastStoragePlaceBeobachter;
import api.GefahrstoffeBeobachter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import routing.*;
import storageContract.administration.CustomerList;
import storageContract.cargo.CargoLager;
import ui.ConsoleController;
import ui.ConsoleView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.mockito.Mockito.*;

public class UITest {
    @Test
    public void test1(){
        CustomerList customerList= new CustomerList();
        CargoLager lager= new CargoLager(customerList,2);
        CustomerEinfuegenListener customerEinfuegenListener = new CustomerEinfuegenListener(customerList);
        CargoEinfuegenEventListener addCargoLiquidListener = new UnitisedCargoEinfuegenEventListener(lager);
        CargoEinfuegenEventListener addCargoUnitisedListener = new LiquidBulkCargoEinfuegenEventListener(lager);
        CargoLoeschenEventListener deleteCargoListener = new CargoLoeschenEventListener(lager);
        CustomerAnzeigenListener showCustomerListener = new CustomerAnzeigenListener(lager);
        ViewCustomerAnzeigeListener viewCustomerAnzeigeListener= new ViewCustomerAnzeigeListener();

        AddCustomerHandler addCustomer = new AddCustomerHandler();
        addCustomer.addEventListener(customerEinfuegenListener);
        AddCargoHandler addCargo = new AddCargoHandler();
        addCargo.addEventListener(addCargoLiquidListener);
        addCargo.addEventListener(addCargoUnitisedListener);
        CargoLoeschenHandler deleteCargo = new CargoLoeschenHandler();
        deleteCargo.addEventListener(deleteCargoListener);
        CustomerAnzeigenHandler anzeigenCustomer = new CustomerAnzeigenHandler();
        anzeigenCustomer.addEventListener(showCustomerListener);
        ViewAnzeigeHandler anzeigeHandler= new ViewAnzeigeHandler();
        anzeigeHandler.addEventListener(viewCustomerAnzeigeListener);
        showCustomerListener.setHandler(anzeigeHandler);
        ConsoleView view= new ConsoleView();

        CargoLastStoragePlaceBeobachter storagePlaceBeobachter= new CargoLastStoragePlaceBeobachter(view,lager);
        GefahrstoffeBeobachter gefahrstoffeBeobachter= new GefahrstoffeBeobachter(view, lager);
        InputStream oldInput= System.in;
        PrintStream ps= System.out;
        PrintStream currPS= mock(PrintStream.class);

        ByteArrayInputStream inputStream= new ByteArrayInputStream("Linh\nUnitisedCargo Linh 1 3 toxic , flammable y\ncustomer".getBytes());

        System.setIn(inputStream);
        System.setOut(currPS);
        ConsoleController controller= new ConsoleController(addCustomer,deleteCargo,addCargo,anzeigenCustomer);
        for(int i=0; i<3; i++){
            controller.readInput();
        }
        verify(currPS, times(1)).println("Gefahrstoffenliste hat sich geändert");
        verify(currPS, times(1)).println("Es gibt nur noch einen Platz");
        verify(currPS, times(1)).println("Linh 1");
        System.setIn(oldInput);
        System.setOut(ps);

    }
}
