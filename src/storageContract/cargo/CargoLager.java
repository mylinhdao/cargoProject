package storageContract.cargo;

import api.Beobachter;
import api.Subjekt;
import storageContract.administration.Customer;
import storageContract.administration.CustomerList;

import java.util.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CargoLager implements Subjekt {
    private List<Beobachter> beobachters= new LinkedList<>();

    int capacity;
    private int anzahl=0;
    private CustomerList customerList;
    private  Cargo[] lager;
    private Date[] storageDate;
    private Date[] inspectionDate;

    Collection<Hazard> hazards=new HashSet<>();

    public CargoLager(CustomerList customerList, int capacity){
        this.customerList=customerList;
        this.capacity=capacity;
        lager=new Cargo[capacity];
        storageDate= new Date[capacity];
        inspectionDate= new Date[capacity];

    }
    public synchronized boolean isFull(){
        return anzahl==capacity;
    }
    public CustomerList getCustomerList(){
        return customerList;
    }
    public int getCapacity(){ return capacity;}
    /**
     * löscht alle Cargos im Lager
     */
    public synchronized void deleteStorage(){
        lager = new Cargo[capacity];
        storageDate= new Date[capacity];
        inspectionDate= new Date[capacity];
    }
    /**
     * fügt ein Cargo in den Lager hinzu und gibt seine Lagerposition zurück.
     * dabei wird geprüft ob der Cargo einem existierenden Kunden gehört.
     * @param cargo hinzuzufügende Cargo
     * @return Position der eingelagerte Cargo
     * @throws OwnerNotExistException falls der Besitzer nicht registiert ist.
     * @throws LagerVollException falls der Lager voll ist
     */
    public synchronized int addCargo(Cargo cargo) throws OwnerNotExistException, LagerVollException, IllegalArgumentException{
        //more

        //end
        if(cargo==null || cargo.getOwner()== null){
            throw new IllegalArgumentException("Cargo or Customer cannot be null");
        }
        String customerName= cargo.getOwner().getName();
        if(customerList.getCustomer(customerName)!=null){
            if(this.isFull()){ throw new LagerVollException();}
            for(int j = 0; j< lager.length; j++){
                if(lager[j]==null){
                    lager[j]=cargo;
                    storageDate[j]=new Date();
                    inspectionDate[j]= new Date();
                    hazards.addAll(cargo.getHazards());
                    anzahl++;
                    this.benachrichtige();
                    return j;
                }
            }
        }


        throw new OwnerNotExistException();

    }

    /**
     * löscht einen Cargo. Falls die angegebende Lagerplatz bereits leer ist, passiert nix
     * @param position Position der zu löschende Cargo
     */
    public synchronized void deleteCargo(int position){
        if(position>=capacity || position<0){
            return;
        }
        lager[position]=null;
        storageDate[position]=null;
        inspectionDate[position]=null;
        hazards= new HashSet<>();
        for(Cargo c: lager){
            if(c!=null) {
                hazards.addAll(c.getHazards());
            }
        }
        anzahl--;
        this.benachrichtige();
    }

    /**
     * @param type 1: UnitisedCargos; 2: LiquidBulkCargo; 3: Mixed; else: return null
     * @return die Liste der Positionen aller eingelagerte Cargos
     */
    public synchronized Collection<Cargo> showCargos(int type){
        Collection<Cargo> liste= new ArrayList<>();
        Class typeClass;
        switch (type){
            case 1: typeClass=UnitisedCargoImpl.class; break;
            case 2: typeClass=LiquidBulkCargoImpl.class; break;
            case 3: typeClass= MixedLiquidBulkAndUnitisedCargoImpl.class; break;
            default: return null;

        }

        for(int i = 0; i< lager.length; i++){
            if(lager[i]!=null && lager[i].getClass()==typeClass){
                liste.add(this.getCargo(i));

            }
        }
        return liste;
    }

    /**
     *
     * @param position
     * @return Einlagerungsdatum der Cargo auf dem Position
     */
    public synchronized Date getStorageDate(Integer position){
        return storageDate[position];
    }

    /**
     *
     * @param position
     * @return letzte Inspektionsdatum der Cargo auf dem Position
     */
    public synchronized Date getInspectionDate(Integer position){
        return inspectionDate[position];
    }

    /**
     *
     * @param position
     * @return den Cargo auf dem Position
     */
    public synchronized Cargo getCargo(Integer position){
        return lager[position];
    }

    /**
     *
     * @return liste aller beinhaltende Gefahrstoffe
     */
    public synchronized Collection<Hazard> getIncludedHazard(){

        return hazards;
    }

    /**
     *
     * @return Liste aller nicht beinhaltende Gefahrstoffe
     */
    public synchronized Collection<Hazard> getExcludedHazard(){
        Collection<Hazard> exHazards= new HashSet<>();
        exHazards.add(Hazard.toxic);
        exHazards.add(Hazard.flammable);
        exHazards.add(Hazard.radioactive);
        exHazards.add(Hazard.explosive);
        exHazards.removeAll(this.hazards);
        return exHazards;
    }

    /**
     * Cargo auf dem Position wird inspektiert
     * @param position
     */
    public synchronized void inspect(int position){
        if(lager[position]!=null) {
            inspectionDate[position]=new Date();
        }
    }

    /**
     *
     * @param cargo
     * @return Position des Cargos
     */
    public synchronized int getPositionCargo(Cargo cargo){
        for(int i = 0; i< lager.length; i++){
            if(lager[i]==cargo){
                return i;
            }
        }
        return 0;
    }

    /**
     *
     * @param customer
     * @return Liste aller Cargo die diesem Customer gehört
     */
    public synchronized Collection<Cargo> cargosOfCustomer(Customer customer){
        Collection<Cargo> cargoLIST=new ArrayList<>();
        for(int i = 0; i< lager.length; i++){
            if(lager[i]!=null&& lager[i].getOwner().getName().equals(customer.getName())){
                cargoLIST.add(lager[i]);
            }

        }
        return cargoLIST;
    }
    public synchronized int getNumberOfCargo(){
        return anzahl;
    }

    @Override
    public void meldeAn(Beobachter beobachter) {
        beobachters.add(beobachter);
    }

    @Override
    public void meldeAb(Beobachter beobachter) {
        beobachters.remove(beobachter);
    }

    @Override
    public synchronized void benachrichtige() {
        for(Beobachter b: beobachters){
            b.update();
        }

    }
}
