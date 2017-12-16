//variable Datacontainer.allowed:
//Boolean, der bei true dem spieler erlaubt zu schießen
//und bei false dem gegner
//wird beim aufruf von getHit automatisch auf true gesetzt wenn 0 zurückgegeben wird
//(salven modus: man darf solange schießen bis man daneben schießt)

//Playerboard besteht aus AbstractTiles
//diese beinhalten:
    //Atribute
        Ship master //schiff wellches an dieser stelle im feld platziert ist
        int status  //statuswert den das feld an der stelle hat
                    //(0 wasser, 1 treffer,2 versenkt, 3 schiff(unberührt)

//die arrays werden mit $array[y][x] initialisiert,
//um sie identisch zu den tabellen auf der oberfläche zu erzeugen
//(bei [x][y] wärde das ganze um 90° verdreht)


//Konstruktor
//holt sich alle benötigten Parameter (höhe/ breite) aus dem Datacontainer

    Board()

//setzt entsprechendes feld x,y im playershots array auf wert value

    void setPlayershots(int x, int y, int value)

//gibt  wert an stelle x,y aus Playershots zurück

    int getPlayershots(int x, int y)

//überprüft ob an stelle x, y im playerboard Wasser(=0) oder ein schiff ist,
//getroffen (=1), oder versenkt (=2)
//der hitcounter des entsprechenden schiffs wird automatisch runtergezählt und wenn versenkt
//der status in allen entsprechenden feldern geändert
//gibt bei fehler -1 zurück

    int checkboard(int x, int y)

//bewegt schiff wenn möglich an stelle x, y
//falls nicht möglich wird position zurückgesetzt und false ausgegeben
//bei erfolg wird true ausgegeben

    boolean moveShip(int x, int y, Ship s)

//ändert orrientierung des schiffs
//auf (0 links, 1 oben, 2 rechts ,3 unten)

    void rotateship(int i,, Ship s)

// löscht schiff aus array

    void removeShip(Ship s)

platziert schiff an aktueller position gibt true bei erfolg und false bei fehlschlag zurück
    public boolean place (Ship s)

//prüft ob schiff an aktueller position und orrientierung platziert werden darf
//wird von moveShip und place automatisch aufgerufen
//false= ungültig
//true = gpültig

boolean checkplace(Ship s)

//gibt playerboard an position x, y zurück

    AbstractTile getPlayerboardAt (int x, int y)

//setzt Playerboard status an stelle x,y

    void stetPlayerboardAt(int x, int y, int value)

//setzt Playerboard master an stelle x,y

    void SetPlayerboardAt(int x,int y, Ship master)

//METHODEN AUS Game:

//erzeugt treffer beim spieler an stelle x,y
//gibt art des treffers zurück: 0,1,2
//setzt Boolean allowed bei 0 auf true

   int getHit(int x, int y)