import gui.GUIMain;
import gui.VictoryScreen;

import javax.swing.*;

/** Diese Klasse dient dazu ein neues Spiel in einem neuen Thread zu starten
 *
 * @author Christopher Kisch, Jan Riedel, Felix Graeber
 *
 */
public class Start {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {

                new GUIMain();
            }
        });
    }
}
