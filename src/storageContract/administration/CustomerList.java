package storageContract.administration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

public class CustomerList {
    private Collection<Customer> customerList= new HashSet<>();
    public void deleteAll(){
        customerList= new HashSet<>();
    }
    /**
     * add a customer
     * @param customer hinzukommende Kunde
     * @throws IllegalArgumentException falls der Name null ist oder schon in der Liste existiert
     */
    public void addCustomer(Customer customer) throws IllegalArgumentException{
        if(customer.getName()==null || customer.getName().trim().equals("")){
            throw new IllegalArgumentException("Der Name darf nicht leer sein.");
        }
        if(getCustomer(customer.getName())!= null){
            throw new IllegalArgumentException("Name existiert bereits.");
        }
        customerList.add(customer);
    }

    /**
     * löscht eine Kunde mit dem Namen name. Falls dieser Kunde nicht existiert, passiert nix.
     * @param name
     */
    public void deleteCustomer(String name){
        for(Customer c: customerList){
            if(c.getName().equals(name)){
                customerList.remove(c);
            }
        }
    }


    /**
     * gibt ein Kunde mit dem Namen zurück
     * @param name
     * @return Kunde mit dem Namen oder null, falls dieser Name nicht in der Liste existiert
     */

    public Customer getCustomer(String name){
        for (Customer c: customerList){
            if(c.getName().equals(name)){
                return c;
            }
        }
        return null;
    }

    /**
     *
     * @return Liste aller Kunden die registiert sind.
     */
    public Collection<Customer> showCustomer(){
        return customerList;
    }

}
