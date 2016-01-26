package com.juegospichurria.qbox;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.juegospichurria.modelo.Escena;
import com.juegospichurria.visual.Recursos;

public class Juego extends Escena {


	com.juegospichurria.actores.Mapa mapa;
	com.juegospichurria.actores.Mapa mapaahora;
	Stage estado;
	float tiempo ;
	BitmapFont fuente;
	public static boolean pierde;
	public static boolean gana;
	public static boolean menu;
	boolean condicion;
	public static int mapaid;
	public static  int mapaActual;
	
	public Juego(qbox qBox, int nivel) {
		super(qBox);
		fuente = new BitmapFont();
		fuente.setColor(1, 1, 1,1);
		tiempo = 0 ;
		condicion = true;
		mapaActual = nivel;
		iniciarMapa(mapaActual);
		menu = false;
		
	
	}
	
	public void iniciarMapa(int mapa){
		try{
		this.mapa = Recursos.cargarMapa(("mapa"+mapa),mapa);
		}catch(Exception e){
			System.out.println("No inicio");
			condicion = false;
			Menu.preferencias.putInteger("niveles", mapaActual);
			Menu.preferencias.flush();
			qBox.setScreen(new Menu(qBox));
			
		}
	}

	@Override
	public void pintar(float delta) {

		mapa.pintar(batch, tiempo,vista);
	}

	@Override
	public void actualizar(float delta) {
		
		Gdx.input.setCatchBackKey(true);
		
		if(mapaActual<25){
		Gdx.gl.glClearColor(0, 0,0,0);
		}else if(mapaActual>=25){
			Gdx.gl.glClearColor(.4f,0f,.8f,0);
		}
		
		tiempo+= delta;
		
		if(gana){
			mapaActual++;
			
			
				Menu.preferencias.putInteger("niveles", mapaActual+1);
				Menu.preferencias.flush();
			
			iniciarMapa(mapaActual);
			gana  = false;
		}
		
		if(pierde){
			iniciarMapa(mapaActual);
			pierde = false;
		}
		
		if(menu || Gdx.input.isKeyPressed(Input.Keys.BACK)){
			qBox.setScreen(new Menu(qBox));
			//qBox.setScreen(qbox.menu);
			menu = false;
			Gdx.input.setCatchBackKey(false);
			
		}
		
		mapa.actualizar(tiempo);
		
	}

	

}
