import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import storageContract.administration.Customer;
import storageContract.administration.CustomerImpl;
import storageContract.administration.CustomerList;
import storageContract.cargo.*;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class AddCargoTest {
    CustomerList customerList;
    CargoLager cargoLager;
    @BeforeEach
    void setUp(){
        customerList= new CustomerList();
        cargoLager= new CargoLager(customerList, 10);

    }
    @Test
    public void add_LiquidBulkCargo_to_existing_customer() throws LagerVollException, OwnerNotExistException {
        Customer customer = new CustomerImpl("Lisa");
        customerList.addCustomer(customer);
        Collection<Hazard> hazards = new ArrayList<>();
        hazards.add(Hazard.explosive);
        hazards.add(Hazard.flammable);
        Cargo cargo = new LiquidBulkCargoImpl(customer, new BigDecimal(1200), Duration.ofSeconds(4000), hazards, true);
        int storagePosition = cargoLager.addCargo(cargo);

        // check if cargo is in storage and if it has a position and storage date
        Assertions.assertSame(cargoLager.getCargo(storagePosition), cargo);
        Assertions.assertTrue(cargoLager.getStorageDate(storagePosition) != null);

    }

    @Test
    public void add_UnitisedCargo() throws LagerVollException, OwnerNotExistException {
        Customer customer = new CustomerImpl("Lisa");
        customerList.addCustomer(customer);
        Collection<Hazard> hazards = new ArrayList<>();
        hazards.add(Hazard.explosive);
        hazards.add(Hazard.flammable);
        Cargo cargo = new UnitisedCargoImpl(customer, new BigDecimal(1200), Duration.ofSeconds(4000), hazards, true);
        int storagePosition = cargoLager.addCargo(cargo);

        // check if cargo is in storage and if it has a position and storage date
        Assertions.assertSame(cargoLager.getCargo(storagePosition), cargo);
        Assertions.assertTrue(cargoLager.getStorageDate(storagePosition) != null);

    }

    @Test
    public void add_MixedCargo() throws LagerVollException, OwnerNotExistException {
        Customer customer = new CustomerImpl("Lisa");
        customerList.addCustomer(customer);
        Collection<Hazard> hazards = new ArrayList<>();
        hazards.add(Hazard.explosive);
        hazards.add(Hazard.flammable);
        Cargo cargo = new MixedLiquidBulkAndUnitisedCargoImpl(customer, new BigDecimal(1200), Duration.ofSeconds(4000), hazards, true, false);

        int storagePosition = cargoLager.addCargo(cargo);

        // check if cargo is in storage and if it has a position and storage date
        Assertions.assertSame(cargoLager.getCargo(storagePosition), cargo);
        Assertions.assertTrue(cargoLager.getStorageDate(storagePosition) != null);

    }
    @Test
    void customerNotExist() throws LagerVollException, OwnerNotExistException {
        Customer customer = new CustomerImpl("Lisa");
        Cargo cargo = new MixedLiquidBulkAndUnitisedCargoImpl(customer, new BigDecimal(1200), Duration.ofSeconds(4000), Collections.singletonList(Hazard.toxic), true, false);

        Assertions.assertThrows(OwnerNotExistException.class, () ->{cargoLager.addCargo(cargo);});

    }
    @Test
    public void lagerVoll() throws LagerVollException, OwnerNotExistException {
        Customer customer = new CustomerImpl("Lisa");
        customerList.addCustomer(customer);
        Collection<Hazard> hazards = new ArrayList<>();
        hazards.add(Hazard.explosive);
        Cargo cargo = new MixedLiquidBulkAndUnitisedCargoImpl(customer, new BigDecimal(1200), Duration.ofSeconds(4000), hazards, true, false);
        for(int i=0; i< 10; i++){
            cargoLager.addCargo(cargo);
        }
        Assertions.assertThrows(LagerVollException.class, () -> {        cargoLager.addCargo(cargo);
        });
    }
    @Test
    public void ownerIsNull(){
        Customer customer = new CustomerImpl(null);
        Exception e= Assertions.assertThrows(IllegalArgumentException.class, () ->{customerList.addCustomer(customer);});
        Assertions.assertEquals("Der Name darf nicht leer sein.", e.getMessage());
    }
    @Test
    public void ownerAlreadyExists(){
        Customer customer = new CustomerImpl("Leo");
        customerList.addCustomer(customer);
        Customer customer1 = new CustomerImpl("Leo");

        Exception e= Assertions.assertThrows(IllegalArgumentException.class, () ->{customerList.addCustomer(customer1);});
        Assertions.assertEquals("Name existiert bereits.", e.getMessage());
    }
    @Test
    public void cargoIsNull() throws LagerVollException, OwnerNotExistException, IllegalArgumentException{
        Cargo cargo= null;
        Assertions.assertThrows(IllegalArgumentException.class, () ->{cargoLager.addCargo(cargo);});
    }


}
