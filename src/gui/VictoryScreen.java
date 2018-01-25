package gui;

import javax.swing.*;
import java.awt.*;

class VictoryScreen {
    private JDialog victory;
    VictoryScreen(boolean won) {

        /*victory = new JDialog();
        victory.setModal(true);
        victory.setUndecorated(true);
        victory.setBackground(Color.BLACK);
        victory.setContentPane(Box.createVerticalBox());
        Box vbox = Box.createVerticalBox();
        victory.setPreferredSize(new Dimension(250,100));
        victory.setMinimumSize(new Dimension(250,100));
        victory.setMaximumSize(new Dimension(250,100));
        JButton retry=new JButton("zurueck");
        retry.setBackground(Color.BLACK);
        retry.setForeground(Color.WHITE);
        retry.setFont(new Font("Tahoma", Font.PLAIN, 20));
        JLabel message;
        if(won){
            message = new JLabel("Sie haben Gewonnen!");
        }
        else{
            message= new JLabel("Sie haben Verloren");
        }
        vbox.add(message);
        vbox.add(retry);
        retry.addActionListener(
                (e) -> {
                    if(DataContainer.getGameType().equals("mp") || DataContainer.getGameType().equals("mps")) {
                        Network.closeClientConnection();
                        Network.closeHostConnection();
                    }
                    //playView.dispose();
                }
        );
        victory.add(vbox);
    }*/



        //SelectModi(){

            victory = new JDialog();

            victory.setModal(true);
            victory.setUndecorated(true);
            victory.setBackground(Color.BLACK);
            victory.setContentPane(Box.createVerticalBox());

            Box vbox = Box.createVerticalBox();
            Box hbox = Box.createHorizontalBox();

            //hbox.add(Box.createHorizontalStrut(20));
            //vbox.add(Box.createVerticalStrut(20));

        JLabel message;

        if(won){
            message = new JLabel("Sie haben Gewonnen!");
        }
        else{
            message= new JLabel("Sie haben Verloren");
        }
        message.setForeground(Color.WHITE);
        message.setFont(new Font("Tahoma", Font.PLAIN, 30));
        vbox.add(Box.createGlue());
        vbox.add(message);
            vbox.add(Box.createGlue());   //Abstand zwischen Buttons



            /**
             * zurueck Button
             */

            JButton back = new JButton("zurück");
            back.setAlignmentX(Component.CENTER_ALIGNMENT);
            back.setPreferredSize(new Dimension(250, 50));
            back.setMinimumSize(new Dimension(250, 50));
            back.setMaximumSize(new Dimension(250, 50));
            back.setBackground(Color.BLACK);
            back.setForeground(Color.WHITE);
            back.setFont(new Font("Tahoma", Font.PLAIN, 20));

            back.addActionListener(
                    (e) -> {
                        victory.dispose();               // schließt den JDialog
                    }
            );
            vbox.add(Box.createGlue());
            vbox.add(back);
            vbox.add(Box.createGlue());

            //vbox.add(Box.createGlue());
            hbox.add(vbox);
            //hbox.add(Box.createGlue());


            victory.add(hbox);
            victory.pack();
            victory.setLocationRelativeTo(null);

            victory.setVisible(true);
        }
    }

