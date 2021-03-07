import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import storageContract.administration.Customer;
import storageContract.administration.CustomerImpl;
import storageContract.administration.CustomerList;
import storageContract.cargo.*;


public class TestInterfaceMethodsCustomer {
    CustomerList customerList;
    CargoLager cargoLager;
    @BeforeEach
    void setUp(){
        customerList= new CustomerList();
        cargoLager= new CargoLager(customerList, 10);
    }
    @Test
    public void getName(){
        Customer customer = new CustomerImpl("Lotta");
        customerList.addCustomer(customer);
        Assertions.assertEquals(customer.getName(), "Lotta");
    }

}
