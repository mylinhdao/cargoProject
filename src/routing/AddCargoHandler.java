package routing;

import java.util.LinkedList;
import java.util.List;

public class AddCargoHandler {
    List<CargoEinfuegenEventListener> listenerList= new LinkedList<>();
    public void addEventListener(CargoEinfuegenEventListener listener){
        listenerList.add(listener);
    }
    public void removeEventListener(CargoEinfuegenEventListener listener){
        listenerList.remove(listener);
    }
    public void handle(CargoEinfuegenEvent event){
        for(CargoEinfuegenEventListener listener: listenerList){
            listener.onInputEvent(event);
        }
    }
}
