package com.example.conway;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

public class Tablero extends View {
    private Casilla[][] casillas;
    private final int numeroColumnas = 9;
    private final int numeroFilas = 17;
    private  Context context;

    public Tablero(Context context) {

        super(context);
        this.context = context;
        initCasillas();
    }


    public Casilla[][] getCasillas() {
        return casillas;
    }

    private void initCasillas() {
        casillas = new Casilla[numeroFilas][numeroColumnas];

        for (int fila = 0; fila < numeroFilas; fila++) {
            for (int columna = 0; columna < numeroColumnas; columna++) {
                casillas[fila][columna] = new Casilla();
            }
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {

        // Color de fondo de la cuadricula(layout)
        canvas.drawRGB(255, 255, 255);
        int ancho = 0;

        if (canvas.getWidth() < canvas.getHeight())
            ancho = this.getWidth();
        else
            ancho = this.getHeight();

        int anchoCuadricula = ancho / numeroColumnas;

        Paint paint = new Paint();
        paint.setTextSize(50);

        //paint.setARGB( 255, 242,241,241);

        paint.setARGB( 255,125,125,125);

        Paint paintlinea1 = new Paint();
        paintlinea1.setARGB(255, 255, 255, 255);
        int filaActual = 0;

        for (int fila = 0; fila <  numeroFilas; fila++) {

            for (int columna = 0; columna < numeroColumnas; columna++) {
                casillas[fila][columna].fijarxy(columna * anchoCuadricula, filaActual, anchoCuadricula);

                if (!casillas[fila][columna].isEstado())
                    paint.setARGB( 255,125,125,125);
                    // paint.setARGB( 255, 242,241,241);
                else
                    paint.setARGB(255, 0, 0, 0);


                canvas.drawRect(columna * anchoCuadricula, filaActual, columna * anchoCuadricula
                        + anchoCuadricula - 2, filaActual + anchoCuadricula - 2, paint);

                // linea blanca
                canvas.drawLine(columna * anchoCuadricula, filaActual, columna * anchoCuadricula
                        + anchoCuadricula, filaActual, paintlinea1);

                canvas.drawLine(columna * anchoCuadricula + anchoCuadricula - 1, filaActual, columna
                                * anchoCuadricula + anchoCuadricula - 1, filaActual + anchoCuadricula,
                        paintlinea1);

                    /*canvas.drawText(
                            String.valueOf(casillas[fila][columna].getContenido()), columna
                                    * anchoCuadricula + (anchoCuadricula / 2) - numeroColumnas,
                            filaActual + (anchoCuadricula / 2) + numeroFilas, paint2);
                /*


                     */
   //             canvas.drawT
                /*if (casillas[fila][columna].contenido >= 1
                        && casillas[fila][columna].contenido <= 8
                        && casillas[fila][columna].isDestapado())
                    canvas.drawText(
                            String.valueOf(casillas[fila][columna].contenido), columna
                                    * anchoCuadricula + (anchoCuadricula / 2) - 8,
                            filaActual + anchoCuadricula / 2, paint2);

                if (casillas[fila][columna].contenido == 80
                        && casillas[fila][columna].isDestapado()) {
                    Paint bomba = new Paint();
                    bomba.setARGB(255, 255, 0, 0);
                    canvas.drawCircle(columna * anchoCuadricula + (anchoCuadricula / 2),
                            filaActual + (anchoCuadricula / 2), 8, bomba);
                }*/


            }

            filaActual = filaActual + anchoCuadricula;
        }

    }

    public int getNumeroColumnas() {
        return numeroColumnas;
    }

    public int getNumeroFilas() {
        return numeroFilas;
    }

}

