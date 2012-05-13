package rad.boardgame.objects;

public enum bitmapLocations {
	BOARDBACKGROUND(0),PLAYER(1),MENU(2),MEMES(3),DICE(4);
	public int index;
	bitmapLocations(int _index){
		index = _index;
	}
}
