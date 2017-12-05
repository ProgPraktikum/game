package GUI;

import Data.ship;
import Data.DataContainer;
import Data.Directions;
import Data.game;
import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.xml.crypto.Data;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Iterator;


public class PlaceShips {

    /**
     * Variablen
     */
    private TableView table;
    private Point startingPoint;
    private JTextArea ta;
    private JScrollPane scrollPane;
    private game g1 = new game(false);
    //success gibt an ob die letzte plazierung erfolgreich war um zu verhindern,
    // dass das nächste schiff ausgewählt wird bevor das vorherige platziert ist
    private boolean success = true;
    //s ist hilfsvariable um ausgewähltes schiff zu speichern
    ship s=null;

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
         *TextArea zum anzeigen der zu platzierenden schiffe + scrollpane
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
         *MouseAdapter wird hinzugefuegt. beim klicken wird TouchedMouse aufgerufen.
         *anschließend wird geprueft ob die schiffe platziert sind
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
            if (startingPoint == null) {
                startingPoint = x;
                endpoints(row, column);
            }
            // wenn schon ein punkt als Startpunkt gewählt ist
            boolean clickedendpoint;

            if (table.getValueAt(row, column) != null && table.getValueAt(row, column).equals(3)) {

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
                hideEndpoints(start_y, start_x);
                /**ausgewähltes schiff wird aus stack geholt
                 * und in selectedship zwischengespeichert
                 */

                if (!(DataContainer.getfleet().isEmpty()) && success) {
                    s = null;
                    DataContainer.setSelectedShip();
                    s = DataContainer.getSelectedShip();
                }
                if (s != null) {
                    s.setOrientation(0);
                    if (start_y > end_y) { //orrientierung OBEN(=1)
                        g1.rotateShip();
                    } else if (end_x > start_x) { //orrientierung RECHTS (=2)
                        g1.rotateShip();
                        g1.rotateShip();
                    } else if (start_y < end_y) { //Orrientierung UNTEN (=3)
                        g1.rotateShip();
                        g1.rotateShip();
                        g1.rotateShip();
                    }
                    success=false;
                    if(g1.moveShip(start_x, start_y)){
                        success = g1.placeShip(DataContainer.getSelectedShip());
                    }

                    /**grafische darstellung des schiffs
                     *
                     */
                    if (success) {
                        //Todo do something about nullpointerexeption
                        switch (s.getOrientation()) {
                            case 0:
                                for (int i = s.getXpos(); i >= s.getXpos() - s.getLength() + 1; i--) {
                                    table.setValueAt(3, s.getYpos(), i);
                                }
                                break;
                            case 1:
                                for (int i = s.getYpos(); i >= s.getYpos() - s.getLength() + 1; i--) {
                                    table.setValueAt(3, i, s.getXpos());
                                }
                                break;
                            case 2:
                                for (int i = s.getXpos(); i <= s.getXpos() + s.getLength() - 1; i++) {
                                    table.setValueAt(3, s.getYpos(), i);
                                }
                                break;
                            case 3:
                                for (int i = s.getYpos(); i <= s.getYpos() + s.getLength() - 1; i++) {
                                    table.setValueAt(3, i, s.getXpos());
                                }
                                break;
                        }
                    DataContainer.getShipLenghts().remove(DataContainer.getShipLenghts().firstElement());
                        textAreaRemoveLine();
                    }
                }
                g1.getboard();
                if (DataContainer.getShipLenghts().isEmpty()) {
                    return;
                }
                //DataContainer.getShipLenghts().pop();

                /*
                nach dem setzen soll das element entfernt werden und aus
                dem Stack entfernt werden ( der Stack enthält alle längen der
                schiffe) ebenso soll es aus der TextArea entfernt werden.
                */

                startingPoint = null;
            } else {
                /**
                 * falls auf keinen endpunkt geklickt wude sollen die möglichen wieder
                 * versteckt werden. TODO zetzt nur zurück wenn startpunkt wieder gedrückt wird ?!
                 */
                hideEndpoints(table.rowAtPoint(startingPoint), table.columnAtPoint(startingPoint));
                endpoints(row, column);
                startingPoint = x;//null
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
        boolean check;       //zur pruefung ob setzbar ist
        int length = DataContainer.getShipLenghts().firstElement();// länge des zu setzenden schiffs

        for (Directions directions : Directions.values()) {
            switch(directions){

                case LINKS:
                    check = true;
                    if( (column -length + 1) >= 0){
                        for(int i= column;i >=column -length +1;i--){
                            if(table.getValueAt(row,i).equals(1)){
                                check=false;
                            }
                        }
                        if(check){
                            table.setValueAt(4, row,column-length+1);
                        }
                    }
                    break;

                case OBEN:
                    check = true;
                    if ( row - length + 1 >= 0){
                        for(int i =row; i>= row - length +1;i--){
                            if(table.getValueAt(i, column).equals(1)){
                                check = false;
                            }
                        }
                        if(check){
                            table.setValueAt(4,row - length +1,column);
                        }
                    }
                    break;

                case RECHTS:
                    check=true;
                    if(DataContainer.getSpielFeldBreite()-1 >=(column + length-1)){
                        for(int i = column; i <=column + length - 1; i++){
                            if(table.getValueAt(row, i).equals(1)){
                                check= false;
                            }
                        }
                        if(check){
                            table.setValueAt(4,row,column+length-1);
                        }
                    }
                    break;

                case UNTEN:
                    check=true;
                    if(DataContainer.getSpielFeldHoehe() - 1 >= (row + length -1)){
                        for(int i = row; i <= row + length -1; i++) {
                            if (table.getValueAt(i, column).equals(1)) {
                                check = false;
                            }
                        }
                        if(check){
                            table.setValueAt(4,row + length -1 ,column);
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
                    if(x - size + 1 >= 0){
                        if(table.getValueAt(y,x- size + 1).equals(3)){
                            table.setValueAt(0,y,x - size + 1);
                        }
                    }
                    break;

                case OBEN:
                    if(y - size + 1 >= 0){
                        if(table.getValueAt(y - size + 1,x).equals(3)){
                            table.setValueAt(0,y - size + 1, x);
                        }
                    }
                    break;

                case RECHTS:
                    if(x + size - 1 <= DataContainer.getSpielFeldBreite() - 1){
                        if(table.getValueAt(y,x + size - 1).equals(3)){
                            table.setValueAt(0,y,x + size - 1);
                        }
                    }
                    break;

                case UNTEN:
                    if(y + size - 1 <= DataContainer.getSpielFeldHoehe() - 1){
                        if(table.getValueAt(y + size - 1, x).equals(3)){
                            table.setValueAt(0,y + size - 1, x);
                        }
                    }
                    break;

                default:
                    break;
            }
        }
    }

    public void textAreaRemoveLine(){
        int start;
        int end;
        int count = -1;

        try{
            count = ta.getLineCount();
            if(count > 0){
                start = ta.getLineStartOffset(0);
                end = ta.getLineEndOffset(0);

                ta.replaceRange(null, start,end);
            }
        }catch(BadLocationException e){
            e.printStackTrace();
        }
    }

}
