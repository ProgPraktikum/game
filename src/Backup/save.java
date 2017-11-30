package Backup;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import Data.DataContainer;


/**
 * Diese Klasse speichert ein laufendes Spiel in einer TXT Datei
 */
public class save {


/**
 * im Spielmodus "bdf" (benutzerdefiniert) wird diese Methode zum speichern des Spieles
 * aufgerufen
 */
public static void saveBDF(String file){

    try{

        BufferedWriter save = new BufferedWriter(new FileWriter(file));

        /**
         * gameType wird in file geschrieben
         */
        save.write("gameType ");
        save.write(DataContainer.getGameType());
        save.write("\r\n");

        /**
         * Die Spielfeldbreite wird in file geschrieben
         */
        save.write("Fieldwidth ");
        save.write("" + DataContainer.getSpielFeldBreite());
        save.write("\r\n");

        /**
         * Die Spielfeldhoehe wird in file geschrieben
         */
        save.write("Fieldheight ");
        save.write("" + DataContainer.getSpielFeldHoehe());
        save.write("\r\n");


        /**
         * schließt den writer
         */
        save.close();

    }catch(IOException e){
        e.printStackTrace();
    }
}
}
