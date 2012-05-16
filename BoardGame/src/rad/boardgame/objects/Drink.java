package rad.boardgame.objects;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class Drink implements picker {
	private Paint paint;
	Rect rect,draw;
	private String message;
	private Object object;
	private boolean on = true;
	public Drink(Bitmap[] bitmaps,String message,Object object){
		paint = new Paint();
		paint.setColor(Color.RED);
		paint.setTextSize(40);
		this.message = message;
	}
	public void doDraw(Canvas canvas){
	}
	@Override
	public void setRectangle(Rect rect) {
		// TODO Auto-generated method stub
		this.rect = rect;		
		int width = (rect.right-rect.left)/2;
		int height = (rect.bottom-rect.top)/2;
		draw = new Rect(rect.left+width-100,rect.top+height-20,0,0);
	}
	@Override
	public Rect getRectangle() {
		// TODO Auto-generated method stub
		return rect;
	}
	@Override
	public void Draw(Canvas canvas) {
		canvas.drawText(message, draw.left,draw.top, paint);		
		
	}
	public Object getObject() {
		return object;
	}
	public void setObject(Object object) {
		this.object = object;
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
