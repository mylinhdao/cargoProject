package uiTest;

import org.junit.jupiter.api.Test;
import routing.*;

import storageContract.administration.CustomerList;
import storageContract.cargo.*;
import ui.ConsoleController;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Collection;
import java.util.Collections;
import java.util.logging.Handler;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class ListenerTest {
    @Test
    // Event wird behandelt
    public void addCustomerUsesEvent(){
        CustomerList customerList= new CustomerList();
        CustomerEinfuegenEvent event= mock(CustomerEinfuegenEvent.class);
        when(event.getName()).thenReturn("Leona");
        CustomerEinfuegenListener listener= new CustomerEinfuegenListener(customerList);
        listener.onInputEvent(event);
        verify(event, times(1)).getName();

    }
    @Test
    //Auf Geschäftslogik wird zugegriffen und verändert
    public void addCustomerDomainLogic(){
        CustomerList customerList= mock(CustomerList.class);
        CustomerEinfuegenEvent event= mock(CustomerEinfuegenEvent.class);
        when(event.getName()).thenReturn("Benny");
        CustomerEinfuegenListener listener= new CustomerEinfuegenListener(customerList);
        listener.onInputEvent(event);
        verify(customerList,times(1)).addCustomer(any());

    }
    @Test
    //auf die Source wird nicht zugegriffen
    public void addCustomerNoSourceCast(){
        CustomerList customerList= new CustomerList();
        CustomerEinfuegenEvent event= mock(CustomerEinfuegenEvent.class);
        when(event.getName()).thenReturn("Ebert");
        CustomerEinfuegenListener listener= new CustomerEinfuegenListener(customerList);
        listener.onInputEvent(event);
        verify(event, never()).getSource();
    }
    @Test
    // Event wird behandelt
    public void deleteCargoUsesEvent(){
        CargoLager lager= mock(CargoLager.class);
        CargoLoeschenEvent event= mock(CargoLoeschenEvent.class);
        when(event.getPosition()).thenReturn(3);
        CargoLoeschenEventListener listener= new CargoLoeschenEventListener(lager);
        listener.onInputEvent(event);
        verify(event, times(1)).getPosition();


    }
    @Test
    //Auf Geschäftslogik wird zugegriffen und verändert
    public void deleteCargoDomainLogic(){
        CargoLager lager= mock(CargoLager.class);
        CargoLoeschenEvent event= mock(CargoLoeschenEvent.class);
        when(event.getPosition()).thenReturn(9);
        CargoLoeschenEventListener listener= new CargoLoeschenEventListener(lager);
        listener.onInputEvent(event);
        verify(lager,times(1)).deleteCargo(9);


    }
    @Test
    //auf die Source wird nicht zugegriffen
    public void deleteCargoNoSourceCast(){
        CargoLager lager= mock(CargoLager.class);
        CargoLoeschenEvent event= mock(CargoLoeschenEvent.class);
        when(event.getPosition()).thenReturn(1);
        CargoLoeschenEventListener listener= new CargoLoeschenEventListener(lager);
        listener.onInputEvent(event);
        verify(event, never()).getSource();

    }

}
