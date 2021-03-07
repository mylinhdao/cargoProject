package routing;

import java.util.LinkedList;
import java.util.List;

public class CargoLoeschenHandler {
    List<CargoLoeschenEventListener> listenerList= new LinkedList<>();
    public void addEventListener(CargoLoeschenEventListener listener){
        listenerList.add(listener);
    }
    public void removeEventListener(CargoLoeschenEventListener listener){
        listenerList.remove(listener);
    }
    public void handle(CargoLoeschenEvent event){
        for(CargoLoeschenEventListener listener: listenerList){
            listener.onInputEvent(event);
        }
    }
}
