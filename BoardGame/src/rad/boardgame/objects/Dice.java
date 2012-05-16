package rad.boardgame.objects;

import java.util.Random;

import rad.boardgame.Panel;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class Dice {
	private Bitmap bitmap;
	private int roll;
	private Paint paint;
	private Boolean rolled;
	private String name;
	private Rect board;
	private Rect[] playerLocations = new Rect[4];
	private Rect[] locations = new Rect[6];
	public Dice(Bitmap[] bitmaps){
		bitmap = bitmaps[bitmapLocations.DICE.index];
		paint = new Paint();
		paint.setColor(Color.RED);
		paint.setTextSize(20);
		for(int i = 0; i < 6;i++){
			locations[i] = new Rect(i*150,0,(i+1)*150,150);
		}
		for(int i = 0; i < 4;i++){
			playerLocations[i] = new Rect((int)Panel.mWidth-80,50+75*i,(int)Panel.mWidth-40,50+50*(i+1)+25*i);
		}
		board = new Rect((int)Panel.mWidth-65,100,(int)Panel.mWidth-15,150);
	}
	public void doDraw(Canvas canvas){
		canvas.drawBitmap(bitmap,locations[roll-1],playerLocations[0],null);
		canvas.drawText("Player "+name, Panel.mWidth-100, 35, paint);
	}
	public void doDrawBoard(Canvas canvas){
		canvas.drawBitmap(bitmap,locations[roll-1],board,null);
	}
	public void doDraw(Canvas canvas,int player){
		canvas.drawBitmap(bitmap,locations[roll-1],playerLocations[player],null);
		canvas.drawText("Player "+name, Panel.mWidth-100, 40+75*player, paint);
	}
	public void roll(){
		Random random = new Random();
		roll = random.nextInt(6)+1;
	}
	public int getRoll() {
		return roll;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Boolean getRolled() {
		return rolled;
	}
	public void setRolled(Boolean rolled) {
		this.rolled = rolled;
	}
	
	

}
