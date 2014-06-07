package game.pong.pintar;

import opciones.OpcionesSingleton;
import game.pong.Marcador;
import game.pong.elementos.Bola;
import game.pong.elementos.BolaMoveThread;
import game.pong.elementos.Coordenada;
import game.pong.elementos.Elemento;
import game.pong.elementos.Point;
import game.pong.elementos.Raqueta;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class PongGameView extends SurfaceView implements SurfaceHolder.Callback {

	public static final int UMBRAL_TACTIL = 140;
	// Constante para ampliar los movimientos con la parte tactil

	private PongGameThread paintThread;
	private BolaMoveThread bolaThread;
	private OpcionesSingleton mOpcionesSingleton;

	private Elemento raquetaIzda;
	private Elemento raquetaDcha;
	private Elemento bola;
	private int mAltoRaqueta = 100;
	//private int origenY;
	private SparseArray<Point> mActivePointers;

	// Marcador
	private Marcador marcador;
	private Paint paintBall; // SE cargara aqui la fuente nueva
	private Paint paintRaqueta; 
	// Ya que si la cargamos cada vez, puede consumir muchisimos recursos

	public PongGameView(Context context,  int puntosIzda, int puntosDcha) {
		super(context);
		getHolder().addCallback(this); // Para usar SurfaceView
		// Creamos marcador, posiblemente con puntos de partida sin acabar
		mOpcionesSingleton = OpcionesSingleton.getInstance(context);
		mActivePointers = new SparseArray<Point>();
		this.marcador = new Marcador(puntosIzda, puntosDcha);
		// Cargaremos fuente, asignaremos color..
		paintBall = new Paint();
		getcolorRaquetas();
		paintBall.setColor(Color.WHITE);
		paintBall.setTextAlign(Align.CENTER);
		paintBall.setTypeface(Typeface.createFromAsset(this.getContext()
				.getAssets(), "fonts/Frijole-Regular.ttf"));
		// SE ha cargado la fuente
		paintBall.setTextSize(80);
		paintBall.setAntiAlias(true);
		mAltoRaqueta = mOpcionesSingleton.getTamanyRaquetes();
	}
	
	private void getcolorRaquetas(){
		paintRaqueta = new Paint();
		switch(mOpcionesSingleton.getColorRaquetas()){
		case "Cyan":
			paintRaqueta.setColor(Color.CYAN);
			break;
		case "Blau":
			paintRaqueta.setColor(Color.BLUE);
			break;
		case "Vermell":
			paintRaqueta.setColor(Color.RED);
			break;
		case "Groc":
			paintRaqueta.setColor(Color.YELLOW);
			break;
		case "Blanc":
			paintRaqueta.setColor(Color.WHITE);
			break;
		case "Rosa":
			paintRaqueta.setColor(Color.MAGENTA);
			break;
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		raquetaIzda = new Raqueta(new Coordenada(50, getHeight() / 2 - 50), 20,
				mAltoRaqueta);
		raquetaDcha = new Raqueta(new Coordenada(getWidth() - 70,
				getHeight() / 2 - 50), 20, mAltoRaqueta);
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
		canvas.drawRect(raquetaIzda.getRectElemento(), paintRaqueta);
		canvas.drawRect(raquetaDcha.getRectElemento(), paintRaqueta);
		canvas.drawRect(bola.getRectElemento(), paintBall);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// get pointer index from the event object
	    int pointerIndex = event.getActionIndex();

	    // get pointer ID
	    int pointerId = event.getPointerId(pointerIndex);

	    // get masked (not specific to a pointer) action
	    int maskedAction = event.getActionMasked();

		switch (maskedAction) {
	    case MotionEvent.ACTION_DOWN:
		case MotionEvent.ACTION_POINTER_DOWN:// hemos pulsado
			// 	new pointer
			Point f = new Point();
		    f.x = event.getX(pointerIndex);
		    f.y = event.getY(pointerIndex);
		    // ------
			Rect parteTactil; // Copiaremos el elemento raqueta
								// y añadiremos un extra de tamaño para que sea
								// mas comodo el control
			parteTactil = new Rect(raquetaIzda.getRectElemento());
			parteTactil.set(parteTactil.left - UMBRAL_TACTIL, parteTactil.top,
					parteTactil.right + UMBRAL_TACTIL, parteTactil.bottom);
			// Se compara con la copia de la raqueta
			if (parteTactil.contains((int)f.x, (int)f.y)) { // Contains nos
												// ahorra
												// condicionales,
				// verifica si la X y la Y estan dentro del rectangulo realmente
				//elementoActivo = raquetaIzda;
				f.setLado("I");
				mActivePointers.put(pointerId, f);
				raquetaIzda.origenYRelativo = (int)f.y;
				break;
			}
			// SE hace lo mismo que con la otra raqueta
			parteTactil = new Rect(raquetaDcha.getRectElemento());
			parteTactil.set(parteTactil.left - UMBRAL_TACTIL, parteTactil.top,
					parteTactil.right + UMBRAL_TACTIL, parteTactil.bottom);
			if (parteTactil.contains((int)f.x, (int)f.y)) {
				//elementoActivo = raquetaDcha;
				f.setLado("D");
				mActivePointers.put(pointerId, f);
				raquetaDcha.origenYRelativo = (int)f.y;
				break;
			}
			break;
		case MotionEvent.ACTION_MOVE:// hemos arrastrado
			// Solo queremos mover verticalmente - Modificaremos coord. Y
			// Restar = ir hacia arriba / Sumar = hacia abajo
			for (int size = event.getPointerCount(), i = 0; i < size; i++) {
				Point point = mActivePointers.get(event.getPointerId(i));
				if (point != null) {
					Raqueta r = null;
					// Calcularemos la diferencia de posiciones para mandarselo a move()
					if (point.getLado().equals("D"))
						r = (Raqueta) raquetaDcha; // Cast para usar
					else if (point.getLado().equals("I")) r = (Raqueta) raquetaIzda;
					point.x = event.getX(i);
			        point.y = event.getY(i);
															// move()
					// Le pasamos a canMove la posicion donde queremos mover
					// Y la pantalla donde nos queremos mover en formacto
					// rectangulo
					if (r.canMove(0, (int)point.y - r.origenYRelativo, new Rect(0, 0, getWidth(),
							getHeight()))) {
						r.move(0, (int)point.y - r.origenYRelativo); // 0 para no mover eje X

					}
					r.origenYRelativo = (int)point.y;
				}
				
			}
			break;
		case MotionEvent.ACTION_UP:// hemos levantado
			mActivePointers.remove(pointerId);
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
					this.getWidth() / 2 + wLine / 2, ini + hLine, paintBall);
			ini += hLine + espacio;
		}
	}

	// Método para dibujar marcador
	private void drawMarcador(Canvas canvas) {
		canvas.drawText(Integer.toString(marcador.getPuntosIzda()),
				getWidth() / 2 - 80, 80, paintBall);
		canvas.drawText(Integer.toString(marcador.getPuntosDcha()),
				getWidth() / 2 + 80, 80, paintBall);
		// Pintamos los puntos de cada lado 80px de distancia de la linea divisoria
	}

	public Marcador getMarcador() {
		return marcador;
	}
	
	public void setSpeed(int speed){
		bolaThread.setSpeed(speed);
	}
	
	public int getSpeed(){
		return bolaThread.getSpeed();
	}
	
	
}
