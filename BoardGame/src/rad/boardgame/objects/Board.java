package rad.boardgame.objects;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import rad.boardgame.Panel;

import android.content.res.XmlResourceParser;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.EventLog.Event;
import android.util.Log;

public class Board {
	private List<Tile> board;
	private Bitmap background;
	private int boardsize = 24;
	private Rect maxBoardSize;
	
	public Board(Bitmap[] bitmaps,List<memeSet> files){ 
		board = new ArrayList<Tile>();
		background = bitmaps[bitmapLocations.BOARDBACKGROUND.index];
		maxBoardSize = new Rect(0,0,(int)Panel.mWidth,(int)Panel.mHeight);
		List<Tile> fakeBoard = new ArrayList<Tile>();
		board = new ArrayList<Tile>();
		Rect loc,source;
		int x,y,width,height;
		String top,bottom,action,special;
		Tile start,end,used;
		int event;
		for(memeSet meme : files){
			try{
			XmlResourceParser xml = meme.getXmlResourceParser();
			xml.next();
			xml.next();
			xml.next();
			event = xml.getEventType();
			while(event != XmlPullParser.END_TAG && xml.getName()!= null && xml.getName().contentEquals("meme")){
				//<X>
				xml.next();
				//text
				xml.next();
				x = Integer.parseInt(xml.getText());
				//</X>
				xml.next();
				//<Y>
				xml.next();
				//text
				xml.next();
				y = Integer.parseInt(xml.getText())	;
				//</Y>
				xml.next();
				//<width>
				xml.next();
				//text
				xml.next();
				width =Integer.parseInt(xml.getText());
				//</width>
				xml.next();
				//<height>
				xml.next();
				//text
				xml.next();		
				height = Integer.parseInt(xml.getText());
				//</height>
				xml.next();
				//<topText>
				xml.next();
				//text
				xml.next();
				top = xml.getText();
				//</topText>
				xml.next();
				//<bottomText>
				xml.next();
				//text
				xml.next();
				bottom = xml.getText();
				//</bottomText>
				xml.next();
				//<action>
				xml.next();
				//text
				xml.next();				
				action = xml.getText();
				//</action>
				xml.next();
				//<special>
				xml.next();
				//text
				xml.next();				
				special = xml.getText();
				//</special>
				xml.next();
				//</meme>
				xml.next();
				//<meme>?
				xml.next();
				used = new Tile(meme.getBitmap(),x,y,width,height,action,special,top,bottom);
				if(special.equalsIgnoreCase("start")){
					start = used;
				}
				else if(special.equalsIgnoreCase("end")){
					end = used;
				}
				board.add(used);
			}
			}catch(Exception e){
				Log.e("rad",e.getMessage());				
			}
		}
		int counter = 0;
		int i = 3;
		//first row 
			for(int j = 5; j>=0;j--){
				source = new Rect((int)(j*Panel.mWidth/6),(int)(i*Panel.mHeight/4),(int)((j+1)*Panel.mWidth/6),(int)((i+1)*Panel.mHeight/4));
				board.get(counter).setLocation(source);
				board.get(counter++).setDrawScreenLocation(maxBoardSize);
			}
			i = 2;
			for(int j = 0; j<6;j++){
				source = new Rect((int)(j*Panel.mWidth/6),(int)(i*Panel.mHeight/4),(int)((j+1)*Panel.mWidth/6),(int)((i+1)*Panel.mHeight/4));
				board.get(counter).setLocation(source);
				board.get(counter++).setDrawScreenLocation(maxBoardSize);
			}
			i=1;
			for(int j = 5; j>=0;j--){
				source = new Rect((int)(j*Panel.mWidth/6),(int)(i*Panel.mHeight/4),(int)((j+1)*Panel.mWidth/6),(int)((i+1)*Panel.mHeight/4));
				board.get(counter).setLocation(source);
				board.get(counter++).setDrawScreenLocation(maxBoardSize);
			}
			i=0;
			for(int j = 0; j<6;j++){
				source = new Rect((int)(j*Panel.mWidth/6),(int)(i*Panel.mHeight/4),(int)((j+1)*Panel.mWidth/6),(int)((i+1)*Panel.mHeight/4));
				board.get(counter).setLocation(source);
				board.get(counter++).setDrawScreenLocation(maxBoardSize);
			}
		//board.add(fakeBoard.get(23));board.add(fakeBoard.get(22));board.add(fakeBoard.get(21));board.add(fakeBoard.get(20));board.add(fakeBoard.get(19));board.add(fakeBoard.get(18));
		//board.add(fakeBoard.get(12));board.add(fakeBoard.get(13));board.add(fakeBoard.get(14));board.add(fakeBoard.get(15));board.add(fakeBoard.get(16));board.add(fakeBoard.get(17));
		//board.add(fakeBoard.get(11));board.add(fakeBoard.get(10));board.add(fakeBoard.get(9));board.add(fakeBoard.get(8));board.add(fakeBoard.get(7));board.add(fakeBoard.get(6));
		//board.add(fakeBoard.get(0));board.add(fakeBoard.get(1));board.add(fakeBoard.get(2));board.add(fakeBoard.get(3));board.add(fakeBoard.get(4));board.add(fakeBoard.get(5));

		/*board.get(0).setAction(Action.Nothing);
		board.get(1).setAction(Action.Beginning);
		board.get(4).setAction(Action.AnotherTurn);
		board.get(6).setAction(Action.Beginning);
		board.get(9).setAction(Action.SkipTurn);
		board.get(11).setAction(Action.PickGoBack3);
		board.get(13).setAction(Action.RollBack);
		board.get(15).setAction(Action.DrinkOrGoBack3);
		board.get(16).setAction(Action.LeaderGoBackToStart);
		board.get(19).setAction(Action.Roll6);
		board.get(22).setAction(Action.GoBack10);*/
	}
	private void swapBoard(int index1,int index2){
		Tile tile = board.get(index1);
		board.set(index1, board.get(index2));
		board.set(index2, tile);
		
	}
	public void update(Rect source){
		
	}
	public void doDraw(Canvas canvas,Rect source){
		canvas.drawBitmap(background, null, maxBoardSize, null);		
		for(Tile tile:board){
			if(tile.onBoard(source)){
				tile.doDraw(canvas);
			}
		}
		
	}
	public void doDraw(Canvas canvas,Player p){
		board.get(p.getTileLocation()).doBigDraw(canvas);
	}
	public void setPlayer(Player player){
		board.get(player.getTileLocation()).setPlayer(player);
	}
	public Action performAction(Player player){
		return board.get(player.getTileLocation()).getAction();
	}
	public Rect getMaxBoardSize() {
		return maxBoardSize;
	}
	public void setMaxBoardSize(Rect maxBoardSize) {
		this.maxBoardSize = maxBoardSize;
	}
	public int getNumTiles(){
		return board.size();
	}
	public Rect getTile(Player p){
		return board.get(p.getTileLocation()).getLocation();
	}
	public Bitmap getHugeBitmap(){
		return background;
	}
	public void shuffle(){
		Random random = new Random();
		for(int i =0; i <100;i++){
			int place1 = random.nextInt(boardsize-2)+1;
			int place2 = random.nextInt(boardsize-2)+1;
			swap(place1,place2);			
		}
	}
	private void swap(int place1,int place2){
		Tile tile1 = board.get(place1);
		Tile tile2 = board.get(place2);
		Rect location = tile1.getLocation();
		tile1.setLocation(tile2.getLocation());
		
		tile2.setLocation(location);
		board.set(place1, board.get(place2));
		board.set(place2, tile1);
		
		
	}
}
