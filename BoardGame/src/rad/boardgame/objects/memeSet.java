package rad.boardgame.objects;

import android.content.res.XmlResourceParser;
import android.graphics.Bitmap;

public class memeSet {
	public memeSet(Bitmap bit,XmlResourceParser xmlResource){
		bitmap = bit;
		xmlResourceParser = xmlResource;
	}
	private Bitmap bitmap;
	private XmlResourceParser xmlResourceParser;
	public Bitmap getBitmap() {
		return bitmap;
	}
	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}
	public XmlResourceParser getXmlResourceParser() {
		return xmlResourceParser;
	}
	public void setXmlResourceParser(XmlResourceParser xmlResourceParser) {
		this.xmlResourceParser = xmlResourceParser;
	}
	
}
