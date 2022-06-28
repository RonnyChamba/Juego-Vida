package com.example.conway;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity  implements View.OnTouchListener, View.OnClickListener {

    private Tablero fondo;
    private Casilla[][] casillas;
    private boolean[][] estadoCasillas;
    private Button btnPlay;
    private Button btnDetener;
    private Button btnReiniciar;
    private Button btnAutores;
    private boolean detener;
    private ThredEjecucion thredEjecucion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout layout = findViewById(R.id.layout1);
        btnPlay = findViewById(R.id.btnJuego);
        btnDetener = findViewById(R.id.btnDetener);
        btnReiniciar = findViewById(R.id.btnReiniciar);
        btnAutores = findViewById(R.id.btnProgramadores);

        btnPlay.setOnClickListener(this);
        btnDetener.setOnClickListener(this);
        btnReiniciar.setOnClickListener(this);
        btnAutores.setOnClickListener(this);

        fondo = new Tablero(this);
        estadoCasillas = new boolean[fondo.getNumeroFilas()][fondo.getNumeroColumnas()];
        casillas = fondo.getCasillas();
        fondo.setOnTouchListener(this);

        layout.addView(fondo);
        getSupportActionBar().hide();
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        int typeEvent = event.getAction();

        if (typeEvent == MotionEvent.ACTION_DOWN) {

            for (int row = 0; row < fondo.getNumeroFilas(); row++) {

                for (int column = 0; column < fondo.getNumeroColumnas(); column++) {

                    int posX = (int) event.getX();
                    int posY = (int) event.getY();

                    Casilla casillaPulsada = casillas[row][column];

                    if (casillaPulsada.dentro(posX, posY))
                        casillaPulsada.setEstado(!casillaPulsada.isEstado());

                }
            }
            fondo.invalidate();

        }

        return true;
    }
    private void fotografiar() {

        for (int row = 0; row < fondo.getNumeroFilas(); row++) {
            for (int column = 0; column < fondo.getNumeroColumnas(); column++) {
                estadoCasillas[row][column] = casillas[row][column].isEstado();
            }
        }
    }


    private int contarVivas(int posX, int posY) {

        int vivas = 0;

        // Representacion de yx: 03
        String coordenadaCasilla = String.format("[%s-%s]", posY, posX);

        // Guardar posicion actual en x de la cuadricula -1
        int posXInicio = posX - 1;
        // Guardar posicion actual en y de la cuadricula -1
        int posYInicio = posY - 1;

        // Guardar posicion actual en x de la cuadricula + una posicion
        int posXFinal = posX + 1;
        // Guardar posicion actual en y de la cuadricula +  una posicion
        int posYFinal = posY + 1;

        // Para que siempre tenga celdas vecinos , ademas que no nos encontremos en la ultima columna
        // o la ultima fila
        if ((posXInicio >= 0 && posYInicio >= 0)
                && (posY < fondo.getNumeroFilas() - 1 &&
                posX < fondo.getNumeroColumnas() - 1)
        ) {
            // Log.i("Vivas:", String.format("[%s-%s]", posYInicio, posXInicio));
            for (int row = posYInicio; row <= posYFinal; row++) {


                for (int column = posXInicio; column <= posXFinal; column++) {

                    try {
                        // Para que tome en cuenta la celda que se esta tomando como referencia para
                        // encontrar los vecinos vivos

                        String coordenadaCasillaVecina = String.format("[%s-%s]", row, column);

                        if (!coordenadaCasillaVecina.equals(coordenadaCasilla)) {
                            if (estadoCasillas[row][column])
                                vivas++;
                        }


                        // Si es mayor a 3, no es necesario seguir iterando las demas celdas vecinas ya que
                        // con ello muere la celda iterada
                        if (vivas > 3) {
                            return vivas;
                        }

                    //    Log.i("Separador", "**********************************************");

                    } catch (Exception e) {

                        e.printStackTrace();
                        Log.i("Contar vivas:", e.getMessage() + " " + e.getClass().getSimpleName());
                    }

                }
            }

        }

        return vivas;
    }

    private void siguienteEstado() {

        Log.i("Thread Mary: ", "-----------------Iniciando--------------");
        thredEjecucion = new ThredEjecucion();
        thredEjecucion.start();
    }


    class ThredEjecucion extends Thread {

        public ThredEjecucion() {

        }

        @Override
        public void run() {

            Timer timer = new Timer();
            timer.schedule(new TimerTask() {

                @Override
                public void run() {

                    fotografiar();
                    for (int row = 0; row < fondo.getNumeroFilas(); row++) {

                        for (int column = 0; column < fondo.getNumeroColumnas(); column++) {

                            Log.i("Valor:", String.format("[%s-%s]", row, column));
                            int vivas = contarVivas(column, row);
                            Casilla casilla = casillas[row][column];

                            boolean estadoCelula = estadoCasillas[row][column];

                            if (estadoCelula) { // Celula Esta viva
                                // Sobrevive
                                if (vivas < 2 || vivas > 3)
                                    casilla.setEstado(!estadoCelula);
                            } else if (!estadoCelula) { // Celula esta muerta

                                // Nacimiento
                                if (vivas == 3) // celula nace
                                    casilla.setEstado(!estadoCelula);
                            }
                        }
                    }

                    fondo.invalidate();
                    //incremento++;

                    if (detener) {
                        timer.cancel();
                        Log.i("Finalizado: ", "--------------------------- : Hilo: " + thredEjecucion.isInterrupted());
                    }

                }

            }, 1000, 1000);

        }
    }

    public void reiniciar() {
        for (int row = 0; row < fondo.getNumeroFilas(); row++) {
            for (int column = 0; column < fondo.getNumeroColumnas(); column++) {
                casillas[row][column].setEstado(false);
            }
        }
        fondo.invalidate();
    }
    @Override
    public void onClick(View view) {

        int id = view.getId();

        if (id == R.id.btnJuego) {
            detener = false;
            siguienteEstado();

        } else if (id == R.id.btnDetener) {

            detener = true;

        } else if (id == R.id.btnReiniciar) {
            reiniciar();
        }else if (id == R.id.btnProgramadores) {


            Intent intent = new Intent(this, Programador.class);
            startActivity(intent);
            reiniciar();
        }
    }


}