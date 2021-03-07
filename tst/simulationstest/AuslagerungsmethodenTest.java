package simulationstest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import simulation.Auslagern;
import storageContract.administration.Customer;
import storageContract.administration.CustomerImpl;
import storageContract.administration.CustomerList;
import storageContract.cargo.*;
import ui.ConsoleView;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Collections;
import java.util.Date;

import static org.mockito.Mockito.*;

public class AuslagerungsmethodenTest {
    ConsoleView view= new ConsoleView();
    @Test
    public void umlagern() throws LagerVollException, OwnerNotExistException {
        CustomerList customerList= new CustomerList();
        Customer customer= new CustomerImpl("Leo");
        customerList.addCustomer(customer);
        CargoLager lager= new CargoLager(customerList, 3);
        CargoLager lager2= new CargoLager(customerList, 4);
        CargoLager[] lagers1= {lager2};
        Auslagern auslagern= new Auslagern(lagers1,lager,1,view);

        int pos=lager.addCargo(new CargoImpl(customer,new BigDecimal(1),Duration.ofSeconds(1), Collections.singletonList(Hazard.explosive)));
        lager.addCargo(new CargoImpl(customer,new BigDecimal(1),Duration.ofSeconds(1), Collections.singletonList(Hazard.explosive)));
        Cargo cargo= lager.getCargo(pos);
        auslagern.umlagern(lager2);
        boolean umgelagert=false;
        for(int i=0; i<lager2.getCapacity(); i++){
            if(lager2.getCargo(i)== cargo){
                umgelagert=true;
                break;
            }
        }
        Assertions.assertTrue(umgelagert);
    }
    @Test
    public void getCapacity(){
        CustomerList customerList= new CustomerList();
        Customer customer= new CustomerImpl("Leo");
        customerList.addCustomer(customer);
        CargoLager lager= new CargoLager(customerList, 3);
        Assertions.assertEquals(3, lager.getCapacity());
    }
    @Test
    public void getStorageDate() throws LagerVollException, OwnerNotExistException {
        CustomerList customerList= new CustomerList();
        Customer customer= new CustomerImpl("Leo");
        customerList.addCustomer(customer);
        CargoLager lager= new CargoLager(customerList, 3);
        Date before= new Date();
        int pos=lager.addCargo(new CargoImpl(customer,new BigDecimal(1),Duration.ofSeconds(1), Collections.singletonList(Hazard.explosive)));
        Date after= new Date();
        Assertions.assertTrue(lager.getStorageDate(pos).compareTo(before)>=0);
        Assertions.assertTrue(lager.getStorageDate(pos).compareTo(after)<=0);

    }
    @Test
    public void addAndGetCargo() throws LagerVollException, OwnerNotExistException {
        CustomerList customerList= new CustomerList();
        Customer customer= new CustomerImpl("Leo");
        customerList.addCustomer(customer);
        CargoLager lager= new CargoLager(customerList, 3);
        Cargo cargo= new CargoImpl(customer,new BigDecimal(1),Duration.ofSeconds(1),Collections.singletonList(Hazard.explosive));
        int pos=lager.addCargo(cargo);
        Assertions.assertEquals(cargo, lager.getCargo(pos));
    }
}
