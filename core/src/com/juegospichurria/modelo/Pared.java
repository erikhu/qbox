package com.juegospichurria.modelo;

import com.badlogic.gdx.math.Vector2;
import com.juegospichurria.qbox.qbox;

public class Pared {

	public static final int ESTADO_NORMAL = 0 ;
	public static final int ESTADO_ACTIVO = 1 ;
	public int estado ;
	
	Vector2 posicion;
	
	public Pared(com.juegospichurria.visual.Celda celda){
		estado = ESTADO_NORMAL;
		posicion = new Vector2(qbox.Pantalla.CELDA*celda.getColumna(),qbox.Pantalla.CELDA*celda.getFila());
	}
	
	public Vector2 getPosicion(){
		return posicion;
	}
	
	public void setEstado(int estado){
		this.estado = estado;
	}
	
	public int getEstado(){
		return this.estado;
	}
}
