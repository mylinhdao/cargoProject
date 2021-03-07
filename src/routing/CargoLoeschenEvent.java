package routing;

import java.util.EventObject;

public class CargoLoeschenEvent extends EventObject {
    int position;
    public CargoLoeschenEvent(Object o, int position) {
        super(o);
        this.position=position;
    }
    public int getPosition(){
        return position;
    }

}
