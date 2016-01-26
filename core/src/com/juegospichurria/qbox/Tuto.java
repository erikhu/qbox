package com.juegospichurria.qbox;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.juegospichurria.modelo.Escena;
import com.juegospichurria.visual.Recursos;

public class Tuto extends Escena{

	TextureRegion imagenActual;
	TextureRegion next;
	Rectangle huesosNext;
	String[] imagenes = {"imagenes/tuto/tuto1.png","imagenes/tuto/tuto2.png","imagenes/tuto/tuto3.png"};
	Vector3 puntero;
	int index;
	
	public Tuto(qbox qBox) {
		super(qBox);
		
		index = 0;
		
		imagenActual = Recursos.getRecursos().getCargaImagenes(imagenes[index]);
		next = Recursos.getRecursos().getCargaImagenes("imagenes/tuto/next.png");
		huesosNext = new Rectangle(qbox.Pantalla.ANCHO-128,qbox.Pantalla.ALTO-64 , 64,64);
		puntero = new Vector3();
		
	}

	@Override
	public void pintar(float delta) {
		
		batch.begin();
		batch.draw(imagenActual,0,0);
		batch.draw(next, qbox.Pantalla.ANCHO-128,qbox.Pantalla.ALTO-64 , 64,64);
		batch.end();
		
	}

	@Override
	public void actualizar(float delta) {
		if(Gdx.input.justTouched()){
			puntero = camara.unproject(puntero.set(Gdx.input.getX(),Gdx.input.getY(), 0));
			
			System.out.println("en x " + puntero.x + " en Y : "+ puntero.y);
			
			if(index>=2){
				qBox.setScreen(new Menu(qBox));
			}
			
			if(huesosNext.contains(puntero.x, puntero.y) ){
				if(index<2){
				index+=1;
				}
				System.out.println("toco: "+ index);
				imagenActual = Recursos.getRecursos().getCargaImagenes(imagenes[index]);
			}
			
			
		}	
	}

}
