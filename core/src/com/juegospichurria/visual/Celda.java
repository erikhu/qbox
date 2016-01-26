package com.juegospichurria.visual;

import com.badlogic.gdx.math.Vector2;

public class Celda {

	private int fila;
	private int columna;
	
	public Celda(int fila , int columna){
		this.setFila(fila);
		this.setColumna(columna);
	}
	
	public Celda(Vector2 posicion){
		setFila((int)posicion.y);
		setColumna((int)posicion.x);
	}
	
	public Celda(Celda celda){
		fila = celda.getFila();
		columna = celda.getColumna();
	}
	
	public Vector2 getPosicion(){
		return new Vector2(columna, fila);
	}

	public int getFila() {
		return fila;
	}

	public void setFila(int fila) {
		this.fila = fila;
	}

	public int getColumna() {
		return columna;
	}

	public void setColumna(int columna) {
		this.columna = columna;
	}
	
}
