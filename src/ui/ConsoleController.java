package ui;

import routing.*;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.*;

public class ConsoleController {
    private AddCustomerHandler customerEinfuegenHandler;
    private CargoLoeschenHandler cargoLoeschenHandler;
    private AddCargoHandler cargoEinfuegenHandler;
    private CustomerAnzeigenHandler customerAnzeigenHandler;
    Scanner scanner=new Scanner(System.in);
    public ConsoleController(AddCustomerHandler customerEinfuegenHandler,
                             CargoLoeschenHandler cargoLoeschenHandler,
                             AddCargoHandler cargoEinfuegenHandler,
                             CustomerAnzeigenHandler customerAnzeigenHandler){
        this.customerEinfuegenHandler =customerEinfuegenHandler;
        this.cargoLoeschenHandler=cargoLoeschenHandler;
        this.cargoEinfuegenHandler = cargoEinfuegenHandler;
        this.customerAnzeigenHandler=customerAnzeigenHandler;

    }

    /**
     * liest den User Input ein. EventHandler verschickt ein Event mit dem Input .
     * @return von User eingegebene Text
     */
    public void readInput(){
        String unitisedCargo= "UnitisedCargo";
        String liquidBulkCargo= "LiquidBulkCargo";
        String text;
        StringTokenizer tokenizer;
        text = scanner.nextLine();
        //wenn der Input leer ist, passiert nix
        if(text.trim().equals("")|| text==null){
            return;
        }
        //sonst wird der Input analysiert
        tokenizer = new StringTokenizer(text);
        //Kunde einfügen
        if(tokenizer.countTokens()==1){
            if(text.equals("customer")) {
                CustomerAnzeigenEvent event = new CustomerAnzeigenEvent(this);
                customerAnzeigenHandler.handle(event);
                return;
            }
            int position;
            try{
                position=Integer.parseInt(text);
            }catch (Exception e){
                CustomerEinfuegenEvent event2= new CustomerEinfuegenEvent(this, text);
                customerEinfuegenHandler.handle(event2);
                return;
            }
            CargoLoeschenEvent event1= new CargoLoeschenEvent(this, position);
            cargoLoeschenHandler.handle(event1);
            //cargo einfuegen
        }else{
            String[] splitedText= text.split(" ");
            int wortAnzahl= splitedText.length;
            if(wortAnzahl<6){
                return;
            }
            String cargoTyp = splitedText[0];
            if(!(cargoTyp.equals(liquidBulkCargo)|| cargoTyp.equals(unitisedCargo))){
                return;
            }
            String customer= splitedText[1];
            try {
                Integer.parseInt(customer);
                return;
            }catch (Exception e) {
            }
            BigDecimal wert;
            Duration duration;
            try {
                wert= new BigDecimal(splitedText[2]);
                duration = Duration.ofSeconds(Long.parseLong(splitedText[3]));
            }catch (Exception e){
                return;
            }
            String x = splitedText[wortAnzahl - 1];
            int grenzeHarzard= wortAnzahl-2;
            boolean eigenschaft;
            if(x.equals("n")){
                eigenschaft=false;
            }else if(x.equals("y")){
                eigenschaft=true;
            }else {
                return;
            }
            String hazards="";
            for(int i=4; i<=grenzeHarzard; i++){
                hazards+=splitedText[i];
            }
            Collection<String> hazCollection= new HashSet<>();
            String[] hazardsList= hazards.split(",");
            boolean legalHazardsIncluded=true;
            //doppelte Einträge erlaubt
            for(String haz: hazardsList){
                if(!(haz.trim().equals("radioactive")||
                   haz.trim().equals("flammable")||
                   haz.trim().equals("explosive") ||
                   haz.trim().equals("toxic"))){
                    legalHazardsIncluded=false;
                    return;
                }
                hazCollection.add(haz);

            }
            if(legalHazardsIncluded) {
                CargoEinfuegenEvent event = new CargoEinfuegenEvent(this, cargoTyp, customer, wert, duration, hazCollection, eigenschaft);
                cargoEinfuegenHandler.handle(event);
            }


          }

    }


}
