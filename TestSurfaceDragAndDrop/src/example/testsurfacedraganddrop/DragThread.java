package example.testsurfacedraganddrop;
import android.graphics.Canvas;
import android.view.SurfaceHolder;


public class DragThread extends Thread {
	
	private SurfaceHolder sh;
	private DragAndDropV view;
	private boolean run;
	
	public DragThread(SurfaceHolder sh, DragAndDropV view) {
		this.run = false;
		this.sh = sh;
		this.view = view;
	}

	public void setRunning(boolean run) {
		this.run = run;
	}
	
	@Override
	public void run() {
		Canvas canvas;
		while(run) {
			canvas = null;
			try {
				canvas = sh.lockCanvas(null);
				synchronized(sh) {
					 view.onDraw(canvas);
				}
		    } finally {
		    	if(canvas != null)
		    		sh.unlockCanvasAndPost(canvas); // Si falla, devolvemos el canvas
		    										// A un estado estable
		    }
		}
	}

}
