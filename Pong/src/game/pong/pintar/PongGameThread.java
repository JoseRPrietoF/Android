package game.pong.pintar;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class PongGameThread extends Thread {

	private SurfaceHolder sh;
	private PongGameView view;
	private boolean run;

	public PongGameThread(SurfaceHolder sh, PongGameView view) {
		this.sh = sh;
		this.view = view;
		run = false;
	}

	public void setRunning(boolean run) {
		this.run = run;
	}

	@SuppressLint("WrongCall")
	public void run() {
		Canvas canvas;
		while (run) {
			canvas = null;
			try {
				canvas = sh.lockCanvas(null);
				synchronized (sh) {
					view.onDraw(canvas);
				}
			} finally {
				if (canvas != null)
					sh.unlockCanvasAndPost(canvas);
			}
		}
	}
}
