package com.juegospichurria.actores;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;

public class Pared extends Actor {

	com.juegospichurria.modelo.Pared pared ;
	int estado;
	
	public Pared(com.juegospichurria.modelo.Pared pared){
		this.pared = pared;
		estado = this.pared.getEstado();
		
		Tocable();
		
		this.addListener(new ActorGestureListener(){
			public void tap(InputEvent event, float x, float y, int count, int button) {
				
				setEstado(com.juegospichurria.modelo.Pared.ESTADO_NORMAL);
			}		
		});
	}
	
	public void draw(){
		switch ( estado ){
			case com.juegospichurria.modelo.Pared.ESTADO_ACTIVO:
				break;
			case com.juegospichurria.modelo.Pared.ESTADO_NORMAL:
				break;
		}
	}
	
	public void act(){
		Tocable();	
	}
	
	public void Tocable(){
		if(getEstado() == com.juegospichurria.modelo.Pared.ESTADO_ACTIVO){
			this.setTouchable(Touchable.enabled);
		}else{
			this.setTouchable(Touchable.disabled);
		}
	}
	
	public int getEstado(){
		return estado;
	}
	
	public void setEstado(int estado){
		this.estado = estado; 
	}
}
