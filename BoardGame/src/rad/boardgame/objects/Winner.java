package rad.boardgame.objects;

import java.util.Random;

import rad.boardgame.Panel;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class Winner {
	private Bitmap bitmap;
	private int roll;
	private Paint paint;
	private String name;
	public Winner(Bitmap[] bitmaps){
		paint = new Paint();
		paint.setColor(Color.WHITE);
	}
	public void doDraw(Canvas canvas){
		canvas.drawText("Player "+name+" has Won.  Click to play again.", Panel.mWidth/2-100, Panel.mHeight/2, paint);
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	

}
