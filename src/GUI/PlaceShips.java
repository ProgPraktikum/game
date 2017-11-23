package GUI;

import Data.DataContainer;
import Data.Directions;

import javax.swing.*;
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

    public PlaceShips() {


        JDialog setships = new JDialog();
        setships.setModal(true);
        setships.setUndecorated(true);
        setships.setContentPane(Box.createVerticalBox());
        setships.setBackground(Color.BLACK);


        startingPoint = null;

        table = new TableView();


        Iterator<Integer> ships = DataContainer.getShipLenghts().iterator();

       /*
        TextArea zum anzeigen der zu platzierenden schiffe + scrollpane
         */
        scrollPane = new JScrollPane();
        ta = new JTextArea(5, 2);
        scrollPane.getViewport().add(ta);

        /*
		 * Es werden alle ausgewaehlten Schiffstypen zur textArea hinzugefuegt.
		 */
        while (ships.hasNext()) {
            ta.append(ships.next().toString() + "\n");
        }


        JButton weiter = new JButton("weiter");


          /*
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
                    new GameView();
                    setships.dispose();
                }
        );


        Box verticalBox = Box.createVerticalBox();

        verticalBox.add(Box.createHorizontalStrut(5));
        verticalBox.add(table);

        verticalBox.add(scrollPane);


        setships.add(verticalBox);
        setships.add(weiter);
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
            else{
                if(table.getValueAt(row, column) != null){

                    // korrekt gewählter Endpunkt
                    if(table.getValueAt(row, column).equals(3)){

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
                        if(!table.getValueAt(row, column).equals(3)) {
                            hideEndpoints(row, column);
                            startingPoint = null;
                        }
                    }
                }
            }


        }
    }

    /**
     * die Methode Endpoints sucht und setzt mögliche Endpunkte der Schiffe, welche dann auf dem Spielfeld
     * grün dargestellt werden.
     * @param row
     * @param column
     */
    public void endpoints(int row, int column){
        boolean check = true;       //zur pruefung ob setzbar ist
        int length = DataContainer.getShipLenghts().firstElement();// länge des zu setzenden schiffs


        for (Directions directions : Directions.values()) {
            switch(directions){
                case UNTEN:
                    check = true;
         /*
        nach unten pruefen
         */
                    if(DataContainer.getSpielFeldHoehe() >= (row + length)){
                        for(int i = row; i <= row + length -1; i++){
                            if(table.getValueAt(i , column).equals(1))
                                check = false;
                        }
                        if(check == true) table.setValueAt(3,row + length -1 ,column);
                    }
                    break;
                case RECHTS:
                    check = true;
         /*
        Rechts nach möglichen Endpunkten suchen
         */
                    if(DataContainer.getSpielFeldBreite() >= (column + length)){
                        for(int i = column; i <= column+length-1; i++){
                            if(table.getValueAt(row, i).equals(1))
                                check = false;
                        }
                        if (check == true) table.setValueAt(3, row,column+length-1);
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

                case RECHTS:

                    if (x + size <= DataContainer.getSpielFeldBreite()) {
                        if (table.getValueAt(y, x + size - 1).equals(3))
                            table.setValueAt(0, y, x + size - 1);
                    }
                    break;
                case UNTEN:

                    if (y + size <= DataContainer.getSpielFeldHoehe()) {
                        if (table.getValueAt(y + size - 1, x).equals(3))
                            table.setValueAt(0, y + size - 1, x);
                    }
                    break;

                default:
                    break;
            }
        }
    }

}
