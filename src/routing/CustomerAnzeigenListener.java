package routing;

import storageContract.administration.Customer;
import storageContract.administration.CustomerList;
import storageContract.cargo.CargoLager;

import java.util.Collection;

public class CustomerAnzeigenListener {
    CargoLager lager; // Lager, in dem alle Cargo gespeichert sind
    public CustomerAnzeigenListener(CargoLager lager){
        this.lager=lager;
    }
    /**
     * Diese EventListener ist auch ein EventSource. Es schickt Event zum ConsoleView
     */
    ViewAnzeigeHandler handler;
    public void setHandler(ViewAnzeigeHandler handler){
        this.handler=handler;
    }
    public void onInputEvent(CustomerAnzeigenEvent event){
        String output= "";
        int val;
        Collection<Customer> customers= lager.getCustomerList().showCustomer();
        for(Customer c: customers){
            val=lager.cargosOfCustomer(c).size();
            output+=c.getName()+" "+val+"\n";
        }
        output=output.substring(0,output.length()-1);
        ViewAnzeigenEvent event1= new ViewAnzeigenEvent(this, output);
        handler.handle(event1);

    }
}
