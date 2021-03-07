package routing;

import java.util.LinkedList;
import java.util.List;

public class CustomerAnzeigenHandler {
    List<CustomerAnzeigenListener> listenerList= new LinkedList<>();
    public void addEventListener(CustomerAnzeigenListener listener){
        listenerList.add(listener);
    }
    public void removeEventListener(CustomerAnzeigenListener listener){
        listenerList.remove(listener);
    }
    public void handle(CustomerAnzeigenEvent event){
        for(CustomerAnzeigenListener listener: listenerList){
            listener.onInputEvent(event);
        }
    }
}
