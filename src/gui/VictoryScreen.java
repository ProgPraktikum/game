package gui;

import javax.swing.*;
import java.awt.*;

public class VictoryScreen {
    private JDialog victory;
    public VictoryScreen(boolean won) {
            won=won;
            victory = new JDialog();
            victory.setModal(true);
            victory.setUndecorated(true);
            victory.setBackground(Color.BLACK);
            victory.setContentPane(Box.createVerticalBox());

            Box vbox = Box.createVerticalBox();
            Box hBoxBot = Box.createHorizontalBox();
            Box hBoxTop = Box.createHorizontalBox();
        Dimension template = new Dimension(250,50);
        JLabel message;

        if(won){
            message = new JLabel(" Gewonnen :)");
        }
        else{
            message= new JLabel(" Verloren :(");
        }
        message.setForeground(Color.WHITE);
        message.setHorizontalAlignment(SwingConstants.CENTER);
        message.setFont(new Font("Tahoma", Font.PLAIN, 25));
        message.setPreferredSize(template);
        message.setMinimumSize(template);
        message.setMaximumSize(template);
        hBoxTop.add(Box.createGlue());
        hBoxTop.add(message);
        hBoxTop.add(Box.createGlue());
        vbox.add(hBoxTop);
        vbox.add(Box.createVerticalStrut(10));   //Abstand zwischen Buttons
        vbox.add(Box.createGlue());
            /**
             * zurueck Button
             */

            JButton back = new JButton("zurück");
            back.setAlignmentX(Component.CENTER_ALIGNMENT);
            back.setPreferredSize(template);
            back.setMinimumSize(template);
            back.setMaximumSize(template);
            back.setBackground(Color.BLACK);
            back.setForeground(Color.WHITE);
            back.setFont(new Font("Tahoma", Font.PLAIN, 20));

            back.addActionListener(
                    (e) -> {
                        victory.dispose();               // schließt den JDialog
                    }
            );
            hBoxBot.add(Box.createGlue());
            hBoxBot.add(back);
            hBoxBot.add(Box.createGlue());
            vbox.add(hBoxBot);
            

            //
            //hbox.add(vbox);
            //hbox.add(Box.createGlue());

            victory.add(vbox);
            victory.pack();
            victory.setLocationRelativeTo(null);

            victory.setVisible(true);
        }
    }

