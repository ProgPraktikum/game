package backup;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import com.google.gson.Gson;
import data.Game;
import gameboard.Board;
import gui.GameView;
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

            DataContainer.setGameType(gameType);
            DataContainer.setGameboardWidth(gameboardWidth);
            DataContainer.setGameboardHeight(gameboardHeight);
            DataContainer.setAllowed(allowed);
            Game.setMap(playerBoard);
            Ai ai = new Ai();
            ai.setAiBoard(aiBoard);
            ai.setAiStrikes(aiStrikes);

            new GameView();

        } catch(IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Something went horribly wrong!");
        } catch(Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Could not load the savegame! Probably it's corrupt!");
        }
    }
}
