package com.juegospichurria.qbox;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.juegospichurria.modelo.Escena;
import com.juegospichurria.visual.Recursos;

public class CargaInicial extends Escena {

	float tiempoCarga = 0 ;
	
	public TextureRegion carga;
	public TextureRegion logo;
	
	public CargaInicial(qbox qBox) {
		super(qBox);
		
		carga = Recursos.getRecursos().getCargaImagenes("imagenes/carga/carga.png");
		logo = Recursos.getRecursos().getCargaImagenes("imagenes/carga/logo.png");
	}

	//falta textura
	
	@Override
	public void pintar(float delta) {
	
		batch.begin();
		
	if(tiempoCarga<=10){
		
		if(tiempoCarga<= 5){
		
			
		batch.draw(carga, 0, 0);
		
		
		}else{
			batch.draw(logo, 0, 0);
		}
		
	}else{
			qBox.setScreen(new Menu(qBox));
		}
	
	batch.end();
	
	}

	@Override
	public void actualizar(float delta) {
		tiempoCarga += Gdx.graphics.getDeltaTime();
		System.out.println(""+tiempoCarga);
	}

}
