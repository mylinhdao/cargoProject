package routing;

import java.util.EventObject;
import java.util.LinkedList;
import java.util.List;

public class AddCustomerHandler {
    List<CustomerEinfuegenListener> listenerList= new LinkedList<>();
    public void addEventListener(CustomerEinfuegenListener listener){
        listenerList.add(listener);
    }
    public void removeEventListener(CustomerEinfuegenListener listener){
        listenerList.remove(listener);
    }
    public void handle(CustomerEinfuegenEvent event){
        for(CustomerEinfuegenListener listener: listenerList){
            listener.onInputEvent(event);
        }
    }
}
