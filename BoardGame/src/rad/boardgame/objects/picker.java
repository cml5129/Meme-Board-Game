package rad.boardgame.objects;

import android.graphics.Canvas;
import android.graphics.Rect;

public interface picker {
	void setRectangle(Rect rect);
	Rect getRectangle();
	void Draw(Canvas canvas);
	void turnOff();
	void turnOn();
}
