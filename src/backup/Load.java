package backup;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import ai.Trace;
import com.google.gson.Gson;
import data.Game;
import gameboard.Board;
import gui.GameView;
import gui.TableView;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import data.DataContainer;
import ai.Ai;
import org.json.simple.parser.ParseException;

import javax.swing.*;

/**
 * Diese Klasse liest ein laufendes Spiel aus einer TXT Datei.
 */
public class Load {


    /**
     * Im Spielmodus "bdf" (benutzerdefiniert) wird diese Methode zum Speichern des Spieles
     * aufgerufen.
     */
    public static void loadSavegame(String file) {

        System.out.println("File: " + file);
        Gson gson = new Gson();

        try {
            JSONParser parser = new org.json.simple.parser.JSONParser();
            Object obj = new Object();

            try {
                obj = parser.parse(new BufferedReader(new FileReader(file)));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            JSONObject jsonObject = (JSONObject) obj;

            Integer gameboardWidth = ((Number) jsonObject.get("gameboardWidth")).intValue(); // returns Long but initial given int - dunno why.
            Integer gameboardHeight = ((Number) jsonObject.get("gameboardHeight")).intValue(); // - * -
            Boolean allowed = (Boolean) jsonObject.get("allowed");

            String playerBoardString = (String) jsonObject.get("playerBoard");
            Board playerBoard = gson.fromJson(playerBoardString, Board.class);

            String aiBoardString = (String) jsonObject.get("aiBoard");
            Board aiBoard = gson.fromJson(aiBoardString, Board.class);

            String aiStrikesString = (String) jsonObject.get("aiStrikes");
            Board aiStrikes = gson.fromJson(aiStrikesString, Board.class);

            String aiTraceString = (String) jsonObject.get("aiTrace");
            Trace aiTrace = gson.fromJson(aiTraceString, Trace.class);

            Boolean aiPlaced = (Boolean) jsonObject.get("aiPlaced");

            DataContainer.setGameType("bdf-loaded");
            DataContainer.setGameboardWidth(gameboardWidth);
            DataContainer.setGameboardHeight(gameboardHeight);
            DataContainer.setAllowed(allowed);
            Game.setMap(playerBoard);

            DataContainer.setTable(new TableView());

            TableView playerTable = new TableView();
            TableView playerShootTable = new TableView();
            playerTable.setFont(new Font("Arial", Font.BOLD, 30));
            playerShootTable.setFont(new Font("Arial", Font.BOLD, 30));


            for (int i = 0; i < gameboardWidth; i++) {
                for (int j = 0; j < gameboardHeight; j++) {
                    int val = playerBoard.getPlayerboardAt(i, j).getStatus();
                    if (val == 7) {
                        playerTable.setValueAt("X", j, i);
                    } else {
                        playerTable.setValueAt(val, j, i);
                    }
                }
            }

            for (int i = 0; i < gameboardWidth; i++) {
                for (int j = 0; j < gameboardHeight; j++) {
                    int val = playerBoard.getPlayershots(i, j);
                    if (val == 7) {
                        playerShootTable.setValueAt("X", j, i);
                    } else {
                        playerShootTable.setValueAt(val, j, i);
                    }
                }
            }

            DataContainer.setTable(playerTable);
            DataContainer.setPlayerShootTable(playerShootTable);

            Ai ai = new Ai();
            ai.setAiBoard(aiBoard);
            ai.setAiStrikes(aiStrikes);
            ai.setTrace(aiTrace);
            ai.setPlaced(aiPlaced);

            new GameView();

        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "The given filepath does not exist!", "Error!", JOptionPane.ERROR_MESSAGE);
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Something went horribly wrong!", "Error!", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Could not load the savegame! Probably it's corrupt!", "Error!", JOptionPane.ERROR_MESSAGE);
        }
    }
}
