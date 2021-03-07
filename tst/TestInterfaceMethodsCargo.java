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

public class TestInterfaceMethodsCargo {
    CustomerList customerList;
    CargoLager cargoLager;
    @BeforeEach
    public void setUp(){
        customerList= new CustomerList();
        cargoLager= new CargoLager(customerList, 10);
    }
    @Test
    public void add_Cargo() throws LagerVollException, OwnerNotExistException {
        Customer customer = new CustomerImpl("Lisa");
        customerList.addCustomer(customer);
        Collection<Hazard> hazards = new ArrayList<>();
        hazards.add(Hazard.explosive);
        hazards.add(Hazard.flammable);
        Cargo cargo = new LiquidBulkCargoImpl(customer, new BigDecimal(1200), Duration.ofSeconds(4000), hazards, true);
        int storagePosition = cargoLager.addCargo(cargo);

        Assertions.assertEquals(cargoLager.getCargo(storagePosition).getOwner(), customer);
    }
    @Test
    public void get_Owner_Cargo() throws LagerVollException, OwnerNotExistException {
        Customer customer = new CustomerImpl("Lisa");
        customerList.addCustomer(customer);
        Collection<Hazard> hazards = new ArrayList<>();
        hazards.add(Hazard.explosive);
        hazards.add(Hazard.flammable);
        Cargo cargo = new LiquidBulkCargoImpl(customer, new BigDecimal(1200), Duration.ofSeconds(4000), hazards, true);
        int storagePosition = cargoLager.addCargo(cargo);

        Assertions.assertEquals(cargoLager.getCargo(storagePosition).getOwner(), customer);
    }
    @Test
    public void getValue_Cargo() throws LagerVollException, OwnerNotExistException {
        Customer customer = new CustomerImpl("Lisa");
        customerList.addCustomer(customer);
        Collection<Hazard> hazards = new ArrayList<>();
        hazards.add(Hazard.explosive);
        hazards.add(Hazard.flammable);
        Cargo cargo = new LiquidBulkCargoImpl(customer, new BigDecimal(1200), Duration.ofSeconds(4000), hazards, true);
        int storagePosition = cargoLager.addCargo(cargo);

        Assertions.assertEquals(cargoLager.getCargo(storagePosition).getValue(), new BigDecimal(1200) );
    }
    @Test
    public void getDuration_Cargo() throws LagerVollException, OwnerNotExistException {
        Customer customer = new CustomerImpl("Lisa");
        customerList.addCustomer(customer);
        Collection<Hazard> hazards = new ArrayList<>();
        hazards.add(Hazard.explosive);
        hazards.add(Hazard.flammable);
        Cargo cargo = new LiquidBulkCargoImpl(customer, new BigDecimal(1200), Duration.ofSeconds(4000), hazards, true);
        int storagePosition = cargoLager.addCargo(cargo);

        Assertions.assertEquals(cargoLager.getCargo(storagePosition).getDurationOfStorage(), Duration.ofSeconds(4000));
    }
    @Test
    public void getHazards_Cargo() throws LagerVollException, OwnerNotExistException {
        Customer customer = new CustomerImpl("Lisa");
        customerList.addCustomer(customer);
        Collection<Hazard> hazards = new ArrayList<>();
        hazards.add(Hazard.explosive);
        Cargo cargo = new LiquidBulkCargoImpl(customer, new BigDecimal(1200), Duration.ofSeconds(4000), hazards, true);
        int storagePosition = cargoLager.addCargo(cargo);

        Assertions.assertEquals(cargoLager.getCargo(storagePosition).getHazards(), Collections.singletonList(Hazard.explosive));
    }
    @Test
    public void isPressurized_Cargo() throws LagerVollException, OwnerNotExistException {
        Customer customer = new CustomerImpl("Lisa");
        customerList.addCustomer(customer);
        Collection<Hazard> hazards = new ArrayList<>();
        hazards.add(Hazard.explosive);
        hazards.add(Hazard.flammable);
        LiquidBulkCargo cargo = new LiquidBulkCargoImpl(customer, new BigDecimal(1200), Duration.ofSeconds(4000), hazards, true);
        int storagePosition = cargoLager.addCargo(cargo);

        Assertions.assertEquals(cargo.isPressurized(), true);
    }
    @Test
    public void isFragile_Cargo() throws LagerVollException, OwnerNotExistException {
        Customer customer = new CustomerImpl("Lisa");
        customerList.addCustomer(customer);
        Collection<Hazard> hazards = new ArrayList<>();
        hazards.add(Hazard.explosive);
        hazards.add(Hazard.flammable);
        UnitisedCargo cargo = new UnitisedCargoImpl(customer, new BigDecimal(1200), Duration.ofSeconds(4000), hazards, true);
        int storagePosition = cargoLager.addCargo(cargo);

        Assertions.assertEquals(cargo.isFragile(), true);
    }
    @Test
    public void isPressurized_isFragile() throws LagerVollException, OwnerNotExistException {
        Customer customer = new CustomerImpl("Lisa");
        customerList.addCustomer(customer);
        Collection<Hazard> hazards = new ArrayList<>();
        hazards.add(Hazard.explosive);
        hazards.add(Hazard.flammable);
        MixedLiquidBulkAndUnitisedCargo cargo = new MixedLiquidBulkAndUnitisedCargoImpl(customer, new BigDecimal(1200), Duration.ofSeconds(4000), hazards, true, true);
        int storagePosition = cargoLager.addCargo(cargo);
        //check if cargo is fragile and pressurized.
        Assertions.assertEquals(cargo.isPressurized(), true);
        Assertions.assertEquals(cargo.isFragile(), true);
    }
}
