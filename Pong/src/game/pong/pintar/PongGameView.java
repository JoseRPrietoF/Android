package game.pong.pintar;

import game.pong.elementos.Bola;
import game.pong.elementos.BolaMoveThread;
import game.pong.elementos.Coordenada;
import game.pong.elementos.Elemento;
import game.pong.elementos.Raqueta;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class PongGameView extends SurfaceView implements SurfaceHolder.Callback {

	private PongGameThread paintThread;
	private BolaMoveThread bolaThread;

	private Elemento raquetaIzda;
	private Elemento raquetaDcha;
	private Elemento bola;
	private Elemento elementoActivo = null;
	private int origenY;

	public PongGameView(Context context) {
		super(context);
		getHolder().addCallback(this);
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		raquetaIzda = new Raqueta(new Coordenada(50, getHeight() / 2 - 50), 20,
				100);
		raquetaDcha = new Raqueta(new Coordenada(getWidth() - 70,
				getHeight() / 2 - 50), 20, 100);
		bola = new Bola(
				new Coordenada(getWidth() / 2 - 5, getHeight() / 2 - 5), 10, 10);

		paintThread = new PongGameThread(getHolder(), this);
		paintThread.setRunning(true);
		paintThread.start();

		// Empezamos a mover la bola
		bolaThread = new BolaMoveThread((Bola) bola, (Raqueta) raquetaIzda,
				(Raqueta) raquetaDcha, new Rect(0, 0, getWidth(), getHeight()));
		bolaThread.setRunning(true);
		bolaThread.start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		boolean retry = true;
		paintThread.setRunning(false);
		bolaThread.setRunning(false);
		while (retry) {
			try {
				paintThread.join();
				bolaThread.join();
				retry = false;
			} catch (InterruptedException e) {
			}
		}
	}

	@Override
	public void onDraw(Canvas canvas) {
		Paint paint = new Paint();
		paint.setColor(Color.WHITE);

		canvas.drawColor(Color.BLACK);
		canvas.drawRect(raquetaIzda.getRectElemento(), paint);
		canvas.drawRect(raquetaDcha.getRectElemento(), paint);
		canvas.drawRect(bola.getRectElemento(), paint);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int x = (int) event.getX();
		int y = (int) event.getY();

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:// hemos pulsado
			if (raquetaIzda.getRectElemento().contains(x, y)) { // Contains nos
																// ahorra
																// condicionales,
				// verifica si la X y la Y estan dentro del rectangulo realmente
				elementoActivo = raquetaIzda;
				origenY = y;
				break;
			}
			if (raquetaDcha.getRectElemento().contains(x, y)) {
				elementoActivo = raquetaDcha;
				origenY = y;
				break;
			}
			break;
		case MotionEvent.ACTION_MOVE:// hemos arrastrado
			// Solo queremos mover verticalmente - Modificaremos coord. Y
			// Restar = ir hacia arriba / Sumar = hacia abajo
			if (elementoActivo != null) {
				// Calcularemos la diferencia de posiciones para mandarselo a
				// move()
				Raqueta r = (Raqueta) elementoActivo; // Cast para usar move()
				// Le pasamos a canMove la posicion donde queremos mover
				// Y la pantalla donde nos queremos mover en formacto rectangulo
				if (r.canMove(0, y - origenY, new Rect(0, 0, getWidth(),
						getHeight()))) {
					r.move(0, y - origenY); // 0 para no mover eje X
				}

			}
			origenY = y;
			break;
		case MotionEvent.ACTION_UP:// hemos levantado
			elementoActivo = null;
			break;
		}

		return true;
	}

}
