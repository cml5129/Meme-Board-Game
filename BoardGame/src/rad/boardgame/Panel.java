package rad.boardgame;

import java.util.ArrayList;
import java.util.List;

import rad.boardgame.objects.Dice;
import rad.boardgame.objects.GameMaster;
import rad.boardgame.objects.Player;
import rad.boardgame.objects.Winner;
import rad.boardgame.objects.bitmapLocations;
import rad.boardgame.objects.memeSet;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;


//panel class
public class Panel extends SurfaceView implements SurfaceHolder.Callback{
	public enum GameState
	{
		load,loading,start,settings,running,end;
	}
	private static ViewThread mThread;
	private static Context context;
	public static float mWidth;
	public static float mHeight;
	private static Paint mPaint = new Paint();
	public static float points = 0;
	private static Boolean paused = false;
	private static GameState gameState = GameState.load;
	private static float sinceEnd = 0;
	private static GameMaster gameMaster;
	private static Winner winner;
	private static Menu menu;
	private static Load load;
	private static Settings settings;
	private static boolean quit = false;
	private static Bitmap[] bitmaps= new Bitmap[5];
	 
	public Panel(Context context) {
	    super(context);
	    getHolder().addCallback(this);
	    if(mThread == null){
		    mThread = new ViewThread(this);
	    	ViewThread.mStartTime = System.currentTimeMillis();
	    	WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
	    	Display display = wm.getDefaultDisplay();
	    	mWidth = display.getWidth();
	    	mHeight = display.getHeight();
	    	loadingScreen();
	    }
	}
	public void load(){

	    getImages();	    	
    	menu = new Menu(bitmaps);
    	settings = new Settings(bitmaps);
    	List<memeSet> memes = new ArrayList<memeSet>();
    	memes.add(new memeSet(bitmaps[bitmapLocations.MEMES.index],getResources().getXml(R.xml.memes)));
    	gameMaster = new GameMaster(bitmaps,memes);
    	winner = new Winner(bitmaps);
    	gameState = GameState.start;
	}
	public void loadingScreen(){
		bitmaps[2] = BitmapFactory.decodeResource(getResources(), R.drawable.menu);
		load = new Load(bitmaps);
		
	}
	public void getImages()
	{		
		bitmaps[0] = BitmapFactory.decodeResource(getResources(), R.drawable.empty);
		bitmaps[1] = BitmapFactory.decodeResource(getResources(), R.drawable.redditalien);
		getMems(1,R.drawable.memes,3);
		getMems(1,R.drawable.dice,4);
	}
	public void getMems(int sampleSize,int id,int index){
		// First decode with inJustDecodeBounds=true to check dimensions
	    final BitmapFactory.Options options = new BitmapFactory.Options();

	    // Calculate inSampleSize
	    options.inSampleSize = sampleSize;

	    try{
	    	System.gc();
	    	bitmaps[index] = BitmapFactory.decodeResource(getResources(), id, options);
	    }catch(OutOfMemoryError e){
			getMems(sampleSize*2,id,index);
		}
	}
	public void doDraw(long elapsed,Canvas canvas) 
	{
		canvas.drawColor(Color.BLACK);
		switch(gameState){
		case loading:
			load();
			break;
		case load:
			load.doDraw(canvas);
			gameState = GameState.loading;
			break;
		case start:
			menu.doDraw(canvas);
			break;
		case settings:
			settings.doDraw(canvas);
			break;
		case end:
			winner.doDraw(canvas);
			return;
		default:
			gameMaster.doDraw(canvas);			
		}
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) 
	{
		switch(gameState)
		{
		case start:
			menu.onTouch(event);
			
			switch(menu.getMenuOption()){
			case play:
				gameState = gameState.running;
				if(settings.isShuffle()){
					gameMaster.shuffle();
				}
				gameMaster.setNumPlayers(settings.getNumPlayers());
				break;
			case settings:
				gameState = gameState.settings;
				break;
			}
			break;
		case settings:
			if(settings.onTouch(event)){
				menu.setMenuOption(MenuOptions.none);
				gameState = GameState.start;
			}
			break;
		case end:
			switchFromEndToStart();
			break;
		case running:
			gameMaster.onTouch(event);
			break;
		}
	    return super.onTouchEvent(event);
	}
	public void animate(long elapsedTime) 
	{
		switch(gameState)
		{
		case running:
			if(gameMaster.isWinner() != null){
				winner.setName(gameMaster.isWinner());
				end();
			}
			gameMaster.animate(elapsedTime);
		}
	}
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) 
	{
		// TODO Auto-generated method stub
	    mWidth = width;
	    mHeight = height;
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) 
	{
		if (!mThread.isAlive()) 
		{
	        mThread = new ViewThread(this);
	        mThread.setRunning(true);
	        mThread.start();
	    }
		
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) 
	{
		if (mThread.isAlive()) {
	        mThread.setRunning(false);
	    }
		
	}
	@Override
	public void onWindowFocusChanged(boolean hasWindowFocus) {
		// TODO Auto-generated method stub
		//add pause timer
		//gameState = GameState.start;
		super.onWindowFocusChanged(hasWindowFocus);
	}
	public static void end()
	{
		gameState = GameState.end;
	}
	private void switchFromEndToStart()
	{
		gameState = GameState.start;
		gameMaster.Reset();
    	
	}
	public boolean isQuit() {
		return quit;
	}
	public static void setQuit(boolean quit) {
		Panel.quit = quit;
	}
	
	
}
