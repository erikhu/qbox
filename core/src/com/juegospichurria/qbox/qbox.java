package com.juegospichurria.qbox;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;

public class qbox extends Game implements ApplicationListener{
	
	
	public static class Pantalla{
		public static final int  ANCHO = 480;
		public static final int  ALTO = 800;
		public static final int CELDA = 60;
		public static final int ALTURA_INICIAL = ALTO/4;
	}
	CargaInicial carga;
	public static Juego juego;
	public static Menu menu;
	
	@Override
	public void create () {
		carga = new CargaInicial(this);
		juego = new Juego(this,0);
		menu = new Menu(this);
		this.setScreen(carga);
	
		
	}

	@Override
	public void render () {
		super.render();
		
		
	}
	
	
	
}
