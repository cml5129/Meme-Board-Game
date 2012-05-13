package rad.boardgame;

import rad.boardgame.objects.bitmapLocations;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.MotionEvent;

public class Settings {

	private Bitmap bitmap;
	private Rect screen,players,shuffleRect,back,backScreen;
	private Paint paint;
	private int numPlayers = 4;
	private boolean shuffle = false;
	public Settings(Bitmap[] bitmaps){
		bitmap = bitmaps[bitmapLocations.MENU.index];
		screen= new Rect(0,0,(int)Panel.mWidth,(int)Panel.mHeight);
		paint = new Paint();
		paint.setColor(Color.WHITE);
		paint.setTextSize(60);
		players = new Rect(0,100,screen.width(),160);
		shuffleRect = new Rect(0,170,screen.width(),230);
		backScreen = new Rect(screen.right-200,screen.bottom-100,screen.right,screen.bottom);
	}
	public void doDraw(Canvas canvas){
		canvas.drawText("Settings", screen.centerX()-50, 50, paint);
		canvas.drawText("Number of Players:", 50, 120, paint);
		canvas.drawText(String.valueOf(numPlayers), screen.width()-60, 120, paint);
		canvas.drawText("Shuffle:", 50, 200, paint);
		canvas.drawText(shuffle?"true":"false", screen.width()-150, 200, paint);
		canvas.drawText("back", screen.right-200, screen.bottom-50, paint);
		
	}
	public boolean onTouch(MotionEvent event){
		int x = (int)event.getX();
		int y = (int)event.getY();
		if(players.contains((int)event.getX(),(int)event.getY())){
			numPlayers++;
			if(numPlayers > 4){
				numPlayers = 2;
			}
		}
		else if(shuffleRect.contains((int)event.getX(),(int)event.getY())){
			shuffle = !shuffle;
		}
		else if(backScreen.contains((int)event.getX(),(int)event.getY())){
			return true;
		}
		return false;
	}
	public int getNumPlayers() {
		return numPlayers;
	}
	public void setNumPlayers(int numPlayers) {
		this.numPlayers = numPlayers;
	}
	public boolean isShuffle() {
		return shuffle;
	}
	public void setShuffle(boolean shuffle) {
		this.shuffle = shuffle;
	}	
	
}
