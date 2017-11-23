package Data;


/*
 erzeugt ein Feld, welches für die Schiffe genutzt werden. Damit ein Schiff alle felder speichern kann die es belegt.
 */
public class ShipField {


    int x;
    int y;


    public ShipField(int x, int y) {
        this.x = x;
        this.y = y;
    }


    public int getX() {
        return x;
    }


    public int getY() {
        return y;
    }



}
