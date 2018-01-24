package backup;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.Gson;
import data.Game;
import gameboard.Board;
import org.json.simple.JSONObject;

import data.DataContainer;
import ai.Ai;

/**
 * Diese Klasse speichert ein laufendes Spiel in einer TXT Datei
 */
public class Save {

    /**
     * im Spielmodus "bdf" (benutzerdefiniert) wird diese Methode zum speichern des Spieles
     * aufgerufen
     */
    public static void saveBDF(String file){

        try {

            BufferedWriter writer = new BufferedWriter(new FileWriter(file));

            Gson gson = new Gson();
            JSONObject out = new JSONObject();

            /* gameType wird in das Savegame geschrieben */
            out.put("gameType", DataContainer.getGameType());

            /* Die Spielfeldbreite wird in das Savegame geschrieben */
            out.put("gameboardWidth", DataContainer.getGameboardWidth());

            /* Die Spielfeldhoehe wird in das Savegame geschrieben */
            out.put("gameboardHeight", DataContainer.getGameboardHeight());

            /* Der 'allowed' Wert wird in das Savegame geschrieben */
            out.put("allowed", DataContainer.getAllowed());

            /* Die Spieler-Spielfelder werden in das Savegame geschrieben */
            //String playerTable = gson.toJson(DataContainer.getTable().);
            //String playerShootTable = gson.toJson(DataContainer.getPlayerShootTable());

            //out.put("playerTable", playerTable);
            //out.put("playerShootTable", playerShootTable);

            String playerBoard = gson.toJson(Game.getMap());
            out.put("playerBoard", playerBoard);

            /* Die ai-Spielfelder werden in das Savegame geschrieben */
            Ai ai = new Ai();
            String aiBoard = gson.toJson(ai.getAiBoard());
            String aiStrikes = gson.toJson(ai.getAiStrikes());

            out.put("aiBoard", aiBoard);
            out.put("aiStrikes", aiStrikes);

            /**
             * Schreibe erstelltes JSON in das Savegame
             */
            writer.write(out.toJSONString());

            /**
             * Schließt den Writer
             */
            writer.close();

        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
