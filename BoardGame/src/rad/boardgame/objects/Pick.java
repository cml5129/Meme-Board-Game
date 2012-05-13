package rad.boardgame.objects;


import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.MotionEvent;

public class Pick {
	private picker picker;
	private picker[][] objects;
	public Pick(int rows,int cols,Rect screen, picker[][] objects){
		int colsSize = (screen.right-screen.left)/cols;
		int rowSize = (screen.bottom-screen.top)/rows;
		for(int i = 0; i < rows;i++){
			for(int j = 0; j < cols;j++){
				objects[i][j].setRectangle(new Rect(j*colsSize,i*rowSize,(j+1)*colsSize,(i+1)*rowSize));
			}
		}
		this.objects = objects;
	}
	public void doDraw(Canvas canvas){
		for(int i = 0; i < objects.length;i++){
			for(int j = 0; j < objects[i].length;j++){
				objects[i][j].Draw(canvas);
			}
		}				
	}
	public void onTouch(MotionEvent event){
		for(int i = 0; i < objects.length;i++){
			for(int j = 0; j < objects[i].length;j++){
				if(objects[i][j].getRectangle().contains((int)Math.floor(event.getX()), (int)Math.floor(event.getY()))){
					picker = objects[i][j]; 
					return;
				}
			}
		}			
	}
	public picker getPicker() {
		return picker;
	}
	public void setPicker(picker picker) {
		this.picker = picker;
	}
	

}
