package gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.io.File;
import java.io.IOException;


/**
 * Diese Klasse bildet das Startfenster, also quasi das Hauptmenue.
 * Es wird ein JFrame erstellt, welches ein Hintergrundbild beherbergt
 * mehrere JButton um ein Neues Spiel zu starten, ein vorhandes zu laden
 * oder das ganze Spiel wieder zu beenden.
 *
 * @author Christopher Kisch, Jan Riedel, Felix Graeber
 */
public class GUIMain {

    JFrame mainFrame;


    public GUIMain() {

        mainFrame = new JFrame();
        mainFrame.setTitle("Schiffe versenken");
        mainFrame.setUndecorated(true);
        mainFrame.setBackground(Color.BLACK);
        mainFrame.setContentPane(Box.createVerticalBox());

        /**
         Schlachtschiff Bild (Startbildschirm)
         */
        ImageIcon cover = null;
        try {
            Image image = ImageIO.read(getClass().getResource("Schlachtschiff.jpg"));
            cover = new ImageIcon(image);
        } catch (IOException e) {
            e.printStackTrace();
        }

        JLabel schlachtschiff = new JLabel(cover);
        schlachtschiff.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainFrame.add(schlachtschiff);


        /**
         ButtonBox nimmt saemtliche vorhanden JButtons auf
         */
        Box btn_box = Box.createVerticalBox();

        /*
         Neues Spiel Button
         */
        btn_box.add(Box.createVerticalStrut(20));       //Abstand zwischen Bild und Button
        JButton newGameBtn = new JButton("Neues Spiel");
        newGameBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        newGameBtn.setPreferredSize(new Dimension(250, 50));
        newGameBtn.setMinimumSize(new Dimension(250, 50));
        newGameBtn.setMaximumSize(new Dimension(250, 50));
        newGameBtn.setBackground(Color.BLACK);
        newGameBtn.setForeground(Color.WHITE);
        newGameBtn.setFont(new Font("Tahoma", Font.PLAIN, 20));
        newGameBtn.addActionListener(
                (e) -> {

                    new SelectModi();
                }
        );
        btn_box.add(newGameBtn);

        /*
          Spiel laden Button
         */
        btn_box.add(Box.createVerticalStrut(7));   //Abstand zwischen Buttons
        JButton loadBtn = new JButton("Spiel laden");
        loadBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        loadBtn.setPreferredSize(new Dimension(250, 50));
        loadBtn.setMinimumSize(new Dimension(250, 50));
        loadBtn.setMaximumSize(new Dimension(250, 50));
        loadBtn.setBackground(Color.BLACK);
        loadBtn.setForeground(Color.WHITE);
        loadBtn.setFont(new Font("Tahoma", Font.PLAIN, 20));
        loadBtn.addActionListener(
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
        btn_box.add(loadBtn);


        /*
        Spiel verlassen
         */
        btn_box.add(Box.createVerticalStrut(7));   //Abstand zwischen Buttons
        JButton leave = new JButton("Beenden");
        leave.setAlignmentX(Component.CENTER_ALIGNMENT);
        leave.setPreferredSize(new Dimension(250, 50));
        leave.setMinimumSize(new Dimension(250, 50));
        leave.setMaximumSize(new Dimension(250, 50));
        leave.setBackground(Color.BLACK);
        leave.setForeground(Color.WHITE);
        leave.setFont(new Font("Tahoma", Font.PLAIN, 20));
        leave.addActionListener(
                (e) -> {
                    System.exit(0);
                }
        );
        btn_box.add(leave);
        btn_box.add(Box.createVerticalStrut(20));

        mainFrame.add(btn_box);
        mainFrame.pack();
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);

    }


}
