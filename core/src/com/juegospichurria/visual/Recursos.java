package com.juegospichurria.visual;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Recursos {

	
	private static Recursos recursos;
	
	TextureAtlas atlas , atlasSubmenu, menu;
	public Sound toca;
	public Sound entra;
	public Sound gana;
	public Sound pierde;
	
	public Recursos(){
	
		entra = Gdx.audio.newSound(Gdx.files.internal("sonido/entra_mapa.wav"));
		toca = Gdx.audio.newSound(Gdx.files.internal("sonido/cuadro_juego.wav"));
		
		gana = Gdx.audio.newSound(Gdx.files.internal("sonido/gano.ogg"));
		gana.setLooping(1,false);
		
		pierde = Gdx.audio.newSound(Gdx.files.internal("sonido/pierde.wav"));
		pierde.setLooping(2,false);
		
		atlas = new TextureAtlas("imagenes/cuadros.atlas");		
		atlasSubmenu = new TextureAtlas("imagenes/submenu.atlas");
		menu = new TextureAtlas("imagenes/menu/menu.atlas");
		
	}
	
	public static Recursos getRecursos(){
		if(recursos == null){
			recursos = new Recursos();
			return recursos;
		}
		return recursos;
	}
	
	
	public TextureRegion getCuadroColor(String color){
		return atlas.findRegion(color);
	}
	
	public TextureRegion getImagenSubmenu(String imagen){
		return atlasSubmenu.findRegion(imagen);
	}
	
	public TextureRegion getImagenMenu(String imagen){
		return menu.findRegion(imagen);
	}
	
	public TextureRegion getCargaImagenes(String nombre){
		return new TextureRegion(new Texture(nombre));
	}
	
	public static void cargarMapas(){
		cargarMapa("mapa0",0);
	}
	
	public static com.juegospichurria.actores.Mapa cargarMapa(String nombreMapa, int numero){
		FileHandle txt = Gdx.files.internal("mapas/"+nombreMapa+".txt");
		com.juegospichurria.modelo.Mapa mapa = new com.juegospichurria.modelo.Mapa(numero);
		int filas = 0 ; 
		int columnas = 0;
		int filaActivo = 0 ;
		int columnaActivo = 0;
		int cuadros = 0;		
		String temp = txt.readString();
		String[] filass =temp.split("\n");
		
		int filaInicial = 2;
		int columnaInicial = 1;
		
		
		for(int i = 0  ; i<filass.length;i++){
			
			char[] c = filass[i].toCharArray();
				for(int i2 = 0; i2< c.length ;i2++){
					cuadros++;
					if(c[i2] == '#' ){
						mapa.AddCuadro(new com.juegospichurria.modelo.Cuadro(new com.juegospichurria.visual.Celda(filaInicial+(filass.length-i),columnaInicial+i2)));
						System.out.println("Se creo cuadro en la fila :  "+i + " y el la columna: "+i2);
					}else if(c[i2]=='@'){
						filaActivo =i;
						columnaActivo = i2;
						com.juegospichurria.modelo.Cuadro cuadroInicial = new com.juegospichurria.modelo.Cuadro(new com.juegospichurria.visual.Celda(filaInicial+(filass.length-i),columnaInicial+i2));
						cuadroInicial.setEstado(com.juegospichurria.modelo.Cuadro.ACTIVO);
						mapa.AddCuadro(cuadroInicial);
						System.out.println("Se cre activo en la fila :  "+filaActivo + " y el la columna: "+columnaActivo);
					
					}else if(c[i2]=='P'){
					
						com.juegospichurria.modelo.Cuadro cuadroInicial = new com.juegospichurria.modelo.Cuadro(new com.juegospichurria.visual.Celda(filaInicial+(filass.length-i),columnaInicial+i2));
						cuadroInicial.setEstado(com.juegospichurria.modelo.Cuadro.PARED);
						mapa.AddCuadro(cuadroInicial);
						mapa.contCuadros--;
						System.out.println("Se cre activo en la fila :  "+filaActivo + " y el la columna: "+columnaActivo);
					
					}
					if(filas<1){
					columnas++;
					}
				}
				filas++;
		}
		
		com.juegospichurria.actores.Mapa mapaActor = new com.juegospichurria.actores.Mapa(mapa, filaActivo, columnaActivo);
		System.out.println("FIlas: "+filas + " Columas : " + columnas+" Cuadros: "+cuadros );
		return mapaActor;
		
	//	System.out.println("FIlas: "+filas + " Columas : " + columnas);
		
		
	}
	
	
	
}
