package rad.boardgame;

import rad.boardgame.objects.bitmapLocations;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.MotionEvent;

public class Menu {

	private Bitmap bitmap;
	private Rect screen,start,settings,quit,text;
	private Rect startScreen,settingsScreen,quitScreen,textScreen;
	private Paint paint;
	private MenuOptions menuOption;
	
	public Menu(Bitmap[] bitmaps){
		bitmap = bitmaps[bitmapLocations.MENU.index];
		screen= new Rect(0,0,(int)Panel.mWidth,(int)Panel.mHeight);
		
		
		start= new Rect(22,282,294,529);
		settings= new Rect(324,282,635,529);
		quit = new Rect(665,458,778,580);
		text = new Rect(70,30,740,120);
		
		textScreen = new Rect(screen.centerX()-text.width()/2,20,
				screen.centerX()+text.width()/2,text.height()+20);
		startScreen = new Rect(30,screen.bottom-20-start.height(),
							30+start.width(),screen.bottom-20);
		settingsScreen = new Rect(60+start.width(),screen.bottom-20-settings.height(),
				60+start.width()+settings.width(),screen.bottom-20);
		quitScreen = new Rect(90+start.width()+settings.width(),screen.bottom-20-quit.height(),
				60+start.width()+settings.width()+quit.width(),screen.bottom-20);
		menuOption = MenuOptions.none;
	}
	public void doDraw(Canvas canvas){
		canvas.drawBitmap(bitmap, text,textScreen , null);
		canvas.drawBitmap(bitmap, settings,settingsScreen , null);
		//canvas.drawBitmap(bitmap, quit,quitScreen , null);
		canvas.drawBitmap(bitmap, start,startScreen , null);
	}
	public void onTouch(MotionEvent event){
		if(startScreen.contains((int)event.getX(),(int)event.getY())){
			menuOption = MenuOptions.play;
		}
		else if(settingsScreen.contains((int)event.getX(),(int)event.getY())){
			menuOption = MenuOptions.settings;
		}
		else if(quitScreen.contains((int)event.getX(),(int)event.getY())){
			menuOption = MenuOptions.quit;
		}
	}
	public MenuOptions getMenuOption() {
		return menuOption;
	}
	public void setMenuOption(MenuOptions menuOption) {
		this.menuOption = menuOption;
	}
	
}
