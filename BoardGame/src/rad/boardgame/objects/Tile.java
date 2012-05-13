package rad.boardgame.objects;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Tile {
	private Bitmap bitmap;
	private Bitmap background;
	private Rect location;
	private Rect drawLocation;
	private Rect drawScreenLocation;
	private Rect source;
	private int width,height;
	private static int numCols = 2,numRows = 2;
	private static final int PADDING = 10,PADDINGBOTTOM = 30,PADDINGRIGHT = 100;
	public static final int realWidth = 2700;
	private Action action;
	public Tile(Bitmap bitmap,int x,int y,int width,int height,String action,String special,String topText,String bottomText){
		this.bitmap = bitmap;		
		double ratio = (double)bitmap.getWidth()/(double)realWidth;
		this.source = new Rect((int)(x*ratio),(int)(y*ratio),(int)((x+width)*ratio),(int)((y+height)*ratio));
		this.action = Action.valueOf(action);
	}
	public void update(Rect source){
		
	}
	public void doDraw(Canvas canvas){
		canvas.drawBitmap(bitmap, source, drawLocation, null);	
	}
	public void doBigDraw(Canvas canvas){
		canvas.drawBitmap(bitmap, source, drawScreenLocation, null);	
	}
	public void setPlayer(Player player){
		int playerNum = player.getPlayerNum();
		int col = 0,row = 0;
		switch(playerNum){
		case 1:
			col =1;
			break;
		case 2:
			row =1;
			break;
		case 3:
			col =1;
			row = 1;
			break;
		}
		player.setLocation(new Rect(width/numCols*col+location.left,
				height/numRows*row+location.top,
				width/numCols*(col+1)+location.left,
				height/numRows*(row+1)+location.top));
		
	}
	public Action happensOnTile(){
		return action;
	}
	public boolean onBoard(Rect board){
		return true;
	}
	public Action getAction() {
		return action;
	}
	public void setAction(Action action) {
		this.action = action;
	}
	public Rect getLocation() {
		return location;
	}
	public void setLocation(Rect location) {
		this.location = location;
		this.width = location.width();
		this.height = location.height();
		
		drawLocation = setDrawLocation(new Rect(location.left+PADDING,location.top+PADDING,
								location.right-PADDING,location.bottom-PADDINGBOTTOM),source);
	}
	static public Rect setDrawLocation(Rect location,Rect source){
		int newWidth,newHeight;
		float widthPer,heightPer;
		
		if (source.width() > source.height())
	    {
	        newWidth = location.width();
	        widthPer = (float)location.width() / source.width();
	        newHeight = (int)(source.height() * widthPer);
	        if(newHeight > location.height()){
		        newHeight = location.height();
		        heightPer = (float)location.height() / source.height();
		        newWidth =(int)(source.width() * heightPer);
	        }
	    }
	    else
	    {
	        newHeight = location.height();
	        heightPer = (float)location.height() / source.height();
	        newWidth =(int)(source.width() * heightPer);
	        if(newWidth > location.width()){
		        newWidth = location.width();
		        widthPer = (float)location.width() / source.width();
		        newHeight = (int)(source.height() * widthPer);
	        }
	    }
		return new Rect(location.centerX()-newWidth/2,
								location.centerY()-newHeight/2,
								location.centerX()+newWidth/2,
								location.centerY()+newHeight/2);
	}
	public Rect getSource() {
		return source;
	}
	public void setSource(Rect source) {
		this.source = source;
	}
	public Rect getDrawScreenLocation() {
		return drawScreenLocation;
	}
	public void setDrawScreenLocation(Rect location) {
		drawScreenLocation = setDrawLocation(new Rect(location.left+PADDING,location.top+PADDING,
				location.right-PADDING-PADDINGRIGHT,location.bottom-PADDING),source);
	}
	
	
}
