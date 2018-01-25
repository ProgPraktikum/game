package gui;

import ai.Ai;

import com.sun.xml.internal.ws.util.CompletedFuture;
import data.DataContainer;
import network.Network;
import data.Game;
import javax.swing.*;
import java.awt.*;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * Diese Klasse bildet das eigentliche Spielfenster.
 * Es enthaelt eine MenuBar um Spiele zu speichern/laden.
 * Es enthaelt zwei Spielfelder, wobei das linke das des Spielers ist
 * und das rechte ist das um Schuesse abzufeuern.
 */
 public class GameView {

    private TableView tablePlayer;
    private TableView PlayerShootTable;
    private JTextArea textArea;

    public GameView() { // CONSTRUCTOR

        JDialog playView = new JDialog();
        //playView.setModal(true);
        playView.setSize((DataContainer.getGameboardWidth()*2 + 100), (DataContainer.getGameboardHeight() + 100));
        playView.setUndecorated(true);
        playView.setContentPane(Box.createVerticalBox());

        /**
         * Erstellung der TableView abhaengig des gewaehlten GameTyp
         */

        switch (DataContainer.getGameType()) {
             case "ss":    //SS steht für schnelles Spiel
                 tablePlayer = DataContainer.getTable();
                 DataContainer.setAllowed(true);
                 break;

             case "bdf":  // bdf steht für Benutzerdefiniert
                 tablePlayer = DataContainer.getTable();          // das Place ships window wird die Table angelegt haben
                 DataContainer.setAllowed(true);
                 break;

            case "bdf-loaded":  // bdf steht für Benutzerdefiniert
                tablePlayer = DataContainer.getTable();
                break;

             case "mp":
                 tablePlayer = DataContainer.getTable();
                 break;
        }

        if (!DataContainer.getGameType().equals("bdf-loaded")) {
            PlayerShootTable = new TableView();
            for(int i = 0; i <DataContainer.getGameboardHeight(); i++){
                for(int j = 0; j < DataContainer.getGameboardWidth(); j++){
                    PlayerShootTable.setValueAt(9,i,j);
                }
            }
            DataContainer.setPlayerShootTable(PlayerShootTable);
        }


        /**
         * JMenuBar mit den Unterpunkten zum Spiel speichern/laden und schließen.
         */
        JMenuBar bar = new JMenuBar();
        {
            JMenu menu = new JMenu("File");
            {
                JMenuItem item = new JMenuItem("Spiel laden");
                item.addActionListener(
                        (e) -> {
                            JFileChooser filechooserSave = new JFileChooser();

                            FileFilter filter = new FileFilter() {
                                public boolean accept(File f) {
                                    return f.isDirectory()
                                            || f.getName().toLowerCase().endsWith(".txt");
                                }

                                public String getDescription() {
                                    return "TXT";
                                }
                            };
                            filechooserSave.setFileFilter(filter);
                            int state = filechooserSave.showOpenDialog(null);

                            if (state == JFileChooser.APPROVE_OPTION) {
                                File file = filechooserSave.getSelectedFile();
                                String filename = file.getAbsolutePath();
                                backup.Load.loadSavegame(filename);
                            }
                        }
                );
                menu.add(item);
            }
            {
                JMenuItem item = new JMenuItem("Spiel speichern");
                item.addActionListener(
                        (e) -> {
                            if(DataContainer.getGameType().equals("bdf")
                                    || DataContainer.getGameType().equals("ss")) {
                                JFileChooser filechooserSave = new JFileChooser();

                                FileFilter filter = new FileFilter() {
                                    public boolean accept(File f) {
                                        return f.isDirectory()
                                                || f.getName().toLowerCase().endsWith(".txt");
                                    }

                                    public String getDescription() {
                                        return "TXT";
                                    }
                                };
                                filechooserSave.setFileFilter(filter);
                                int state = filechooserSave.showSaveDialog(null);

                                if (state == JFileChooser.APPROVE_OPTION) {
                                    File file = filechooserSave.getSelectedFile();
                                    String filename = file.getAbsolutePath() + ".txt";
                                    if (DataContainer.getGameType().equals("bdf"))
                                    backup.Save.saveBDF(filename);
                                }
                            }
                        }
                );
                menu.add(item);
            }
            {
                JMenuItem item = new JMenuItem("Beenden");
                item.addActionListener(
                        (e) -> {
                            if(DataContainer.getGameType().equals("mp") || DataContainer.getGameType().equals("mps")) {
                                Network.closeClientConnection();
                                Network.closeHostConnection();
                            }
                            playView.dispose();
                            if(DataContainer.getGameType().equals("mp") || DataContainer.getGameType()
                                    .equals("mps")) {

                            }
                        }
                );
                menu.add(item);
            }
            bar.add(menu);
        }
        /**
         * ScrollPane und TextArea unterhalb des Spielfeldes für
         * saemtliche Informationen
         */
        JScrollPane scrollPane = new JScrollPane();
        textArea = new JTextArea(5, 10);
        textArea.setFont(new Font("Verdana", Font.PLAIN, 10));

        scrollPane.getViewport().add(textArea);

        /**
         * TextArea ausgabe
         */
        textArea.append("Das linke Feld ist Ihres.\n");
        if (DataContainer.getGameType().equals("ss") || DataContainer.getGameType().equals("bdf")) {
            textArea.append("Spieler beginnt\n" + "Geben Sie einen Schuss ab,\n" + "indem Sie in eine Zelle des \n"
                    + "rechten Spielfeldes klicken.\n");
        }


        DataContainer.getPlayerShootTable().addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent event) {
                TouchedMouse(event);
            }
            public void mouseReleased(MouseEvent event) {

            }
        });

        Box hbox = Box.createHorizontalBox();
        hbox.add(tablePlayer);
        hbox.add(Box.createHorizontalStrut(10));
        hbox.add(DataContainer.getPlayerShootTable());

        playView.setJMenuBar(bar);
        playView.add(hbox);
        playView.add(scrollPane);
        playView.pack();
        playView.setLocationRelativeTo(null);
        playView.setVisible(true); // MUST stand at the end this this call in combination with .setModal(true) blocks until return after hide or dispose.

        // IN CASE OF LOADED GAME -> Let Ai draw if its her turn:
        if (DataContainer.getGameType().equals("bdf-loaded") && !DataContainer.getAllowed()) {
            Ai ai = new Ai();
            ai.draw();
        } else if (DataContainer.getGameType().equals("mp") && !DataContainer.getAllowed()) {
            CompletableFuture.supplyAsync(Game::hitloop);
        }

    } // END CONSTRUCTOR


    private boolean asyncAiLoop() {
        Ai ai = new Ai();
        while(!DataContainer.getAllowed()){
            ai.draw();
            if(!DataContainer.getAllowed()) {
                try {
                    TimeUnit.MILLISECONDS.sleep(500);
                } catch (Exception ex) {
                    ;
                }
            }
        }
        return true;
    }


    private void TouchedMouse(MouseEvent e) {
        /*
        speichert die angeklickte Zelle der Table
         */
        Point x = e.getPoint();
        int column = PlayerShootTable.columnAtPoint(x);
        int row = PlayerShootTable.rowAtPoint(x);
        //TEST
        //new VictoryScreen(false);
        //TEST

        if (e.getButton() == MouseEvent.BUTTON1) {  // Linke Maustaste
            Ai ai = new Ai();

            if (DataContainer.getAllowed()) {
                if (DataContainer.getGameType().equals("ss") || DataContainer.getGameType().equals("bdf") || DataContainer.getGameType().equals("bdf-loaded")) {
                    if (DataContainer.getPlayerShootTable().getValueAt(row, column).equals(9)) {
                        int i = Game.shoot(column, row, ai);
                        if (i == 0) {
                            CompletableFuture.supplyAsync(this::asyncAiLoop); // Asynchronous executes makes live view at Ai's draws possible
                        }
                    }
                } else if (DataContainer.getGameType().equals("mp")) {
                    if (DataContainer.getPlayerShootTable().getValueAt(row, column).equals(9)) {
                        int i = Game.shoot(column, row, ai);
                        if (i == 0) {
                            CompletableFuture.supplyAsync(Game::hitloop);
                        } else if (i == -1) {
                            textArea.append("Shot failed due to technical issues. Please try again!");
                        } else if (i == -2) {
                            textArea.append("Shot failed since it wasn't your turn!");
                        }
                    }
                }
            }

            /* if (DataContainer.getAllowed()) { // Dismiss following calls if shooting isn't permitted
                if (DataContainer.getPlayerShootTable().getValueAt(row, column).equals(9)) {
                    int i = Game.shoot(column, row, ai);
                    if (DataContainer.getGameType().equals("mp")) {
                        System.out.println("mp!");
                        if (i == 0) {
                            CompletableFuture.supplyAsync(Game::hitloop);
                        }
                        if (i == -1) {
                            textArea.append("Shot failed due to technical issues. Please try again!");
                        }
                        if (i == -2) {
                            textArea.append("Shot failed since it wasn't your turn!");
                        }
                    } else if(!DataContainer.getAllowed() && (DataContainer.getGameType().equals("ss") || DataContainer.getGameType().equals("bdf"))) {
                        ai.draw();
                    }

                }
            } */
        }
    }
    
}
