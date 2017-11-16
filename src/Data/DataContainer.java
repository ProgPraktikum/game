package Data;

/**
 * Diese Klasse beinhaltet sämtliche Werte, wie z.B. Spielfedgroesse. Ebenso enthaelt diese
 * Klasse Getter und Setter Methoden für saemtliche Werte.
 *
 *
 * @author Christopher Kisch, Jan Riedel, Felix Graeber
 *
 */
public class DataContainer {

    private int spielFeldBreite = 10;
    private int spielFeldHoehe = 10;
    private ship fleet[];
    private ship selectedShip;
    private int maxFleetsize=30;
    private static int counter=0; //counter für arrayindex


    /*
    Gibt die Spielfeldbreite zurueck
     */
    public int getSpielFeldBreite(){
        return  spielFeldBreite;
    }

    /*
    setzt die Spielfeldbreite
     */
    public void setSpielFeldBreite(int n){
        spielFeldBreite = n;
    }

    /*
    gibt die Spielfeldhoehe zurueck
     */
    public int getSpielFeldHoehe(){
        return spielFeldHoehe;
    }

    /*
    setzt die Spielfeldhoehe
     */
    public void setSpielFeldHoehe(int n){
        spielFeldHoehe = n;
    }

    public void addFleet(ship s){
        fleet[counter]= s;
        counter++;
    }
    public ship getFleet(int i){
        return fleet[i];
    }
    public ship removeShip(int i){ //overhaul with list... need sleep
        ship s= fleet[i];
        return s;
    }
    public void selectship(int i){
            selectedShip= fleet[i];
    }
    public int getMaxFleetsize(){
        return maxFleetsize;
    }
    public void calcMaxFleetsize(){
        maxFleetsize= (spielFeldHoehe*spielFeldBreite)/30 *100;
    }
}
