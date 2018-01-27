package gui;

import data.Game;
import data.Ship;
import data.DataContainer;
import data.Directions;
import network.Network;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Iterator;
import java.util.Random;


 public class PlaceShips {

    /**
     * Variablen
     */


    private JDialog setships;
    private TableView table;

    private Point startingPoint;
    private JTextArea ta;
     //success gibt an ob die letzte plazierung erfolgreich war um zu verhindern,
    // dass das naechste schiff ausgewaehlt wird bevor das vorherige platziert ist
    private boolean success = true;
    //s ist hilfsvariable um ausgewaehltes schiff zu speichern
    private Ship s=null;

    public PlaceShips() {


        setships = new JDialog();
        setships.setModal(true);
        setships.setUndecorated(true);
        setships.setContentPane(Box.createVerticalBox());


        startingPoint = null;


        /**
         * JMenuBar mit dem Menueeintrag "Beenden".
         */
        JMenuBar bar = new JMenuBar();
        {
            JMenu menu = new JMenu("File");
            {
                JMenuItem item = new JMenuItem("Beenden");
                item.addActionListener(
                        (e) -> {
                            setships.dispose();
                            if(DataContainer.getGameType().equals("mp") || DataContainer.getGameType()
                                    .equals("mps")) {
                                Network.closeClientConnection();
                                Network.closeHostConnection();
                            }
                        }
                );
                menu.add(item);
            }
            bar.add(menu);
        }

        /**
         * erzeugt eine neue Table, welche im spaeteren Verlauf das Spielfeld
         * des Spielers darstellt.
         */
        table = new TableView();
        table.setFont(new Font("Tahoma", Font.BOLD, 30));

        Iterator<Integer> ships = DataContainer.getShipLenghts().iterator();

        /**
         *TextArea zum anzeigen der zu platzierenden schiffe + scrollpane
         */
        JScrollPane scrollPane = new JScrollPane();
        ta = new JTextArea(5, 2);
        DataContainer.setTextArea(ta);
        scrollPane.getViewport().add(ta);

        /**
         * Es werden alle ausgewaehlten Schiffstypen zur textArea hinzugefuegt.
         */
        while (ships.hasNext()) {
            ta.insert("Setze Schiff der Laenge: " + ships.next().toString() + "\n",0);
        }

        /**
         * JLabel fuer Anleitungen wie man ein Scheff zu setzen hat oder es entfernen kann.
         */
        JLabel info = new JLabel();
        info.setText("<html><body>Zum setzen eines Schiffes<br>beliebigen Startpunkt waehlen<br>" +
                "und anschliessend einen der<br>gruenen Punkte anklicken.<br>" +
                " <br>zum Entfernen eines Schiffes,<br>das gewuenschte Objekt mit der  <br>" +
                "rechten Maustaste klicken<br> <br>Fuer eine automatische Platzierung  <br>" +
                "den Button \"zufaellig\" druecken </body></html>");

        JButton weiter = new JButton("weiter");
        weiter.setEnabled(false);


        /**
         * Button, welcher die Funktion aufruft, dass Schiffe automatisch gesetzt werden.
         */
        JButton randomBtn = new JButton("zufaellig");
        randomBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        randomBtn.setFont(new Font("Tahoma", Font.PLAIN, 20));
        randomBtn.addActionListener(
                (e) -> {
                    if (!(DataContainer.getfleet().isEmpty())) {
                        randomLoop();
                    }
                    //randomplace(); // randomplace wird ausgefuehrt bis erfolgreiche platzierung gefunden wurde
                    else {
                        reset();
                        randomLoop();
                    }
                        //randomplace();
                    if (DataContainer.getShipLenghts().size() == 0) {
                        weiter.setEnabled(true); //button wird ernabled
                    }
                }
        );

        /**
         * Button, welcher alle gesetzten Schiffe loescht
         */
        JButton reset = new JButton("zuruecksetzen");
        reset.setAlignmentX(Component.CENTER_ALIGNMENT);
        reset.setFont(new Font("Tahoma", Font.PLAIN, 20));
        reset.addActionListener(
                (e) -> reset()
        );


        /**
         *MouseAdapter wird hinzugefuegt. beim klicken wird TouchedMouse aufgerufen.
         *anschliessend wird geprueft ob die schiffe platziert sind
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
        weiter.setFont(new Font("Tahoma", Font.PLAIN, 20));
        weiter.addActionListener(
                (e) -> {
                    DataContainer.setTable(table);
                    if(DataContainer.getGameType().equals("bdf") ||
                            DataContainer.getGameType().equals("ss")) {

                        //Todo: Aufruf der Ai damit sie die Schiffe platziert
                    }
                    setships.dispose();
                    new GameView();
                }
        );


        Box verticalBox = Box.createVerticalBox();
        Box horzintalBox = Box.createHorizontalBox();
        Box btnBox = Box.createHorizontalBox();

        horzintalBox.add(table);
        horzintalBox.add(info);
        verticalBox.add(Box.createHorizontalStrut(5));
        verticalBox.add(horzintalBox);
        verticalBox.add(scrollPane);

        btnBox.add(randomBtn);
        btnBox.add(Box.createHorizontalStrut(5));
        btnBox.add(reset);

        setships.setJMenuBar(bar);              //JMenuBar wird hinzugefuegt
        setships.add(Box.createVerticalStrut(5));
        setships.add(btnBox);
        setships.add(Box.createVerticalStrut(5));
        setships.add(verticalBox);              //die verticalBox wird hinzugefuegt
        setships.add(Box.createVerticalStrut(5));
        setships.add(weiter);                   //weiter Button hinzugefuegt
        setships.add(Box.createVerticalStrut(5));
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
            wenn noch kein Startpunkt gewaehlt wurde
             */
            if (startingPoint == null) {
                startingPoint = x;
                endpoints(row, column);
            }
            // wenn schon ein punkt als Startpunkt gewaehlt ist
            boolean clickedendpoint;

            if (table.getValueAt(row, column) != null && table.getValueAt(row, column).equals(4)) {

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
                /**ausgewaehltes schiff wird aus stack geholt
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
                        Game.rotateShip(1);
                    } else if (end_x > start_x) { //orrientierung RECHTS (=2)
                        Game.rotateShip(2);
                    } else if (start_y < end_y) { //Orrientierung UNTEN (=3)
                        Game.rotateShip(3);
                    }
                    success=false;
                    if(Game.moveShip(start_x, start_y)){
                        success = Game.placeShip(DataContainer.getSelectedShip());
                    }

                    /**grafische darstellung des schiffs
                     *
                     */
                    if (success) {
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
                    //DataContainer.getShipLenghts().remove(DataContainer.getShipLenghts().firstElement());
                        DataContainer.getShipLenghts().pop();
                        textAreaRemoveLine();
                    }
                }
                if (DataContainer.getShipLenghts().isEmpty()) {
                    return;
                }
                //DataContainer.getShipLenghts().pop();

                /*
                nach dem setzen soll das element entfernt werden und aus
                dem Stack entfernt werden ( der Stack enthaelt alle laengen der
                schiffe) ebenso soll es aus der TextArea entfernt werden.
                */

                startingPoint = null;
            } else {
                /**
                 * falls auf keinen endpunkt geklickt wude sollen die moeglichen wieder
                 * versteckt werden.
                 */
                hideEndpoints(table.rowAtPoint(startingPoint), table.columnAtPoint(startingPoint));
                endpoints(row, column);
                startingPoint = x;//null
            }
        }
        else if(e.getButton() == MouseEvent.BUTTON3){// bei rechte maustaste
            removeShip(row,column);
        }
    }




    /**
     * die Methode Endpoints sucht und setzt moegliche Endpunkte der Schiffe, welche dann auf dem Spielfeld
     * gruen dargestellt werden.
     * @param row
     * @param column
     */
    private void endpoints(int row, int column){
        boolean check;       //zur pruefung ob setzbar ist
        //int length = DataContainer.getShipLenghts().firstElement();// laenge des zu setzenden schiffs
        int length = DataContainer.getShipLenghts().peek();

        for (Directions directions : Directions.values()) {
            switch(directions){

                case LINKS:
                    check = true;
                    if( (column -length + 1) >= 0){
                        for(int i= column;i >=column -length +1;i--){
                            if(table.getValueAt(row,i).equals(3)){
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
                            if(table.getValueAt(i, column).equals(3)){
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
                    if(DataContainer.getGameboardWidth()-1 >=(column + length-1)){
                        for(int i = column; i <=column + length - 1; i++){
                            if(table.getValueAt(row, i).equals(3)){
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
                    if(DataContainer.getGameboardHeight() - 1 >= (row + length -1)){
                        for(int i = row; i <= row + length -1; i++) {
                            if (table.getValueAt(i, column).equals(3)) {
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
    Die Methode hideEndpoints setzt zuvor gesetzte moegliche Endpunkte wieder auf den Wert Wasser
     */
    private void hideEndpoints(int y, int x) {

		/*
		 * In dieser Variable wird die Laenge des letzten Schiffes gespeichert
		 */
        //int size = DataContainer.getShipLenghts().firstElement();
        int size = DataContainer.getShipLenghts().peek();

		/*
		 * Die Schleife laueft die Directions ab
		 */
        for (Directions directions : Directions.values()) {
            switch (directions) {
                case LINKS:
                    if(x - size + 1 >= 0){
                        if(table.getValueAt(y,x- size + 1).equals(4)){
                            table.setValueAt(0,y,x - size + 1);
                        }
                    }
                    break;

                case OBEN:
                    if(y - size + 1 >= 0){
                        if(table.getValueAt(y - size + 1,x).equals(4)){
                            table.setValueAt(0,y - size + 1, x);
                        }
                    }
                    break;

                case RECHTS:
                    if(x + size - 1 <= DataContainer.getGameboardWidth() - 1){
                        if(table.getValueAt(y,x + size - 1).equals(4)){
                            table.setValueAt(0,y,x + size - 1);
                        }
                    }
                    break;

                case UNTEN:
                    if(y + size - 1 <= DataContainer.getGameboardHeight() - 1){
                        if(table.getValueAt(y + size - 1, x).equals(4)){
                            table.setValueAt(0,y + size - 1, x);
                        }
                    }
                    break;

                default:
                    break;
            }
        }
    }

    private void textAreaRemoveLine(){
        int start;
        int end;
        int count;

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

    private boolean randomplace() {
        if (!(DataContainer.getfleet().isEmpty()) && success) {
            DataContainer.setSelectedShip();
            s = DataContainer.getSelectedShip();
        }
        Random rand = new Random();
        success = false;
        int count = 0;
        if(s != null) {
            while (!success && count < DataContainer.getGameboardWidth() * DataContainer.getGameboardHeight()) {
                int randomX = rand.nextInt(DataContainer.getGameboardWidth());
                int randomY = rand.nextInt(DataContainer.getGameboardHeight());
                int startorr = rand.nextInt(4);
                    s.setOrientation(startorr);
                    int i =0;
                    while(i <4 && !success){
                        s.setOrientation((startorr + i)%4);
                        Game.moveShip(randomX,randomY);
                        success= Game.placeShip(s);
                        i++;
                    }
                count++;
            }
        }
        if (success) {
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
            s=null;
            //DataContainer.getShipLenghts().remove(DataContainer.getShipLenghts().firstElement());
            DataContainer.getShipLenghts().pop();
            textAreaRemoveLine();

            if (!(DataContainer.getfleet().isEmpty())) {
                return randomplace();
            }
            return true;
        } else {
            reset();
            return false;
        }
    }




    private void removeShip(int row,int  column){
        if(table.getValueAt(row,column)!=null && table.getValueAt(row, column).equals(3)){
            Ship s=Game.getPlayerboard(column, row).getMaster();
            //DataContainer.getfleet();
            String add= "Setze Schiff der Laenge: "+s.getLength()+"\n";
            ta.insert(add,0);
            switch (s.getOrientation()) {
                case 0:
                    for (int i = s.getXpos(); i >= s.getXpos() - s.getLength() + 1; i--) {
                        table.setValueAt(0, s.getYpos(), i);
                    }
                    break;
                case 1:
                    for (int i = s.getYpos(); i >= s.getYpos() - s.getLength() + 1; i--) {
                        table.setValueAt(0, i, s.getXpos());
                    }
                    break;
                case 2:
                    for (int i = s.getXpos(); i <= s.getXpos() + s.getLength() - 1; i++) {
                        table.setValueAt(0, s.getYpos(), i);
                    }
                    break;
                case 3:
                    for (int i = s.getYpos(); i <= s.getYpos() + s.getLength() - 1; i++) {
                        table.setValueAt(0, i, s.getXpos());
                    }
                    break;
            }
            Game.removeShip(s);
            s.setOrientation(0);
            s.setxpos(0);
            s.setypos(0);
            //DataContainer.getShipLenghts().add(0,s.getLength());
            DataContainer.getShipLenghts().push(s.getLength());
            DataContainer.getfleet().push(s);
        }
    }
    private void reset(){
        int currentlength = 2;
        boolean fieldempty = false;
        int rowempty;
        int columnempty;
        while (!(fieldempty)) {
            columnempty=0;
            for (int i = 0; i < DataContainer.getGameboardHeight(); i++) {
                rowempty=0;
                for (int j = 0; j < DataContainer.getGameboardWidth(); j++) {
                    if (table.getValueAt(i, j).equals(3)) {
                        rowempty =1;
                        if (currentlength == Game.getPlayerboard(j, i).getMaster().getLength()) {
                            removeShip(i, j);
                        }
                    } else {
                        rowempty += 0;
                    }
                }
                //wenn treffer in reihe, dann wird er in spalte uebertragen
                if(rowempty==1){
                    columnempty = 1;
                }
                else{
                    columnempty +=0;
                }
            }
            // wenn kein spaltentreffer, dann ist feld leer -> SCHLEIFENABBRUCH
            if(columnempty==0){
                fieldempty=true;
            }
            //schiffslaenge wird nach komplettem durchlauf durch feld erhoeht
            // (alle schiffe der vorherigen laenge wurden bereits entfernt
            currentlength++;
        }
        success= true;
    }
    public void randomLoop(){
        boolean end= false;
        while(!end){
            end= randomplace();
            if(!end){
                reset();
            }
        }
    }
}
