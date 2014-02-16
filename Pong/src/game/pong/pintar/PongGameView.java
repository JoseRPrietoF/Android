package game.pong.pintar;

import game.pong.Marcador;
import game.pong.elementos.Bola;
import game.pong.elementos.BolaMoveThread;
import game.pong.elementos.Coordenada;
import game.pong.elementos.Elemento;
import game.pong.elementos.Raqueta;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class PongGameView extends SurfaceView implements SurfaceHolder.Callback {

	public static final int UMBRAL_TACTIL = 70;
	// Constante para ampliar los movimientos con la parte tactil

	private PongGameThread paintThread;
	private BolaMoveThread bolaThread;

	private Elemento raquetaIzda;
	private Elemento raquetaDcha;
	private Elemento bola;
	private Elemento elementoActivo = null;
	private int origenY;

	// Marcador
	private Marcador marcador;
	private Paint paint; // SE cargara aqui la fuente nueva
	// Ya que si la cargamos cada vez, puede consumir muchisimos recursos

	public PongGameView(Context context,  int puntosIzda, int puntosDcha) {
		super(context);
		getHolder().addCallback(this); // Para usar SurfaceView
		// Creamos marcador, posiblemente con puntos de partida sin acabar
		this.marcador = new Marcador(puntosIzda, puntosDcha);
		// Cargaremos fuente, asignaremos color..
		paint = new Paint();
		paint.setColor(Color.WHITE);
		paint.setTextAlign(Align.CENTER);
		paint.setTypeface(Typeface.createFromAsset(this.getContext()
				.getAssets(), "fonts/Frijole-Regular.ttf"));
		// SE ha cargado la fuente
		paint.setTextSize(80);
		paint.setAntiAlias(true);
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
				(Raqueta) raquetaDcha, new Rect(0, 0, getWidth(), getHeight()),
				this.getContext(), marcador);
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
		canvas.drawColor(Color.BLACK);
		drawCenterLine(canvas);
		drawMarcador(canvas);
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
			Rect parteTactil; // Copiaremos el elemento raqueta
								// y añadiremos un extra de tamaño para que sea
								// mas comodo el control
			parteTactil = new Rect(raquetaIzda.getRectElemento());
			parteTactil.set(parteTactil.left - UMBRAL_TACTIL, parteTactil.top,
					parteTactil.right + UMBRAL_TACTIL, parteTactil.bottom);
			// Se compara con la copia de la raqueta
			if (parteTactil.contains(x, y)) { // Contains nos
												// ahorra
												// condicionales,
				// verifica si la X y la Y estan dentro del rectangulo realmente
				elementoActivo = raquetaIzda;
				origenY = y;
				break;
			}
			// SE hace lo mismo que con la otra raqueta
			parteTactil = new Rect(raquetaDcha.getRectElemento());
			parteTactil.set(parteTactil.left - UMBRAL_TACTIL, parteTactil.top,
					parteTactil.right + UMBRAL_TACTIL, parteTactil.bottom);
			if (parteTactil.contains(x, y)) {
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

	// Método para crear linea central
	private void drawCenterLine(Canvas canvas) {
		int wLine = 4; // Ancho de banda de cada una de las lineas
		int hLine = 15; // Altura de cada una de las lineas
		int espacio = 10; // Espacio entre cada una de las lineas
		int ini = espacio / 2; // Define donde comenzaremos a pintar

		// Bucle que recorre toda la pantalla hasta llegar abajo
		// Pintamos cada uno de los tramos de manera discontinua
		for (int i = 0; i < this.getHeight() / (hLine + espacio); i++) {
			canvas.drawRect(this.getWidth() / 2 - wLine / 2, ini,
					this.getWidth() / 2 + wLine / 2, ini + hLine, paint);
			ini += hLine + espacio;
		}
	}

	// Método para dibujar marcador
	private void drawMarcador(Canvas canvas) {
		canvas.drawText(Integer.toString(marcador.getPuntosIzda()),
				getWidth() / 2 - 80, 80, paint);
		canvas.drawText(Integer.toString(marcador.getPuntosDcha()),
				getWidth() / 2 + 80, 80, paint);
		// Pintamos los puntos de cada lado 80px de distancia de la linea divisoria
	}

	public Marcador getMarcador() {
		return marcador;
	}
	
	
}
