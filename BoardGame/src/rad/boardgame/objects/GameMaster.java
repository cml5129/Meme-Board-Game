package rad.boardgame.objects;

import java.util.ArrayList;
import java.util.List;

import rad.boardgame.Panel;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;

public class GameMaster {
	enum Mode{
		start,normal,roll,move,zoom,pick,roll6,rollBack,pickPlayer
	}
	private List<Player> players;
	private Board board;
	private int numPlayers = 4,movesLeft,moveDir = -1;
	private Rect screen,boardSize,area;
	private Canvas overlayCanvas;
	private int playersTurn;
	private int startdrawDice,endDrawDice;
	private boolean anotherTurn = false,winningTouch;
	private Player winner,rollBack;
	private final double TIME = 500;
	private double wait = 0;
	
	private Dice dice;
	private List<Dice> di;
	Bitmap bmOverlay;
	private Mode mode;
	private Pick pick,drink;
	private Paint paint,red;
	public GameMaster(Bitmap[] bitmaps,List<memeSet> files){
		board = new Board(bitmaps,files);
		players = new ArrayList<Player>();
		di = new ArrayList<Dice>();
		for(int i = 0; i < numPlayers; i++){
			players.add(new Player(bitmaps,0));
			di.add(new Dice(bitmaps));
		}
		screen= new Rect(0,0,(int)Panel.mWidth,(int)Panel.mHeight);
		boardSize = new Rect(0,0,(int)Panel.mWidth-70,(int)Panel.mHeight);		
		bmOverlay = Bitmap.createBitmap(screen.width(),screen.height(),bitmaps[bitmapLocations.MEMES.index].getConfig());
		area = new Rect((int)Panel.mWidth-70,0,(int)Panel.mWidth,(int)Panel.mHeight);
		overlayCanvas = new Canvas(bmOverlay);
		for(Player player:players){
			board.setPlayer(player);
		}		
		winner = null;
		mode = Mode.start;
		dice = new Dice(bitmaps);
		red = new Paint();
		red.setColor(Color.RED);
		red.setTextSize(15);
		
		paint = new Paint();
		paint.setColor(Color.WHITE);
		picker[][] picker = new picker[2][2];
		picker[0][0] = players.get(0);
		picker[0][1] = players.get(1);
		picker[1][0] = players.get(2);
		picker[1][1] = players.get(3);
		
		pick = new Pick(2,2,screen,picker);
		
		Drink[][] drinks = new Drink[2][1];
		drinks[0][0] = new Drink(bitmaps,"Drink!",DrinkOrGoBack.Drink);
		drinks[1][0] = new Drink(bitmaps,"Go Back!",DrinkOrGoBack.GoBack);
		drink = new Pick(2,1,screen,drinks);
		
		winningTouch = false;
		
		
	}
	public void doDraw(Canvas canvas){
		canvas.drawColor(Color.BLACK);
		switch(mode){
		case roll:
		case move:
		case normal:
			board.doDraw(overlayCanvas, screen);
			for(int i = 0; i< numPlayers;i++){
				players.get(i).doDraw(overlayCanvas);
			}		
			canvas.drawRect(area, paint);
			canvas.drawText("Player "+players.get(playersTurn).getName()+"'s", (int)Panel.mWidth-65, 40, red);
			canvas.drawText("Turn", (int)Panel.mWidth-65, 62, red);
			canvas.drawBitmap(bmOverlay,screen,boardSize,null);
			if(mode == Mode.roll || mode == Mode.move){
				dice.doDrawBoard(canvas);
			}
			break;
		case rollBack:			
		case zoom:
			board.doDraw(canvas, players.get(playersTurn));
			dice.doDraw(canvas);
			break;
		case start:
			board.doDraw(canvas, players.get(0));
			for(int i = 0; i< playersTurn;i++){
				di.get(i).doDraw(canvas,i);
			}
			break;
		case roll6:
			board.doDraw(canvas, players.get(endDrawDice));
			for(int i = 0; i < di.size();i++){
				if(di.get(i).getRolled()){
					di.get(i).doDraw(canvas, i);
				}
			}
			break;
		case pickPlayer:
			board.doDraw(canvas, players.get(endDrawDice));
			pick.doDraw(canvas);
			break;					
		case pick:
			board.doDraw(canvas, players.get(endDrawDice));
			drink.doDraw(canvas);
			break;			
	}
	}
	public void animate(long elapsedTime) 
	{

		switch(mode){
		case move:
			wait += elapsedTime;
			if(wait > TIME){
				movesLeft--;
				Log.d("rad", "movesLeft for player "+players.get(playersTurn).getPlayerNum()+" is "+movesLeft);
				if(movesLeft < -1){
					mode = Mode.zoom;
				}
				else if(movesLeft < 0){
					
				}
				else{
					movePlayer();	
				}
				wait = 0;
			}
		}
	}
	public void onTouch(MotionEvent event){
		switch(mode){
		case normal:
			Roll();
			break;		
		case zoom:
			mode = Mode.normal;
			unzoom();
			if(winner!= null){
				winningTouch = true;
			}
			break;
		case roll:
			break;
		case rollBack:
			dice.roll();
			rollBack.setTileLocation(rollBack.getTileLocation()-dice.getRoll());
			mode = Mode.zoom;
			break;
		case start:
			if(playersTurn < numPlayers){
				di.get(playersTurn).roll();
				di.get(playersTurn).setName(players.get(playersTurn).getName());
				playersTurn++;
			}
			else{
				setStartingPlayer();
				for(int i = 0; i < 4; i++){
					if( i < numPlayers){
						players.get(i).turnOn();
					}else{
						players.get(i).turnOff();
					}
				}
				mode = Mode.normal;
			}
			break;
		case roll6:
			if(di.get(startdrawDice).getRolled()){
				for(int i = 0; i < di.size();i++){
					if(di.get(i).getRoll() == 6){
						players.get(i).setTileLocation(0);
						board.setPlayer(players.get(i));
					}
				}
				mode = Mode.normal;
			}else{
				di.get(startdrawDice).roll();
				di.get(startdrawDice).setRolled(true);
				startdrawDice++;
				if(startdrawDice >= numPlayers)
					startdrawDice = 0;
			}
			break;
		case pickPlayer:
			pick.onTouch(event);
			Player p = ((Player)pick.getPicker());
			p.setTileLocation(p.getTileLocation()-3);
			board.setPlayer(p);
			mode = mode.zoom;
			break;
		case pick:
			drink.onTouch(event);
			Drink drinks = ((Drink)drink.getPicker());
			Player player =players.get(endDrawDice);
			if(drinks.getObject() == DrinkOrGoBack.GoBack){
				player.setTileLocation(player.getTileLocation()-3);
				board.setPlayer(player);
				mode = mode.zoom;
			}
			else{
				mode = mode.normal;
			}
			break;
		}
	}
	public void Roll(){
		dice.roll();
		dice.setName(players.get(playersTurn).getName());
		Roll(dice.getRoll());
		//mode = Mode.zoom;
	}
	public void unzoom(){
		mode = Mode.normal;
		performAction();
	}
	public void setStartingPlayer(){
		int start = 0;
		int roll = di.get(0).getRoll();
		for(int i = 1; i < numPlayers;i++){
			if(di.get(i).getRoll() > roll){
				start = i;
				roll = di.get(i).getRoll();
			}
		}
		playersTurn = start;
	}
	public String isWinner(){
		return !winningTouch?null:winner.getName();
	}
	public void Roll(int rolled){
		Player player = players.get(playersTurn);
		movePlayer(player,rolled);
	}
	public void performAction(){
		Player player = players.get(playersTurn);
		doAction(player);
		board.setPlayer(player);
		if(anotherTurn){
			anotherTurn = false;
		}
		else {
			nextPlayersTurn();
		}		
	}
	public void nextPlayersTurn(){
		if(++playersTurn >= numPlayers){
			playersTurn = 0;
		}
		if(!players.get(playersTurn).canGo()){
			nextPlayersTurn();
		}
	}
	public void movePlayer(Player p,int numTiles){
		Log.d("rad", "Move player "+p.getPlayerNum()+" "+numTiles);
		movesLeft = Math.abs(numTiles);
		moveDir = numTiles > 0 ? -1: 1;
		mode = Mode.move;
	}
	public void movePlayer(){
		int location = players.get(playersTurn).getTileLocation();
		Log.d("rad", "Move player "+players.get(playersTurn).getPlayerNum()+" "+(location -moveDir));
		int newLocation = location -moveDir;
		if(newLocation >= board.getNumTiles()){
			winner = players.get(playersTurn);
			players.get(playersTurn).setTileLocation(board.getNumTiles()-1);
			return;
		}
		players.get(playersTurn).setTileLocation(newLocation);
		board.setPlayer(players.get(playersTurn));
	}
	public void doAction(Player p){
		switch(board.performAction(p)){
		case Beginning:
			p.setTileLocation(0);
			break;
		case AnotherTurn:
			anotherTurn = true;
			break;
		case SkipTurn:
			p.skipTurn();
			break;
		case GoBack3:
			movePlayer(p,-3);
			break;
		case GoBack10:
			movePlayer(p,-10);
			break;
		case LeaderGoBackToStart:
			leaderGoBackToStart();
			break;
		case Roll6:
			mode = Mode.roll6;
			startdrawDice = playersTurn;
			endDrawDice = playersTurn;
			for(Dice dice:di){
				dice.setRolled(false);
			}
			break;
		case RollBack:
			mode = Mode.rollBack;
			rollBack = p;
			break;
		case PickGoBack3:
			endDrawDice = playersTurn;
			mode = Mode.pickPlayer;
			break;
		case DrinkOrGoBack3:
			endDrawDice = playersTurn;
			mode = Mode.pick;			
			break;
		}
	}
	public void leaderGoBackToStart(){
		Player leader = null;
		for(int i = 0; i< numPlayers;i++){
			if(leader == null){
				leader = players.get(i);
				continue;
			}
			if(players.get(i).getTileLocation() > leader.getTileLocation()){
				leader= players.get(i);
			}
		}
		leader.setTileLocation(0);
		board.setPlayer(leader);
	}
	public void update(){
		
	}
	public void Reset(){
		for(Player player:players){
			player.reset();
		}
		winner = null;
		winningTouch = false;
	}
	public void shuffle(){
		board.shuffle();
	}
	public int getNumPlayers() {
		return numPlayers;
	}
	public void setNumPlayers(int numPlayers) {
		this.numPlayers = numPlayers;
	}
	
}
