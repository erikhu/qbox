package com.juegospichurria.actores;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.juegospichurria.qbox.qbox;
import com.juegospichurria.visual.Recursos;

public class Cuadro extends Actor {

	com.juegospichurria.modelo.Cuadro cuadro;
	com.juegospichurria.actores.Mapa mapa;
	
	public static final int destructible = 0 ;
	
	public static final int indestructible = 1;
	
	int destruir;
	
	 static int contActivos = 0;
	
	int activoNumero;
	
	boolean activo;
	
	public static com.juegospichurria.actores.Cuadro cuadroActivo ;
	
	public int estado;
	
	public Cuadro(final com.juegospichurria.modelo.Cuadro cuadro, final com.juegospichurria.actores.Mapa mapa){
		
		destruir = destructible;
		this.cuadro = cuadro;
		this.mapa = mapa;
		this.setBounds(cuadro.getPosicion().x,cuadro.getPosicion().y, com.juegospichurria.qbox.qbox.Pantalla.CELDA , com.juegospichurria.qbox.qbox.Pantalla.CELDA );
		
		EsTocable();
		
		
		activo = true;
		
		estado = cuadro.getEstado();
		
		EsTocable();
		masActivos();
		
		
		this.addListener(new ActorGestureListener(){
			public void tap(InputEvent event, float x, float y, int count, int button) {
				tocoActivo();
				
			}
		});
		
	}
	
	
	
	public void draw(Batch batch, float parentAlpha){
		if(com.juegospichurria.qbox.Juego.mapaActual<25){
		
			pintandoCuadros(batch , "blanco","verde_oscuro","verde");
		
		}else if(com.juegospichurria.qbox.Juego.mapaActual>=25){
			
			pintandoCuadros(batch , "blanco","morado_oscuro","morado");
			
		}
		
		if(destruir == indestructible){
			batch.draw(Recursos.getRecursos().getCuadroColor("amarrillo"), cuadro.getPosicion().x, cuadro.getPosicion().y,qbox.Pantalla.CELDA,qbox.Pantalla.CELDA);
			
		}
		
		
		
	}
	
	public void pintandoCuadros(Batch batch ,String base , String activo , String inactivo){
		
		switch (getEstado()){
		
		case com.juegospichurria.modelo.Cuadro.BASE:
			batch.draw(Recursos.getRecursos().getCuadroColor(base), cuadro.getPosicion().x, cuadro.getPosicion().y,qbox.Pantalla.CELDA,qbox.Pantalla.CELDA);
			break;
		case com.juegospichurria.modelo.Cuadro.ACTIVO:
			batch.draw(Recursos.getRecursos().getCuadroColor(activo), cuadro.getPosicion().x, cuadro.getPosicion().y,qbox.Pantalla.CELDA,qbox.Pantalla.CELDA);
			break;
		case com.juegospichurria.modelo.Cuadro.INACTIVO:
			batch.draw(Recursos.getRecursos().getCuadroColor(inactivo), cuadro.getPosicion().x, cuadro.getPosicion().y,qbox.Pantalla.CELDA,qbox.Pantalla.CELDA);
			break;
	
			}
	}
	
	public void tocoActivo(){
		Recursos.getRecursos().toca.play(.5f);
		cuadro.setEstado(com.juegospichurria.modelo.Cuadro.ACTIVO);
		setEstado(cuadro.getEstado());
		cuadroActivo = this;
			if(destruir != indestructible){
			masActivos();
			}
		EsTocable();
	}
	
	public void act(float delta){
		setEstado(cuadro.getEstado());
		if(getEstado() == com.juegospichurria.modelo.Cuadro.INACTIVO){
			EsTocable();
		}
		if(getEstado() == com.juegospichurria.modelo.Cuadro.BASE){
			EsTocable();
		}
		if(getEstado() == com.juegospichurria.modelo.Cuadro.PARED){
			destruir = indestructible;
		}
		if(destruir != indestructible){
			if(getEstado() == com.juegospichurria.modelo.Cuadro.MUERE){
				remove();
			}
		}
	}
	
	public void masActivos(){
		if(getEstado() == com.juegospichurria.modelo.Cuadro.ACTIVO ){
			contActivos++;
			activoNumero = contActivos;
			System.out.println("activos "+contActivos);
		}
	}
	
	public void EsTocable(){
		if(getEstado() == com.juegospichurria.modelo.Cuadro.INACTIVO || getEstado() == com.juegospichurria.modelo.Cuadro.PARED){
			this.setTouchable(Touchable.enabled);
		}else{
			this.setTouchable(Touchable.disabled);
		}
	}
	
	public int getEstado(){
		return estado;
	}
	
	public void setEstado(int nuevoEstado){
		estado = nuevoEstado;
	}
	
}
