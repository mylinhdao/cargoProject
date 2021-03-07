package routing;


import storageContract.administration.CustomerImpl;
import storageContract.administration.CustomerList;


public class CustomerEinfuegenListener  {
    CustomerList customerList;
    public CustomerEinfuegenListener(CustomerList customerList){
        this.customerList=customerList;
    }
    public void onInputEvent(CustomerEinfuegenEvent eventObject) {
        String name= eventObject.getName();
        try {
            customerList.addCustomer(new CustomerImpl(name));
        }catch (IllegalArgumentException a){

        }
    }
}
