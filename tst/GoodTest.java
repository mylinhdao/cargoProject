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
import java.util.Date;
import java.util.HashSet;

public class GoodTest {
    CustomerList customerList;
    CargoLager cargoLager;
    @BeforeEach
    void setUp(){
        customerList= new CustomerList();
        cargoLager= new CargoLager(customerList, 10);
    }
    // GL Funktionalitäten
    //. 1. Einfügemodus
    @Test
    public void testAddCustomer() {
        Customer customer = new CustomerImpl("Leana");
        customerList.addCustomer(customer);
        Customer customer1 = customerList.getCustomer("Leana");
        Assertions.assertSame(customer, customer1);

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

    //Anzeigemodus
    @Test
    public void showCustomer() {
        Customer customer = new CustomerImpl("Lisa");
        Customer customer2 = new CustomerImpl("Zoe");
        customerList.addCustomer(customer);
        customerList.addCustomer(customer2);
        Collection<Customer> customers = customerList.showCustomer();
        //check if all customer are in list
        boolean lisa=false;
        boolean zoe= false;
        for(Customer c: customers){
            if(c.getName().equals("Lisa")){
                lisa=true;
            }
            if(c.getName().equals("Zoe")){
                zoe=true;
            }
        }
        Assertions.assertTrue(lisa);
        Assertions.assertTrue(zoe);

    }

    @Test
    public void show_Customers_with_number_of_cargo() throws LagerVollException, OwnerNotExistException {
        Customer customer = new CustomerImpl("Lisa");
        Customer customer2 = new CustomerImpl("Zoe");
        customerList.addCustomer(customer);
        customerList.addCustomer(customer2);

        Collection<Hazard> hazards = new ArrayList<>();
        hazards.add(Hazard.explosive);
        Cargo cargo = new MixedLiquidBulkAndUnitisedCargoImpl(customer, new BigDecimal(1200), Duration.ofSeconds(4000), hazards, true, false);
        cargoLager.addCargo(cargo);
        //check number of cargo for all customer
        Assertions.assertEquals(1, cargoLager.cargosOfCustomer(customer).size());
        Assertions.assertEquals(0, cargoLager.cargosOfCustomer(customer2).size());
    }

    @Test
    public void show_UnitisedCargo() throws LagerVollException, OwnerNotExistException {
        Customer customer = new CustomerImpl("Lisa");
        customerList.addCustomer(customer);
        Collection<Hazard> hazards = new ArrayList<>();
        hazards.add(Hazard.explosive);
        hazards.add(Hazard.flammable);
        UnitisedCargo cargo = new UnitisedCargoImpl(customer, new BigDecimal(1200), Duration.ofSeconds(4000), hazards, true);
        UnitisedCargo cargo2 = new UnitisedCargoImpl(customer, new BigDecimal(120), Duration.ofSeconds(8000), hazards, true);
        LiquidBulkCargo cargo3 = new LiquidBulkCargoImpl(customer, new BigDecimal(120), Duration.ofSeconds(8000), hazards, true);

        int storagePosition = cargoLager.addCargo(cargo);
        int storagePosition2 = cargoLager.addCargo(cargo2);
        cargoLager.addCargo(cargo3);
        Collection<Cargo> unitised= cargoLager.showCargos(1);
        Collection<Cargo> liquidbulk= cargoLager.showCargos(2);
        // check number of unitised and liquidbulk to be sure
        Assertions.assertEquals(unitised.size(),2);
        Assertions.assertEquals(liquidbulk.size(),1);
    }
    @Test
    public void includeHazards() throws LagerVollException, OwnerNotExistException {
        Customer customer = new CustomerImpl("Lisa");
        customerList.addCustomer(customer);
        Collection<Hazard> hazards = new ArrayList<>();
        hazards.add(Hazard.explosive);
        hazards.add(Hazard.flammable);
        Cargo cargo = new MixedLiquidBulkAndUnitisedCargoImpl(customer, new BigDecimal(1200), Duration.ofSeconds(4000), hazards, true, false);

        int storagePosition = cargoLager.addCargo(cargo);
        Collection<Hazard> expectInclude= new HashSet<>();
        expectInclude.add(Hazard.explosive);
        expectInclude.add(Hazard.flammable);

        Assertions.assertEquals(expectInclude, cargoLager.getIncludedHazard());


    }
    @Test
    public void excludeHazards() throws LagerVollException, OwnerNotExistException {
        Customer customer = new CustomerImpl("Lisa");
        customerList.addCustomer(customer);
        Collection<Hazard> hazards = new ArrayList<>();
        hazards.add(Hazard.explosive);
        hazards.add(Hazard.flammable);
        Cargo cargo = new MixedLiquidBulkAndUnitisedCargoImpl(customer, new BigDecimal(1200), Duration.ofSeconds(4000), hazards, true, false);

        int storagePosition = cargoLager.addCargo(cargo);
        Assertions.assertTrue(cargoLager.getExcludedHazard().contains(Hazard.radioactive));
        Assertions.assertTrue(cargoLager.getExcludedHazard().contains(Hazard.toxic));
    }
    //Löschmodus
    @Test
    public void delete_Customer(){
        Customer customer = new CustomerImpl("Lisa");
        customerList.addCustomer(customer);

        customerList.deleteCustomer("Lisa");
        Assertions.assertEquals(customerList.getCustomer("Lisa"), null);
    }
    @Test
    public void delete_cargo() throws LagerVollException, OwnerNotExistException {
        Customer customer = new CustomerImpl("Lisa");
        customerList.addCustomer(customer);
        Collection<Hazard> hazards = new ArrayList<>();
        hazards.add(Hazard.explosive);
        hazards.add(Hazard.flammable);
        Cargo cargo = new MixedLiquidBulkAndUnitisedCargoImpl(customer, new BigDecimal(1200), Duration.ofSeconds(4000), hazards, true, false);

        int storagePosition = cargoLager.addCargo(cargo);

        cargoLager.deleteCargo(storagePosition);
        Assertions.assertEquals(cargoLager.getCargo(storagePosition), null);

    }
    //Änderungmodus
    @Test
    public void inspectCargo() throws LagerVollException, OwnerNotExistException {
        Customer customer = new CustomerImpl("Lisa");
        customerList.addCustomer(customer);
        Collection<Hazard> hazards = new ArrayList<>();
        hazards.add(Hazard.explosive);
        hazards.add(Hazard.flammable);
        Cargo cargo = new MixedLiquidBulkAndUnitisedCargoImpl(customer, new BigDecimal(1200), Duration.ofSeconds(4000), hazards, true, false);

        int storagePosition = cargoLager.addCargo(cargo);

        Date now= cargo.getLastInspectionDate();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        cargoLager.inspect(storagePosition);
        Assertions.assertTrue(cargo.getLastInspectionDate().compareTo(now)>0);
    }

}
