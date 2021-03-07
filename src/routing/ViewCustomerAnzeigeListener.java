package routing;


import ui.ConsoleView;

public class ViewCustomerAnzeigeListener {
    public void onInputEvent(ViewAnzeigenEvent event){
        String text= event.getMessage();
        ConsoleView view= new ConsoleView();
        view.showOutput(text);
    }
}
