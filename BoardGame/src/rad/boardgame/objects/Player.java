package rad.boardgame.objects;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class Player implements picker{
	private int tileLocation;
	private int player;
	private Bitmap bitmap;
	private static final Rect playerRect = new Rect(0,0,250,250);
	static private int playernumber = 0;
	private String name;
	private Rect location;
	private boolean skipTurn = false;
	private Rect pickRectangle,drawPickRectangle;
	private boolean on = true;
	private Paint paint;
	public Player(Bitmap[] bitmaps, int location){
		tileLocation = location;
		bitmap = bitmaps[bitmapLocations.PLAYER.index];
		player = playernumber;
		name = String.valueOf(playernumber+1);
		playernumber++;
		paint = new Paint();
		paint.setColor(Color.RED);
	}
	public void doDraw(Canvas canvas){
		//		Paint p = new Paint();

	//	p.setColor(Color.RED);
		//p.setStyle(Paint.Style.FILL); 
		//canvas.drawRect(new Rect(0,0,100,100),p);
		
		canvas.drawBitmap(bitmap, null, location, paint);	
	}
	public boolean canGo(){
		if(skipTurn){
			skipTurn = false;
			return false;
		}
		return true;
	}
	public void skipTurn(){
		skipTurn = true;
	}
	public int getTileLocation() {
		return tileLocation;
	}
	public void setTileLocation(int tileLocation) {
		if(tileLocation < 0 ){
			tileLocation = 0;
		}
		this.tileLocation = tileLocation;
	}
	public static int numPlayers(){
		return playernumber+1;
	}
	public void setLocation(Rect location){
		this.location = location;
	}
	public int getPlayerNum(){
		return player;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public static int getPlayernumber() {
		return playernumber;
	}
	public static void setPlayernumber(int playernumber) {
		Player.playernumber = playernumber;
	}
	public void reset(){
		skipTurn = false;
		tileLocation = 0;
	}
	@Override
	public void setRectangle(Rect rect) {
		pickRectangle = rect;
		int width = (pickRectangle.right-pickRectangle.left)/2;
		int height = (pickRectangle.bottom-pickRectangle.top)/2;
		drawPickRectangle = new Rect(rect.left+width-playerRect.right/2,rect.top+height-playerRect.bottom/2,rect.left+width+playerRect.right/2,rect.top+height+playerRect.bottom/2);
	}
	@Override
	public Rect getRectangle() {		
		return on ? pickRectangle: null;
	}
	@Override
	public void Draw(Canvas canvas) {
		// TODO Auto-generated method stub
		if(on){
			canvas.drawBitmap(bitmap,null,drawPickRectangle, null);
		}
		
	}
	@Override
	public void turnOff() {
		// TODO Auto-generated method stub
		on = false;
	}
	@Override
	public void turnOn() {
		// TODO Auto-generated method stub
		on = true;
	}
}
