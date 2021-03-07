package routing;

import java.util.EventObject;
import java.util.List;

public class CustomerEinfuegenEvent extends EventObject {
    String name;
    public CustomerEinfuegenEvent(Object o, String name) {
        super(o);
        this.name=name;
    }
    public String getName(){ return name;}
}
