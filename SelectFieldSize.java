import javax.swing.*;
import java.awt.*;

public class SelectFieldSize {

    public SelectFieldSize(){

        // Fenster für die eingabe der Spielfeldgröße
        JDialog eingaben = new JDialog();
        eingaben.setModal(true);
        eingaben.setContentPane(Box.createVerticalBox());
        eingaben.setMinimumSize(new Dimension(400,400));
        eingaben.setBackground(Color.BLACK);
        eingaben.setUndecorated(true);


        Box hbox = Box.createHorizontalBox();
        Box hbox2 = Box.createHorizontalBox();

        Box vbox = Box.createVerticalBox();


        vbox.add(Box.createVerticalStrut(20));
        JLabel groesseFeld = new JLabel("Spielfedgröße (5-30)");
        groesseFeld.setAlignmentX(Component.CENTER_ALIGNMENT);
        groesseFeld.setFont(new Font("Tahoma", Font.PLAIN, 25));
        groesseFeld.setForeground(Color.WHITE);

        vbox.add(Box.createVerticalStrut(20));
        hbox.add(Box.createHorizontalStrut(5));

        //Textfeld für den X Wert der Spielfeldgroesse
        JTextField feldx = new JTextField();
        feldx.setPreferredSize(new Dimension(150 , 30));
        feldx.setMinimumSize(new Dimension(150, 30));
        feldx.setMaximumSize(new Dimension(150, 30));

        //Textfeld für den Y Wert der Spielfeldgroesse
        JTextField feldy = new JTextField();
        feldy.setPreferredSize(new Dimension(150 , 30));
        feldy.setMinimumSize(new Dimension(150, 30));
        feldy.setMaximumSize(new Dimension(150, 30));

        // X zwischen den Textfeldern
        JLabel abstand = new JLabel("X");
        abstand.setFont(new Font("Tahoma", Font.PLAIN, 20));
        abstand.setForeground(Color.WHITE);

        /*
         Bestaetigen Button ( ließt die eingegebenen Werte ein und erstellt darauf hin ein Array[][] \
         als Grundlage für das Spielfeld
        */
        JButton ok = new JButton("OK");
        ok.setBackground(Color.BLACK);
        ok.setForeground(Color.WHITE);
        ok.setFont(new Font("Tahoma", Font.PLAIN, 20));
        ok.addActionListener(
                (e) -> {
                    // TODO Einlesen der werte und Erstellung eines Array[][] als Spielfeld
                    eingaben.setVisible(false);
                    new SelectShips();

                }
        );


        // abbrechen Button (schließt den JDialog)
        hbox2.add(Box.createHorizontalStrut(10));

        JButton abbrechen = new JButton("abbrechen");
        abbrechen.setBackground(Color.BLACK);
        abbrechen.setForeground(Color.WHITE);
        abbrechen.setFont(new Font("Tahoma", Font.PLAIN, 20));
        abbrechen.addActionListener(
                (e) -> {
                    eingaben.dispose(); }
        );

        hbox.add(feldx);
        hbox.add(abstand);
        hbox.add(feldy);

        hbox2.add(ok);
        hbox2.add(abbrechen);

        vbox.add(groesseFeld);
        vbox.add(hbox);

        eingaben.add(vbox);
        eingaben.add(hbox2);

        eingaben.pack();
        eingaben.setLocationRelativeTo(null);
        eingaben.setVisible(true);

    }
}
