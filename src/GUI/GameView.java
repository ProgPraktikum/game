package GUI;

import Data.DataContainer;

import javax.swing.*;
import java.awt.*;

public class GameView {

    private TableView tablePlayer;
    private TableView tableEnemy;

    public GameView(){

        JDialog playView = new JDialog();
        playView.setModal(true);
        playView.setSize((DataContainer.getSpielFeldBreite()*2 + 100), (DataContainer.getSpielFeldHoehe() + 100));
        playView.setBackground(Color.BLACK);
        playView.setForeground(Color.WHITE);
        playView.setContentPane(Box.createVerticalBox());

        /**
         * erstellung der TableView abhängig des gewählten GameTyp
         */
        if(DataContainer.getGameType().equals("ss")) {   //SS steht für schnelles Spiel
            DataContainer.setTable(new TableView());
            tablePlayer = DataContainer.getTable();
            tableEnemy = new TableView();
        }else if(DataContainer.getGameType().equals("bdf")){ // bdf steht für Benutzerdefiniert
            //only für Testzwecke die nächste zeile
            DataContainer.setTable(new TableView());
            tablePlayer = DataContainer.getTable();          // das Place ships window wird die Table anlegen
            tableEnemy = new TableView();
        }


        /**
         * ScrollPane und TextArea unterhalb des Spielfeldes für sämtliche Informationen
         */
        JScrollPane scrollPane = new JScrollPane();
        JTextArea textArea = new JTextArea(5, 10);
        textArea.setFont(new Font("Verdana", Font.PLAIN, 10));

        scrollPane.getViewport().add(textArea);

        /**
         * TextArea ausgabe
         */
        textArea.append("Das linke Feld ist Ihres.\n");
        if (DataContainer.getGameType().equals("ss") || DataContainer.getGameType().equals("bdf"))
            textArea.append("Sie beginnen das Spiel.\n"
                    + "Geben Sie einen Schuss ab,\n"
                    + "indem Sie in eine Zelle des \n"
                    + "rechten Spielfeldes klicken.\n");


        Box hbox = Box.createHorizontalBox();
        hbox.add(tablePlayer);
        hbox.add(Box.createHorizontalStrut(20));
        hbox.add(tableEnemy);


        playView.add(hbox);
        playView.add(scrollPane);
        playView.pack();
        playView.setLocationRelativeTo(null);
        playView.setVisible(true);
    }
}
