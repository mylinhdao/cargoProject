package routing;

import java.util.EventObject;

public class ViewAnzeigenEvent extends EventObject {
    String message;
    public ViewAnzeigenEvent(Object o, String message) {
        super(o);
        this.message=message;
    }
    public String getMessage(){ return message;}
}
