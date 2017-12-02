package Data;

public class test {
    public static void main(String args[]){
        game g1 = new game(false);
        ship s = new ship(3);
        DataContainer.setSelectedShip(s);
        g1.moveShip(3,4);
        g1.placeShip(DataContainer.getSelectedShip());
        g1.getboard();

    }
}
