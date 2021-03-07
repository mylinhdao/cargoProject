package routing;

import javax.swing.text.View;
import java.util.LinkedList;
import java.util.List;

public class ViewAnzeigeHandler {
    List<ViewCustomerAnzeigeListener> listenerList= new LinkedList<>();
    public void addEventListener(ViewCustomerAnzeigeListener listener){
        listenerList.add(listener);
    }
    public void removeEventListener(ViewCustomerAnzeigeListener listener){
        listenerList.remove(listener);
    }
    public void handle(ViewAnzeigenEvent event){
        for(ViewCustomerAnzeigeListener listener: listenerList){
            listener.onInputEvent(event);
        }
    }
}
