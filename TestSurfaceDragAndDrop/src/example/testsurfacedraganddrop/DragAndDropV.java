package example.testsurfacedraganddrop;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


public class DragAndDropV extends SurfaceView implements SurfaceHolder.Callback{
	
	private DragThread thread;

	public DragAndDropV(Context context) {
		super(context);
		getHolder().addCallback(this); // Fa que empri aquesta classe com a manejador
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		thread = new DragThread(getHolder(), this);
		thread.setRunning(true);
		thread.start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		boolean retry = true;
		thread.setRunning(false);
		while(retry) {
			try {
				thread.join();
				retry = false;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
		
	}

	@Override
	protected void onDraw(Canvas canvas) {
		Paint p = new Paint();
		p.setColor(Color.BLACK);
		p.setAntiAlias(true);

		canvas.drawColor(Color.WHITE);
		canvas.drawCircle(200, 200, 100, p);
		canvas.drawRect(200, 500, 400, 700, p);
	}
	
	

}
