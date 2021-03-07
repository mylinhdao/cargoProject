import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;
import storageContract.administration.Customer;
import storageContract.administration.CustomerImpl;
import storageContract.administration.CustomerList;
import storageContract.cargo.*;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Collections;

import static org.mockito.Mockito.*;

public class MockitoTest {
    CustomerList customerList;
    CargoLager cargoLager;
    @BeforeEach
    void setUp(){
        customerList= new CustomerList();
        cargoLager= new CargoLager(customerList,10);
    }
    @Test
    public void addCustomer(){
        Customer customer= mock(CustomerImpl.class);
        when(customer.getName()).thenReturn("Anna");
        customerList.addCustomer(customer);
        Assertions.assertTrue(customerList.getCustomer("Anna")!=null);
    }
    @Test
    public void deleteAllCustomer(){
        Customer customer= mock(CustomerImpl.class);
        when(customer.getName()).thenReturn("Anna");
        customerList.addCustomer(customer);
        customerList.deleteAll();
        Assertions.assertTrue(customerList.showCustomer().size()==0);
    }
    @Test
    public void addCargo_VerifyExistingOfOwner() throws LagerVollException, OwnerNotExistException {
        Customer customer= mock(CustomerImpl.class);
        when(customer.getName()).thenReturn("Anna");
        customerList.addCustomer(customer);
        MixedLiquidBulkAndUnitisedCargo cargo= mock(MixedLiquidBulkAndUnitisedCargoImpl.class);
        when(cargo.getOwner()).thenReturn(customer);
        when(cargo.getDurationOfStorage()).thenReturn(Duration.ofSeconds(100));
        when(cargo.getHazards()).thenReturn(Collections.singletonList(Hazard.toxic));
        when(cargo.getValue()).thenReturn(new BigDecimal(900));
        int position=cargoLager.addCargo(cargo);
        InOrder order= inOrder(cargo);
        order.verify(cargo, times(2)).getOwner();
        Assertions.assertTrue(cargoLager.getCargo(position).getOwner()==customer);

    }

}
