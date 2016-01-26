package com.juegospichurria.actores;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.juegospichurria.qbox.Juego;
import com.juegospichurria.qbox.Menu;
import com.juegospichurria.qbox.qbox;
import com.juegospichurria.visual.Recursos;

public class Mapa {
	
	public static final int ESTADO_NORMAL=0;
	public static final int ESTADO_PIERDE = 1;
	public static final int ESTADO_GANA = 2;
	
	SpriteBatch batch2;
	OrthographicCamera camara2;
	
	
	public int estado_juego;
	
	BitmapFont fuente;
	
	int cantInactivos ;
	float time = 0;

	com.juegospichurria.modelo.Mapa mapa;
	com.juegospichurria.modelo.Mapa mapatemp;
	
	Array<com.juegospichurria.actores.Cuadro> cuadrosActores;
	Stage estado;
	
	Image gana,pierde;
	Button replay,reset,next,menu;
	Stage estado_botones;
	
	
	int filaActivo , columnaActivo , filatemp , columnatemp;
	
	Viewport vista;
	
	
	public Mapa(com.juegospichurria.modelo.Mapa mapa, int filaActivo , int columnaActivo){
		cantInactivos = 0;
		Cuadro.contActivos = 0;
		mapatemp = mapa;
		Juego.mapaid = mapa.numeroDeMapa;
		estado_botones = new Stage(new FitViewport(480,800));
		gana = new Image(new TextureRegionDrawable(Recursos.getRecursos().getImagenSubmenu("win")));
		gana.setBounds(80,(qbox.Pantalla.ALTO-Recursos.getRecursos().getImagenSubmenu("fail").getRegionHeight()*2),Recursos.getRecursos().getImagenSubmenu("fail").getRegionWidth(),Recursos.getRecursos().getImagenSubmenu("win").getRegionHeight());
		
		pierde = new Image(new TextureRegionDrawable(Recursos.getRecursos().getImagenSubmenu("fail")));
		pierde.setBounds(80,(qbox.Pantalla.ALTO-Recursos.getRecursos().getImagenSubmenu("fail").getRegionHeight()*2),Recursos.getRecursos().getImagenSubmenu("fail").getRegionWidth(),Recursos.getRecursos().getImagenSubmenu("fail").getRegionHeight());
	
		
		replay = new Button(new TextureRegionDrawable(Recursos.getRecursos().getImagenSubmenu("replay")));
		replay.setTouchable(Touchable.enabled);
		replay.setBounds(qbox.Pantalla.ANCHO/2,qbox.Pantalla.ALTO/2, Recursos.getRecursos().getImagenSubmenu("replay").getRegionWidth(), Recursos.getRecursos().getImagenSubmenu("replay").getRegionHeight());
		
		replay.addListener(new ActorGestureListener(){
			public void tap(InputEvent event, float x, float y, int count, int button) {
				System.out.println("me toco");
				Cuadro.contActivos = 0 ;
				Juego.pierde = true;
				Juego.gana = false;
				
			}
		});
		
		
		
		
		next = new Button(new TextureRegionDrawable(Recursos.getRecursos().getImagenSubmenu("next")));
		next.setBounds(qbox.Pantalla.ANCHO/2, (qbox.Pantalla.ALTO/2)-80, Recursos.getRecursos().getImagenSubmenu("reset").getRegionWidth(), Recursos.getRecursos().getImagenSubmenu("reset").getRegionHeight());
		next.addListener(new ActorGestureListener(){
			public void tap(InputEvent event, float x, float y, int count, int button) {
				System.out.println("me toco next");
				Cuadro.contActivos = 0 ;
				Juego.gana = true;
				Juego.pierde = false;
				
			}
		});
		
		reset = new Button(new TextureRegionDrawable(Recursos.getRecursos().getImagenSubmenu("reset")));
		reset.setBounds(0, 800- Recursos.getRecursos().getImagenSubmenu("reset").getRegionHeight(), Recursos.getRecursos().getImagenSubmenu("reset").getRegionWidth(), Recursos.getRecursos().getImagenSubmenu("reset").getRegionHeight());
		reset.addListener(new ActorGestureListener(){
			public void tap(InputEvent event, float x, float y, int count, int button) {
				System.out.println("me toco");
				Cuadro.contActivos = 0 ;
				time = 0;
				Juego.pierde = true;
				Juego.gana = false;
				
			}
		});
		
		menu = new Button(new TextureRegionDrawable(Recursos.getRecursos().getImagenSubmenu("menu")));
		menu.setBounds((qbox.Pantalla.ANCHO/2)-Recursos.getRecursos().getImagenSubmenu("menu").getRegionWidth(), (qbox.Pantalla.ALTO/2)-30, Recursos.getRecursos().getImagenSubmenu("menu").getRegionWidth(), Recursos.getRecursos().getImagenSubmenu("menu").getRegionHeight());
		menu.addListener(new ActorGestureListener(){
			public void tap(InputEvent event, float x, float y, int count, int button) {
				System.out.println("me toco menu");
				Juego.menu = true;
			}
		});
		
		
		estado_botones.addActor(replay);
		estado_botones.addActor(menu);
		estado_botones.addActor(next);
		
		batch2 = new SpriteBatch();
		camara2 = new OrthographicCamera();
		camara2.setToOrtho(false,320, 480);
		batch2.setProjectionMatrix(camara2.combined);
		camara2.zoom = 120;
		camara2.update();
		
		

		filatemp = filaActivo;
		columnatemp = columnaActivo;
		
		estado_juego = ESTADO_NORMAL;
		
		Inicializar(mapatemp , filatemp , columnatemp );
				
	}
	
	public void Inicializar(com.juegospichurria.modelo.Mapa mapa , int filaActivo , int columnaActivo){
		
		
		this.mapa = mapa;
		
		fuente = new BitmapFont();
		fuente.setColor(new Color(1,1,1,1));
		fuente.scale(.1f);
		

		estado = new Stage(new FitViewport(qbox.Pantalla.ANCHO, qbox.Pantalla.ALTO));
		
		this.filaActivo = filaActivo;
		this.columnaActivo = columnaActivo;
		
		
		
		cuadrosActores  = new Array<com.juegospichurria.actores.Cuadro>();

		int index = 0 ;
		for(com.juegospichurria.modelo.Cuadro cuadro : this.mapa.getCuadros()){ 	
			
			com.juegospichurria.actores.Cuadro cuadrillo  = new com.juegospichurria.actores.Cuadro(cuadro, this);
			cuadrosActores.add(cuadrillo);
			estado.addActor(cuadrosActores.get(index));
			index++;
		}
		
		com.juegospichurria.actores.Cuadro.cuadroActivo = obtenerActivo();
		
		estado.addActor(reset);
			
		
	
		
	}
	
	public boolean existeActorCuadro(int fila , int columna){
		for(Actor actor : estado.getActors()){
			
				try{
			com.juegospichurria.actores.Cuadro cuadro = (com.juegospichurria.actores.Cuadro) actor;
			
			if(cuadro.cuadro.getFila() == fila   && cuadro.cuadro.getColumna() == columna){
				return true;
				
			}
				}catch(Exception e){System.out.println("no es cuadro");};
		}
		return false;
	}
	
	
	
	public void pintar(SpriteBatch batch , float parentAlpha, ScreenViewport vista){
	
		
		
		if(estado_juego == ESTADO_NORMAL){
			
		time += Gdx.graphics.getDeltaTime();
		
		estado.draw();
		batch2.begin();
		fuente.draw(batch2, ""+(int)time, 220,480 );
		batch2.end();
		
		}else if(estado_juego == ESTADO_GANA){
			
			final int tiempo = (int)time;
			
			batch.begin();
		//	fuente.draw(batch2, "Ganaste", 320/2,480/2);
			gana.draw(batch, 1);
			replay.draw(batch, 1);
			menu.draw(batch, 1);
			next.draw(batch, 1);
			
				if(tiempo >= 0 && tiempo <=10){
					batch.draw(Recursos.getRecursos().getCuadroColor("amarrillo"),(qbox.Pantalla.ANCHO/2)-128, (qbox.Pantalla.ALTO/2)-180);
					batch.draw(Recursos.getRecursos().getCuadroColor("azul"),(qbox.Pantalla.ANCHO/2)-52, (qbox.Pantalla.ALTO/2)-180);
					batch.draw(Recursos.getRecursos().getCuadroColor("rojo"),(qbox.Pantalla.ANCHO/2)+25, (qbox.Pantalla.ALTO/2)-180);
				}else if(tiempo > 10 && tiempo <=20){
					
					batch.draw(Recursos.getRecursos().getCuadroColor("amarrillo"),(qbox.Pantalla.ANCHO/2)-128, (qbox.Pantalla.ALTO/2)-180);
					batch.draw(Recursos.getRecursos().getCuadroColor("azul"),(qbox.Pantalla.ANCHO/2)-52, (qbox.Pantalla.ALTO/2)-180);
					
				}else if(tiempo > 20 && tiempo <=30){
					batch.draw(Recursos.getRecursos().getCuadroColor("amarrillo"),(qbox.Pantalla.ANCHO/2)-52, (qbox.Pantalla.ALTO/2)-180);
				}
				
			batch.end();
		}else if(estado_juego == ESTADO_PIERDE){
			batch.begin();
			
			pierde.draw(batch, 1);
			replay.draw(batch, 1);
			menu.draw(batch, 1);
			
			batch.end();
		}
		
	
		
	}
	
	
	public void actualizar(float delta){
		
		switch (	estado_juego	){
		
		case ESTADO_NORMAL:
			estado_normal(delta);
			break;
			
		case ESTADO_GANA:
			estado_gana(delta);
			break;
		
		case ESTADO_PIERDE:
			estado_pierde(delta);
			break;
		
		}
		
	}
	
	public void estado_normal(float delta){
		Gdx.input.setInputProcessor(estado);
	
		seMueve();
		
		EliminarActivos();
		
		estado.act();
		
		if(cantInactivos == 0){

			Recursos.getRecursos().pierde.play();
			Gdx.app.log("","perdiste");
			estado_juego = ESTADO_PIERDE;
		}
		
		if(mapa.contCuadros == com.juegospichurria.actores.Cuadro.contActivos){
			Recursos.getRecursos().gana.play();
			estado_juego = ESTADO_GANA;
			Gdx.app.log("","Ganaste");
		}
		
		
				
	}
	
	public void estado_gana(float delta){
		
		Gdx.input.setInputProcessor(estado_botones);
		next.setVisible(true);
		
	}
	
	public void estado_pierde(float delta){
		
		Gdx.input.setInputProcessor(estado_botones);
		next.setVisible(false);
		
	}
	
	public void EliminarActivos(){
		for(com.juegospichurria.actores.Cuadro cuadro : cuadrosActores){
			
			if(cuadro != com.juegospichurria.actores.Cuadro.cuadroActivo && cuadro.getEstado() == com.juegospichurria.modelo.Cuadro.ACTIVO ){
				volviendoABase(cuadro);
				if(cuadro.destruir != com.juegospichurria.actores.Cuadro.indestructible){
				mapa.setCuadroEstado(cuadro.cuadro.getFila(), cuadro.cuadro.getColumna(), com.juegospichurria.modelo.Cuadro.MUERE); 
				}else{
					mapa.setCuadroEstado(cuadro.cuadro.getFila(), cuadro.cuadro.getColumna(), com.juegospichurria.modelo.Cuadro.PARED); 
				}
			}
			
		}
	}
	
	public void volviendoABase(com.juegospichurria.actores.Cuadro cuadro){
		
			
			if( mapa.existeCuadro(cuadro.cuadro.getFila(),cuadro.cuadro.getColumna()+1)){
				if( mapa.getCuadro(cuadro.cuadro.getFila(),cuadro.cuadro.getColumna()+1).getEstado() != com.juegospichurria.modelo.Cuadro.ACTIVO
						&& mapa.getCuadro(cuadro.cuadro.getFila(),cuadro.cuadro.getColumna()+1).getEstado() != com.juegospichurria.modelo.Cuadro.PARED){
				mapa.setCuadroEstado(cuadro.cuadro.getFila(), cuadro.cuadro.getColumna()+1, com.juegospichurria.modelo.Cuadro.BASE); 
				}
			}
			if( mapa.existeCuadro(cuadro.cuadro.getFila(),cuadro.cuadro.getColumna()-1) ){
			
				if(mapa.getCuadro(cuadro.cuadro.getFila(),cuadro.cuadro.getColumna()-1).getEstado() != com.juegospichurria.modelo.Cuadro.ACTIVO
						&& mapa.getCuadro(cuadro.cuadro.getFila(),cuadro.cuadro.getColumna()-1).getEstado() != com.juegospichurria.modelo.Cuadro.PARED){
				mapa.setCuadroEstado(cuadro.cuadro.getFila(), cuadro.cuadro.getColumna()-1, com.juegospichurria.modelo.Cuadro.BASE); 
				}
			}
			
			if( mapa.existeCuadro(cuadro.cuadro.getFila()+1,cuadro.cuadro.getColumna())){
				
				if(mapa.getCuadro(cuadro.cuadro.getFila()+1,cuadro.cuadro.getColumna()).getEstado() != com.juegospichurria.modelo.Cuadro.ACTIVO
						&& mapa.getCuadro(cuadro.cuadro.getFila()+1,cuadro.cuadro.getColumna()).getEstado() != com.juegospichurria.modelo.Cuadro.PARED){
				mapa.setCuadroEstado(cuadro.cuadro.getFila()+1, cuadro.cuadro.getColumna(), com.juegospichurria.modelo.Cuadro.BASE); 
				}
			}
			
			if( mapa.existeCuadro(cuadro.cuadro.getFila()-1,cuadro.cuadro.getColumna()) ){
			
				if(mapa.getCuadro(cuadro.cuadro.getFila()-1,cuadro.cuadro.getColumna()).getEstado() != com.juegospichurria.modelo.Cuadro.ACTIVO
						&& mapa.getCuadro(cuadro.cuadro.getFila()-1,cuadro.cuadro.getColumna()).getEstado() != com.juegospichurria.modelo.Cuadro.PARED){
				mapa.setCuadroEstado(cuadro.cuadro.getFila()-1, cuadro.cuadro.getColumna(), com.juegospichurria.modelo.Cuadro.BASE); 
				}
			}
			
			//Diagonal
			
			if( mapa.existeCuadro(cuadro.cuadro.getFila()+1,cuadro.cuadro.getColumna()+1) ){
				
				if(mapa.getCuadro(cuadro.cuadro.getFila()+1,cuadro.cuadro.getColumna()+1).getEstado() != com.juegospichurria.modelo.Cuadro.ACTIVO
						&& mapa.getCuadro(cuadro.cuadro.getFila()+1,cuadro.cuadro.getColumna()+1).getEstado() != com.juegospichurria.modelo.Cuadro.PARED){
				mapa.setCuadroEstado(cuadro.cuadro.getFila()+1, cuadro.cuadro.getColumna()+1, com.juegospichurria.modelo.Cuadro.BASE); 
				}
			}
			
			if( mapa.existeCuadro(cuadro.cuadro.getFila()-1,cuadro.cuadro.getColumna()+1) ){
				
				if(mapa.getCuadro(cuadro.cuadro.getFila()-1,cuadro.cuadro.getColumna()+1).getEstado() != com.juegospichurria.modelo.Cuadro.ACTIVO
						&& mapa.getCuadro(cuadro.cuadro.getFila()-1,cuadro.cuadro.getColumna()+1).getEstado() != com.juegospichurria.modelo.Cuadro.PARED){
				mapa.setCuadroEstado(cuadro.cuadro.getFila()-1, cuadro.cuadro.getColumna()+1, com.juegospichurria.modelo.Cuadro.BASE); 
				}
			}
			
			if( mapa.existeCuadro(cuadro.cuadro.getFila()-1,cuadro.cuadro.getColumna()-1) ){
				
				if(mapa.getCuadro(cuadro.cuadro.getFila()-1,cuadro.cuadro.getColumna()-1).getEstado() != com.juegospichurria.modelo.Cuadro.ACTIVO
						&& mapa.getCuadro(cuadro.cuadro.getFila()-1,cuadro.cuadro.getColumna()-1).getEstado() != com.juegospichurria.modelo.Cuadro.PARED){
				mapa.setCuadroEstado(cuadro.cuadro.getFila()-1, cuadro.cuadro.getColumna()-1, com.juegospichurria.modelo.Cuadro.BASE); 
				}
			}
			
			if( mapa.existeCuadro(cuadro.cuadro.getFila()+1,cuadro.cuadro.getColumna()-1) ){
				
				if(mapa.getCuadro(cuadro.cuadro.getFila()+1,cuadro.cuadro.getColumna()-1).getEstado() != com.juegospichurria.modelo.Cuadro.ACTIVO
						&& mapa.getCuadro(cuadro.cuadro.getFila()+1,cuadro.cuadro.getColumna()-1).getEstado() != com.juegospichurria.modelo.Cuadro.PARED){
				mapa.setCuadroEstado(cuadro.cuadro.getFila()+1, cuadro.cuadro.getColumna()-1, com.juegospichurria.modelo.Cuadro.BASE); 
				}
			}
			
		
	}
	
	public com.juegospichurria.actores.Cuadro obtenerActivo(){
		for(com.juegospichurria.actores.Cuadro cuadro : cuadrosActores){
			if(cuadro.getEstado() == com.juegospichurria.modelo.Cuadro.ACTIVO){
				return cuadro;
			}
		}
		
		return null;
	}

	public boolean existeDerecha(){
		return mapa.existeCuadro(com.juegospichurria.actores.Cuadro.cuadroActivo.cuadro.getFila(),com.juegospichurria.actores.Cuadro.cuadroActivo.cuadro.getColumna()+1);
	}
	
	public boolean existeIzquerda(){
		return mapa.existeCuadro(com.juegospichurria.actores.Cuadro.cuadroActivo.cuadro.getFila(),com.juegospichurria.actores.Cuadro.cuadroActivo.cuadro.getColumna()-1);
	}
	public boolean existeArriba(){
		return mapa.existeCuadro(com.juegospichurria.actores.Cuadro.cuadroActivo.cuadro.getFila()+1,com.juegospichurria.actores.Cuadro.cuadroActivo.cuadro.getColumna());
	}
	public boolean existeAbajo(){
		return mapa.existeCuadro(com.juegospichurria.actores.Cuadro.cuadroActivo.cuadro.getFila()-1,com.juegospichurria.actores.Cuadro.cuadroActivo.cuadro.getColumna());
	}
	
	public boolean existeDerArriba(){
		return mapa.existeCuadro(com.juegospichurria.actores.Cuadro.cuadroActivo.cuadro.getFila()+1,com.juegospichurria.actores.Cuadro.cuadroActivo.cuadro.getColumna()+1);
	}
	public boolean existeDerAbajo(){
		return mapa.existeCuadro(com.juegospichurria.actores.Cuadro.cuadroActivo.cuadro.getFila()-1,com.juegospichurria.actores.Cuadro.cuadroActivo.cuadro.getColumna()+1);
	}
	
	public boolean existeIzqArriba(){
		return mapa.existeCuadro(com.juegospichurria.actores.Cuadro.cuadroActivo.cuadro.getFila()+1,com.juegospichurria.actores.Cuadro.cuadroActivo.cuadro.getColumna()-1);
	}
	
	public boolean existeIzqAbajo(){
		return mapa.existeCuadro(com.juegospichurria.actores.Cuadro.cuadroActivo.cuadro.getFila()-1,com.juegospichurria.actores.Cuadro.cuadroActivo.cuadro.getColumna()-1);
	}
	public void seMueve(){
		cantInactivos = 0 ;
			
			if(existeDerecha()){
				if(existeActorCuadro(com.juegospichurria.actores.Cuadro.cuadroActivo.cuadro.getFila(),com.juegospichurria.actores.Cuadro.cuadroActivo.cuadro.getColumna()+1)){
					cantInactivos++;
					
					}
				
				if(mapa.getCuadro(com.juegospichurria.actores.Cuadro.cuadroActivo.cuadro.getFila(),com.juegospichurria.actores.Cuadro.cuadroActivo.cuadro.getColumna()+1).getEstado() != com.juegospichurria.modelo.Cuadro.ACTIVO 
						&& mapa.getCuadro(com.juegospichurria.actores.Cuadro.cuadroActivo.cuadro.getFila(),com.juegospichurria.actores.Cuadro.cuadroActivo.cuadro.getColumna()+1).getEstado() != com.juegospichurria.modelo.Cuadro.MUERE
					){
					
					
					mapa.setCuadroEstado(com.juegospichurria.actores.Cuadro.cuadroActivo.cuadro.getFila(),com.juegospichurria.actores.Cuadro.cuadroActivo.cuadro.getColumna()+1, com.juegospichurria.modelo.Cuadro.INACTIVO);
					
				}
			}
			if(existeIzquerda()){
				if(existeActorCuadro(com.juegospichurria.actores.Cuadro.cuadroActivo.cuadro.getFila(),com.juegospichurria.actores.Cuadro.cuadroActivo.cuadro.getColumna()-1)){
					cantInactivos++;
					
					}
				if(mapa.getCuadro(com.juegospichurria.actores.Cuadro.cuadroActivo.cuadro.getFila(),com.juegospichurria.actores.Cuadro.cuadroActivo.cuadro.getColumna()-1).getEstado() != com.juegospichurria.modelo.Cuadro.ACTIVO
						&& mapa.getCuadro(com.juegospichurria.actores.Cuadro.cuadroActivo.cuadro.getFila(),com.juegospichurria.actores.Cuadro.cuadroActivo.cuadro.getColumna()-1).getEstado() != com.juegospichurria.modelo.Cuadro.MUERE
					){
					
					mapa.setCuadroEstado(com.juegospichurria.actores.Cuadro.cuadroActivo.cuadro.getFila(),com.juegospichurria.actores.Cuadro.cuadroActivo.cuadro.getColumna()-1, com.juegospichurria.modelo.Cuadro.INACTIVO);
					
				}
			}
			if(existeArriba()){
				if(existeActorCuadro(com.juegospichurria.actores.Cuadro.cuadroActivo.cuadro.getFila()+1,com.juegospichurria.actores.Cuadro.cuadroActivo.cuadro.getColumna())){
					cantInactivos++;
					
					}
				if(mapa.getCuadro(com.juegospichurria.actores.Cuadro.cuadroActivo.cuadro.getFila()+1,com.juegospichurria.actores.Cuadro.cuadroActivo.cuadro.getColumna()).getEstado() != com.juegospichurria.modelo.Cuadro.ACTIVO
						&& mapa.getCuadro(com.juegospichurria.actores.Cuadro.cuadroActivo.cuadro.getFila()+1,com.juegospichurria.actores.Cuadro.cuadroActivo.cuadro.getColumna()).getEstado() != com.juegospichurria.modelo.Cuadro.MUERE
					){
					
					mapa.setCuadroEstado(com.juegospichurria.actores.Cuadro.cuadroActivo.cuadro.getFila()+1,com.juegospichurria.actores.Cuadro.cuadroActivo.cuadro.getColumna(), com.juegospichurria.modelo.Cuadro.INACTIVO);
					
				}
	
			}
			if(existeAbajo()){
				if(existeActorCuadro(com.juegospichurria.actores.Cuadro.cuadroActivo.cuadro.getFila()-1,com.juegospichurria.actores.Cuadro.cuadroActivo.cuadro.getColumna())){
					cantInactivos++;
					
					}
				if(mapa.getCuadro(com.juegospichurria.actores.Cuadro.cuadroActivo.cuadro.getFila()-1,com.juegospichurria.actores.Cuadro.cuadroActivo.cuadro.getColumna()).getEstado() != com.juegospichurria.modelo.Cuadro.ACTIVO 
						&& mapa.getCuadro(com.juegospichurria.actores.Cuadro.cuadroActivo.cuadro.getFila()-1,com.juegospichurria.actores.Cuadro.cuadroActivo.cuadro.getColumna()).getEstado() != com.juegospichurria.modelo.Cuadro.MUERE
						//&& mapa.getCuadro(com.juegospichurria.actores.Cuadro.cuadroActivo.cuadro.getFila()-1,com.juegospichurria.actores.Cuadro.cuadroActivo.cuadro.getColumna()).getEstado() != com.juegospichurria.modelo.Cuadro.PARED){
					){
					
					mapa.setCuadroEstado(com.juegospichurria.actores.Cuadro.cuadroActivo.cuadro.getFila()-1,com.juegospichurria.actores.Cuadro.cuadroActivo.cuadro.getColumna(), com.juegospichurria.modelo.Cuadro.INACTIVO);
				
				}
				
			}
			
			//Diagonales
			
			
			if(existeDerArriba()){
				
				if(existeActorCuadro(com.juegospichurria.actores.Cuadro.cuadroActivo.cuadro.getFila()+1,com.juegospichurria.actores.Cuadro.cuadroActivo.cuadro.getColumna()+1)){
					cantInactivos++;
					
					}
				
				if(mapa.getCuadro(com.juegospichurria.actores.Cuadro.cuadroActivo.cuadro.getFila()+1,com.juegospichurria.actores.Cuadro.cuadroActivo.cuadro.getColumna()+1).getEstado() != com.juegospichurria.modelo.Cuadro.ACTIVO 
						&& mapa.getCuadro(com.juegospichurria.actores.Cuadro.cuadroActivo.cuadro.getFila()+1,com.juegospichurria.actores.Cuadro.cuadroActivo.cuadro.getColumna()+1).getEstado() != com.juegospichurria.modelo.Cuadro.MUERE
					){
					
					mapa.setCuadroEstado(com.juegospichurria.actores.Cuadro.cuadroActivo.cuadro.getFila()+1,com.juegospichurria.actores.Cuadro.cuadroActivo.cuadro.getColumna()+1, com.juegospichurria.modelo.Cuadro.INACTIVO);
					
				}
			}
			
			if(existeDerAbajo()){
				if(existeActorCuadro(com.juegospichurria.actores.Cuadro.cuadroActivo.cuadro.getFila()-1,com.juegospichurria.actores.Cuadro.cuadroActivo.cuadro.getColumna()+1)){
					cantInactivos++;
					
					}
				
				if(mapa.getCuadro(com.juegospichurria.actores.Cuadro.cuadroActivo.cuadro.getFila()-1,com.juegospichurria.actores.Cuadro.cuadroActivo.cuadro.getColumna()+1).getEstado() != com.juegospichurria.modelo.Cuadro.ACTIVO 
						&& mapa.getCuadro(com.juegospichurria.actores.Cuadro.cuadroActivo.cuadro.getFila()-1,com.juegospichurria.actores.Cuadro.cuadroActivo.cuadro.getColumna()+1).getEstado() != com.juegospichurria.modelo.Cuadro.MUERE
					){
					
					
					mapa.setCuadroEstado(com.juegospichurria.actores.Cuadro.cuadroActivo.cuadro.getFila()-1,com.juegospichurria.actores.Cuadro.cuadroActivo.cuadro.getColumna()+1, com.juegospichurria.modelo.Cuadro.INACTIVO);
					
				}
			}
			
			if(existeIzqArriba()){

				if(existeActorCuadro(com.juegospichurria.actores.Cuadro.cuadroActivo.cuadro.getFila()+1,com.juegospichurria.actores.Cuadro.cuadroActivo.cuadro.getColumna()-1)){
				cantInactivos++;
				
				}
				if(mapa.getCuadro(com.juegospichurria.actores.Cuadro.cuadroActivo.cuadro.getFila()+1,com.juegospichurria.actores.Cuadro.cuadroActivo.cuadro.getColumna()-1).getEstado() != com.juegospichurria.modelo.Cuadro.ACTIVO 
						&& mapa.getCuadro(com.juegospichurria.actores.Cuadro.cuadroActivo.cuadro.getFila()+1,com.juegospichurria.actores.Cuadro.cuadroActivo.cuadro.getColumna()-1).getEstado() != com.juegospichurria.modelo.Cuadro.MUERE
					){
					
					mapa.setCuadroEstado(com.juegospichurria.actores.Cuadro.cuadroActivo.cuadro.getFila()+1,com.juegospichurria.actores.Cuadro.cuadroActivo.cuadro.getColumna()-1, com.juegospichurria.modelo.Cuadro.INACTIVO);
					
				}
			}
			
			if(existeIzqAbajo()){
				
				if(existeActorCuadro(com.juegospichurria.actores.Cuadro.cuadroActivo.cuadro.getFila()-1,com.juegospichurria.actores.Cuadro.cuadroActivo.cuadro.getColumna()-1)){
					cantInactivos++;
					
					}
				
				if(mapa.getCuadro(com.juegospichurria.actores.Cuadro.cuadroActivo.cuadro.getFila()-1,com.juegospichurria.actores.Cuadro.cuadroActivo.cuadro.getColumna()-1).getEstado() != com.juegospichurria.modelo.Cuadro.ACTIVO 
						&& mapa.getCuadro(com.juegospichurria.actores.Cuadro.cuadroActivo.cuadro.getFila()-1,com.juegospichurria.actores.Cuadro.cuadroActivo.cuadro.getColumna()-1).getEstado() != com.juegospichurria.modelo.Cuadro.MUERE
					){
					
					
					mapa.setCuadroEstado(com.juegospichurria.actores.Cuadro.cuadroActivo.cuadro.getFila()-1,com.juegospichurria.actores.Cuadro.cuadroActivo.cuadro.getColumna()-1, com.juegospichurria.modelo.Cuadro.INACTIVO);
					
				}
			}
			
			
	}
	
}
