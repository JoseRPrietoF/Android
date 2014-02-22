package game.pong.elementos;

import android.graphics.PointF;

public class Pointer extends PointF {
	
	private String name = "";
	
	public void setName(String x){
		name = x;
	}
	
	public String toString(){
		return name;
	}

}
