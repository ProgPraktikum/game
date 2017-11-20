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
    private int currFleetsize=0;
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

    /*
    fügt schiff zu fleet hinzu
     */
    public void addFleet(ship s){
        fleet[counter]= s;
        counter++;
    }

    /*
    gibt schiff an stelle i in fleet aus
     */
    public ship getFleet(int i){
        return fleet[i];
    }

    /*
    entfernt schiff aus fleet
     */
    public ship removeShip(int i){
        ship s= fleet[i];
        fleet[i]=null;
        return s;
    }
    /*wählt schiff an stelle i in fleet aus und speichjert es in selectedship

     */
    public void selectship(int i){
            selectedShip= fleet[i];
    }
    /*gibt maxfleetsize aus

     */
    public int getMaxFleetsize(){
        return maxFleetsize;
    }
    /*
    gibt aktuelle flottengröße aus
     */
    public int getCurrFleetsize(){ return currFleetsize;}
    //erhöht currFleetsize um  int l
    public void addCurrFleetsize(int l){
        currFleetsize += l;
    }

    /*
    berechnet maximale flottengröße
     */
        public void calcMaxFleetsize(){
        maxFleetsize= (spielFeldHoehe*spielFeldBreite)/30 *100;
    }
}
