package rad.boardgame;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class BoardGameActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
	    requestWindowFeature(Window.FEATURE_NO_TITLE);
	    //setRequestedOrientation(WindowM.SCREEN_ORIENTATION_PORTRAIT);

	    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
	                   WindowManager.LayoutParams.FLAG_FULLSCREEN); 
	    setContentView(new Panel(this));
	    
    }
}