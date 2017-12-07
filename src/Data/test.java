package Data;

public class test {
    public static void main(String args[]){
        game g1 = new game(false);
        ship s = new ship(3);
        DataContainer.setSelectedShip(s);
        System.out.println("orientation"+s.getOrientation());
        g1.rotateShip(1);
        System.out.println("orientation"+s.getOrientation());
        g1.rotateShip(1);System.out.println("orientation"+s.getOrientation());
        g1.rotateShip(2);System.out.println("orientation"+s.getOrientation());
        g1.rotateShip(1);System.out.println("orientation"+s.getOrientation());
        //g1.rotateShip();System.out.println("orientation"+s.getOrientation());
        g1.moveShip(2,0);
        g1.placeShip(DataContainer.getSelectedShip());

    }
}
