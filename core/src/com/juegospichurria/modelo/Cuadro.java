package com.juegospichurria.modelo;

import com.badlogic.gdx.math.Vector2;

import com.juegospichurria.qbox.qbox;

public class Cuadro{
	
	public final static int BASE = 0;
	public final static int ACTIVO = 1;
	public final static int INACTIVO = 2;
	public final static int MUERE = 3;
	public final static int PARED = 4;
	private int estado;
	
	
	private com.juegospichurria.visual.Celda celda;
	private Vector2 posicion = null;
	
	
	public Cuadro(com.juegospichurria.visual.Celda celda ){
		
		this.celda = celda;
		
		posicion = new Vector2( celda.getColumna() * qbox.Pantalla.CELDA , celda.getFila() * qbox.Pantalla.CELDA);
		
		estado = BASE;
		
	}
	
	public Vector2 getPosicion(){
		return posicion;
	}
	
	public void CambiarCelda(int fila , int columna){
		celda.setFila(fila);
		celda.setColumna(columna);
	}
	
	public int getFila(){
		
		return celda.getFila();
		
	}
	
	public int getColumna(){
		
		return celda.getColumna();
		
	}
	
	public com.juegospichurria.visual.Celda getCelda(){
		return celda;
	}
	
	public int getEstado(){
		return estado;
	}
	
	public void setEstado(int estado){
		this.estado = estado;
	}

	
	
	
}
