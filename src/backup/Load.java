package backup;

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
 * Diese Klasse speichert ein laufendes Spiel in einer TXT Datei
 */
public class Load {


    /**
     * im Spielmodus "bdf" (benutzerdefiniert) wird diese Methode zum speichern des Spieles
     * aufgerufen
     */
    public static void loadSavegame(String file){

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

            String gameType = (String) jsonObject.get("gameType");
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

            // DataContainer.setGameType(gameType);
            DataContainer.setGameType("bdf-loaded");
            DataContainer.setGameboardWidth(gameboardWidth);
            DataContainer.setGameboardHeight(gameboardHeight);
            DataContainer.setAllowed(allowed);
            Game.setMap(playerBoard);

            DataContainer.setTable(new TableView());

            TableView playerTable = new TableView();
            TableView playerShootTable = new TableView();

            for (int i = 0; i < gameboardWidth; i++) {
                for (int j = 0; j < gameboardHeight; j++) {
                    playerTable.setValueAt(playerBoard.getPlayerboardAt(i, j).getStatus(), j, i);
                }
            }

            for (int i = 0; i < gameboardWidth; i++) {
                for (int j = 0; j < gameboardHeight; j++) {
                    playerShootTable.setValueAt(playerBoard.getPlayershots(i, j), j, i);
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

        } catch(FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "The given filepath does not exist!", "Error!", JOptionPane.ERROR_MESSAGE);
        } catch(IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Something went horribly wrong!", "Error!", JOptionPane.ERROR_MESSAGE);
        } catch(Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Could not load the savegame! Probably it's corrupt!", "Error!", JOptionPane.ERROR_MESSAGE);
        }
    }
}
