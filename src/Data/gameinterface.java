package Data;
public interface gameinterface {
	/* 
	@Author: Felix
	Desc: Hauptkomponente des spiels komuniziert mit spielfeld, netzwerk
	und gui player 0 ist spieler bzw bei Ai vs Ai die erste Ai, player 1 ist bei multiplayer 
	der 2. spieler und bei mensch vs Ai der Mensch
	*/
	/*
	initialize attributes with default values for quick singleplayergame
	*/
	boolean ishost=false;
	boolean player0isbot=false;
	boolean player1isbot=true;

	//methoden

	/*
	plaziert gesamte flotte per zufallsgenerierung solange bis alle schiffe erfolgreich plaziert sind
	*/
	void placeFleet(int player);

	/*erzeugt ein neues schiff mit länge l welche aus dem shiplengths stack gelesen wird
	anschließend wird das schiff in dens chiffstack geschrieben
	 */
	void buildship();


	/*
	methode verschiebt selectedShip an andere koordinate und ueberprueft ob die verschiebung valide ist
	ansonsten wird die position zurueckgesetzt
	bei erfolg wird true ausgegeben und bei misserfolg false
	 */
	boolean moveShip(int x, int y);

	/*
	aendert orientierung von selectedShip
	*/
	void rotateShip();

	/*
	plaziert schiff auf spielbrett
	*/
	boolean placeShip(ship s);


	/* methode um schüsse abzugeben, übergeben wird die zielkoordinate und der zielspieler
	zurück kommt entweder 0="Wasser", 1="Treffer" oder 2="Versenkt" entsprechende anzeige auf dem gui folgt
	der erste schuss iniziert das spiel, der andere spieler antwortet darauf sobald seine flotte plaziert ist
	*/
	int shoot(int x, int y, int player);

	/*speichert spielstand ab($magicfoo) und sendet falls host save message über socket an gegner
	erhält eindeutige id für dateibenennung entweder von host oder selbstgeneriert wenn hostflag true,
	dann wird speichermessage an gegner übertragen
	*/
	void save(int id);
}
