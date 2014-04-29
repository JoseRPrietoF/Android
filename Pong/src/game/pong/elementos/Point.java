package game.pong.elementos;

import android.graphics.PointF;

public class Point extends PointF {

	private String lado = "";
	
	public String getLado(){
		return lado;
	}
	
	public void setLado(String s){
		lado = s;
	}
	
}
