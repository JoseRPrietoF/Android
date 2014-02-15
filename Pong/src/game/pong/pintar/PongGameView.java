package game.pong.pintar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class PongGameView extends SurfaceView  implements SurfaceHolder.Callback{
	 private PongGameThread paintThread;

	 public PongGameView(Context context) {
	  super(context);
	  getHolder().addCallback(this);
	 }
	 @Override
	 public void surfaceChanged(SurfaceHolder holder, int format,
	                    int width, int height) { }

	 @Override
	 public void surfaceCreated(SurfaceHolder holder) {
	  paintThread = new PongGameThread(getHolder(), this);
	  paintThread.setRunning(true);
	  paintThread.start();
	  
	 }

	 @Override
	 public void surfaceDestroyed(SurfaceHolder arg0) {
	  boolean retry = true;
	  paintThread.setRunning(false);
	  while (retry) {
	   try {
	    paintThread.join();
	    retry = false;
	   } catch (InterruptedException e) { }
	  }
	 }

	 @Override
	 public void onDraw(Canvas canvas) {
	  canvas.drawColor(Color.WHITE);
	 }
}
