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
}
