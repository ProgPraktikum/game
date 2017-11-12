package GUI;

import Data.DataContainer;

import javax.swing.*;
import java.awt.*;

public class SelectShips {

    public SelectShips() {

        // Fenster für die Auswahl der Schiffe
        JDialog ships = new JDialog();
        ships.setModal(true);
        ships.setUndecorated(true);
        ships.setContentPane(Box.createVerticalBox());
        ships.setMinimumSize(new Dimension(400,400));
        ships.setBackground(Color.BLACK);




        // Bestätigung Button
        JButton ok = new JButton("OK");
        ok.setBackground(Color.BLACK);
        ok.setForeground(Color.WHITE);
        ok.setFont(new Font("Tahoma", Font.PLAIN, 20));
        ok.addActionListener(
                (e) -> {
                    new GameView();
                }
        );
        // abbrechen Button (schließt den JDialog)
        JButton abbrechen = new JButton("abbrechen");
        abbrechen.setBackground(Color.BLACK);
        abbrechen.setForeground(Color.WHITE);
        abbrechen.setFont(new Font("Tahoma", Font.PLAIN, 20));
        abbrechen.addActionListener(
                (e) -> {
                    ships.dispose(); }
        );

        Box btn_box = Box.createHorizontalBox();

        btn_box.add(ok);
        btn_box.add(Box.createHorizontalStrut(2));
        btn_box.add(abbrechen);
        ships.add(btn_box);
        ships.pack();
        ships.setLocationRelativeTo(null);
        ships.setVisible(true);
    }
}
