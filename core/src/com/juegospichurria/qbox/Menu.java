package com.juegospichurria.qbox;

import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;

import com.juegospichurria.modelo.Escena;
import com.juegospichurria.visual.Recursos;

public class Menu extends Escena{
	
	boolean volumen = true;	
	float tiempoCreditos =0;
	ScrollPane panel;
	Table tabla;
	
	public static Preferences preferencias;
	Sprite encabezado ;
	Stage estado;
	Button play,tuto;
	
	public Menu(qbox qBox) {
		super(qBox);
	
		
		preferencias = Gdx.app.getPreferences("preferencias");
		
		
		tabla = new Table();
		//tabla.setPosition(qbox.Pantalla.ANCHO/2 , (qbox.Pantalla.ALTO/2)-15);
		panel = new ScrollPane(tabla);
		panel.setBounds((qbox.Pantalla.ANCHO/2)-200 , (qbox.Pantalla.ALTO/2)-150, 400,180);
		//panel.scaleBy(1);
		panel.setForceScroll(false, true);
		
		encabezado = new Sprite(Recursos.getRecursos().getImagenMenu("titulo"));
		encabezado.setPosition((qbox.Pantalla.ANCHO/2)-(Recursos.getRecursos().getImagenMenu("titulo").getRegionWidth()/2),(qbox.Pantalla.ALTO/2)+Recursos.getRecursos().getImagenMenu("titulo").getRegionHeight());
		
		tuto = new Button(new TextureRegionDrawable(Recursos.getRecursos().getCargaImagenes("imagenes/menu/tuto.png")));
		tuto.setPosition(qbox.Pantalla.ANCHO-64, qbox.Pantalla.ALTO-64);
		tuto.addListener(new ActorGestureListener(){
			public void tap(InputEvent event, float x, float y, int count, int button) {
				accionTuto();
			}
		});
		
		play = new Button(new TextureRegionDrawable(Recursos.getRecursos().getImagenMenu("play")));
		play.setPosition(120, 120);
		play.addListener(new ActorGestureListener(){
			public void tap(InputEvent event, float x, float y, int count, int button) {
				accionPlay();
			}
		});
		
		
		
		
		
		estado = new Stage(new FitViewport(480,800));
		
		if(!preferencias.contains("niveles")){
			preferencias.putInteger("niveles", 1);
			preferencias.flush();
		}
		
		crearMenuNiveles();
		
		estado.addActor(play);
		estado.addActor(tuto);
	
		estado.addActor(panel);
		
		Gdx.input.setInputProcessor(estado);
	}

	public void accionTuto() {
		qBox.setScreen(new Tuto(qBox));
	}

	@Override
	public void pintar(float delta) {
		estado.draw();
		batch.begin();
		encabezado.draw(batch);
		batch.end();
	}

	@Override
	public void actualizar(float delta) {
		
		Gdx.input.setInputProcessor(estado);
		Gdx.gl.glClearColor(0,0,0,0);
		estado.act();
		
		Gdx.input.setCatchBackKey(true);
		
		if(tabla.isVisible()){
			if(Gdx.input.isKeyPressed(Input.Keys.BACK)){
				tabla.setVisible(false);
				botonesVisibles(true);
				Gdx.input.setCatchBackKey(false);
				
			}
		}
		
	}
	
	public void crearMenuNiveles(){
		
		int maxColumnas = -1;
		
	for( int i = 0 ; i < preferencias.getInteger("niveles"); i++){
			final int numNivel = i;
			TextButton.TextButtonStyle estilo = new TextButton.TextButtonStyle(); 
			estilo.up = new TextureRegionDrawable(Recursos.getRecursos().getCargaImagenes("imagenes/menu/mapa.png"));
			BitmapFont fuente = new BitmapFont();
			fuente.scale(1);
			estilo.font = fuente;
			TextButton boton = new TextButton(""+i,estilo);
			boton.setTouchable(Touchable.enabled);
			boton.addListener(new ActorGestureListener(){
				public void tap(InputEvent event, float x, float y, int count, int button) {
					Recursos.getRecursos().entra.play();
					qBox.setScreen(new Juego(qBox,numNivel));
				}
			});
				
			if(maxColumnas< 4){
				
				maxColumnas++;
				
			}else{
				
				maxColumnas = 0;
				tabla.row();
		
				
			}
			
			tabla.add(boton);
		
			
		}
		
		//tabla.setVisible(false);
		panel.setVisible(false);
		//tuto.setVisible(false);
	}
	
	public void botonesVisibles(boolean visible){
		play.setVisible(visible);
		tuto.setVisible(visible);
	}
	
		
	public void accionPlay(){
		
		botonesVisibles(false);
		//tabla.setVisible(true);
		panel.setVisible(true);
	}

}
