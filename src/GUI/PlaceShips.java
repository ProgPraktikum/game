package GUI;

import Data.ship;
import Data.DataContainer;
import Data.Directions;
import Data.game;
import javax.swing.*;
import javax.xml.crypto.Data;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Iterator;


public class PlaceShips {

    /*
          Variablen
       */
    private TableView table;
    private Point startingPoint;
    private JTextArea ta;
    private JScrollPane scrollPane;
    private game g1 = new game(false);

    public PlaceShips() {


        JDialog setships = new JDialog();
        setships.setModal(true);
        setships.setUndecorated(true);
        setships.setContentPane(Box.createVerticalBox());
        setships.setBackground(Color.BLACK);


        startingPoint = null;


        /**
         * JMenuBar mit dem Menüeintrag "Beenden".
         */
        JMenuBar bar = new JMenuBar();
        {
            JMenu menu = new JMenu("File");
            {
                JMenuItem item = new JMenuItem("Beenden");
                item.addActionListener(
                        (e) -> {
                            setships.dispose();
                        }
                );
                menu.add(item);
            }
            bar.add(menu);
        }

        /**
         * erzeugt eine neue Table, welche im späteren Verlauf das Spielfeld
         * des Spielers darstellt.
         */
        table = new TableView();


        Iterator<Integer> ships = DataContainer.getShipLenghts().iterator();

       /**
        TextArea zum anzeigen der zu platzierenden schiffe + scrollpane
         */
        scrollPane = new JScrollPane();
        ta = new JTextArea(5, 2);
        scrollPane.getViewport().add(ta);

        /**
		 * Es werden alle ausgewaehlten Schiffstypen zur textArea hinzugefuegt.
		 */
        while (ships.hasNext()) {
            ta.append(ships.next().toString() + "\n");
        }


        JButton weiter = new JButton("weiter");
        weiter.setEnabled(false);


          /**
          MouseAdapter wird hinzugefuegt. beim klicken wird TouchedMouse aufgerufen.
          anschließend wird geprueft ob die schiffe platziert sind
           */
        table.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent event) {
                TouchedMouse(event);
            }

            public void mouseReleased(MouseEvent event) {
                if (DataContainer.getShipLenghts().size() == 0) {
                    weiter.setEnabled(true);
                } else {
                    weiter.setEnabled(false);
                }
            }
        });


        weiter.setAlignmentX(Component.CENTER_ALIGNMENT);
        weiter.setForeground(Color.WHITE);
        weiter.setBackground(Color.BLACK);
        weiter.setFont(new Font("Tahoma", Font.PLAIN, 20));
        weiter.addActionListener(
                (e) -> {
                    DataContainer.setTable(table);

                    if (DataContainer.getGameType().equals("bdf") ||
                            DataContainer.getGameType().equals("ss")) {

                        //Todo: Aufruf der AI damit sie die Schiffe platziert
                    }
                    setships.dispose();
                    new GameView();
                }
        );





        Box verticalBox = Box.createVerticalBox();

        verticalBox.add(Box.createHorizontalStrut(5));
        verticalBox.add(table);

        verticalBox.add(scrollPane);


        setships.setJMenuBar(bar);              //JMenuBar wird hinzugefuegt
        setships.add(verticalBox);              //die verticalBox wird hinzugefuegt
        setships.add(weiter);                   //weiter Button hinzugefuegt
        setships.pack();
        setships.setLocationRelativeTo(null);
        setships.setVisible(true);


    }

    private void TouchedMouse(MouseEvent e) {
        /*
        speichert die angeklickte Zelle der Table
         */
        Point x = e.getPoint();
        int column = table.columnAtPoint(x);
        int row = table.rowAtPoint(x);

        if (e.getButton() == MouseEvent.BUTTON1) {  //Linke Maustaste

            if (DataContainer.getShipLenghts().isEmpty()) {
                return;
            }
            /*
            wenn noch kein Startpunkt gewählt wurde
             */
            if(startingPoint == null){
                startingPoint = x;
                endpoints(row, column);
            }
            // wenn schon ein punkt als Startpunkt gewählt ist
            boolean clickedendpoint;
            //if()

            //else if(){

                if(table.getValueAt(row, column) != null && table.getValueAt(row, column).equals(3)){

                        /*
                        wenn zuvor durch endpoints() eine 3 gesetzt wurde
                        wird hier der x und y wert gesetzt als beginn
                         */
                        int start_y = table.rowAtPoint(startingPoint);
                        int start_x = table.columnAtPoint(startingPoint);

                        /*
                        y und x Endwert
                         */
                        int end_y = table.rowAtPoint(x);
                        int end_x = table.columnAtPoint(x);

                        /**ausgewähltes schiff wird aus stack geholt
                        * und in selectedship zwischengespeichert
                         */
                        if(!(DataContainer.getfleet().isEmpty()) ) {
                            DataContainer.setSelectedShip();
                            ship s = DataContainer.getSelectedShip();
                            if (start_y != end_y) {
                                g1.rotateShip();
                            }
                            g1.moveShip(start_x, start_y);
                            boolean success = g1.placeShip(DataContainer.getSelectedShip());

                            /**grafische darstellung des schiffs
                             *
                             */
                            if (success) {
                                if (s.getOrientation() == 0) {
                                    for (int i = s.getXpos(); i >= s.getXpos() - s.getLength() + 1; i--) {
                                        table.setValueAt(1, s.getYpos(), i);
                                    }
                                } else if (s.getOrientation() == 1) {
                                    for (int i = s.getYpos(); i >= s.getYpos() - s.getLength() + 1; i--) {
                                        table.setValueAt(1, i, s.getXpos());
                                    }
                                }
                                g1.getboard();
                            }
                        }

                    /*
                    nach dem setzen soll das element entfernt werden und aus
                    dem Stack entfernt werden ( der Stack enthält alle längen der
                    schiffe) ebenso soll es aus der TextArea entfernt werden.
                     */
                        //DataContainer.getShipLenghts().pop();
                        //Todo aus TextArea entfernen
                     /*
					 * Todo Das gesetzte Schiff soll aus der JTextArea entfernt.
					 */


                     /*
                     wenn keine Schiffe mehr zu setzen sind wird hier abgebrochen
                      */
                        if (DataContainer.getShipLenghts().isEmpty()) {
                            return;
                        }
                        startingPoint = null;
                    }else{
                        /**
                         * falls auf keinen endpunkt geklickt wude sollen die möglichen wieder
                         * versteckt werden. TODO zetzt nur zurück wenn startpunkt wieder gedrückt wird ?!
                         */
                        hideEndpoints(table.rowAtPoint(startingPoint),
                                table.columnAtPoint(startingPoint)); {
                            endpoints(row, column);
                            startingPoint = x;
                        }
                    }
                }
            //}


        }


    /**
     * die Methode Endpoints sucht und setzt mögliche Endpunkte der Schiffe, welche dann auf dem Spielfeld
     * grün dargestellt werden.
     * @param row
     * @param column
     */
    public void endpoints(int row, int column){
        boolean check;       //zur pruefung ob setzbar ist
        int length = DataContainer.getShipLenghts().firstElement();// länge des zu setzenden schiffs


        for (Directions directions : Directions.values()) {
            switch(directions){
                case OBEN:
                    check = true;
         /*
        nach unten pruefen
         */

                    /*if(DataContainer.getSpielFeldHoehe() >= (row + length)){
                        for(int i = row; i <= row + length -1; i++){
                            if(table.getValueAt(i , column).equals(1))
                                check = false;
                        }
                        if(check == true) table.setValueAt(3,row + length -1 ,column);
                        break;
                    }*/
                    if ( (row -length+1) >= 0){
                        for(int i =row; i<= row - length +1;i--){
                            if(table.getValueAt(i, column).equals(1)){
                                check = false;
                            }
                        }
                        if(check){
                            table.setValueAt(3,row - length +1,column);
                        }
                    }
                        break;
                case LINKS:
                    check = true;
         /*
        Rechts nach möglichen Endpunkten suchen
         */
                    /*if(DataContainer.getSpielFeldBreite() >= (column + length)){
                        for(int i = column; i <= column+length-1; i++){
                            if(table.getValueAt(row, i).equals(1))
                                check = false;
                        }
                        if (check == true) table.setValueAt(3, row,column+length-1);
                    }
                    break;*/
                    if( (column -length + 1) >= 0){
                        for(int i= column;i <=column -length +1;i--){
                            if(table.getValueAt(row,i).equals(1)){
                                check=false;
                            }
                        }
                        if(check){
                            table.setValueAt(3, row,column-length+1);
                        }
                    }
                    break;
                default:
                    break;
            }
        }
    }


    /**
    Die Methode hideEndpoints setzt zuvor gesetzte mögliche Endpunkte wieder auf den Wert Wasser
     */
    public void hideEndpoints(int y, int x) {

		/*
		 * In dieser Variable wird die Laenge des letzten Schiffes gespeichert
		 */
        int size = DataContainer.getShipLenghts().firstElement();

		/*
		 * Die Schleife laueft die Directions ab
		 */
        for (Directions directions : Directions.values()) {
            switch (directions) {

                case LINKS:

                    /*if (x + size <= DataContainer.getSpielFeldBreite()) {
                        if (table.getValueAt(y, x + size - 1).equals(3))
                            table.setValueAt(0, y, x + size - 1);
                    }
                    break;*/
                    if(x -size+1 >= 0){
                        if(table.getValueAt(y,x- size + 1).equals(3)){
                            table.setValueAt(0,y,x - size + 1);
                        }
                    }
                    break;
                case OBEN:

                    /*if (y + size <= DataContainer.getSpielFeldHoehe()) {
                        if (table.getValueAt(y + size - 1, x).equals(3))
                            table.setValueAt(0, y + size - 1, x);
                    }
                    break;*/
                    if(y - size+1 >= 0){
                        if(table.getValueAt(y - size + 1,x).equals(3)){
                            table.setValueAt(0,y - size +1, x);
                        }
                    }
                    break;

                default:
                    break;
            }
        }
    }

}
