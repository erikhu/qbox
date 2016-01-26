package com.juegospichurria.modelo;

import com.badlogic.gdx.utils.Array;
import com.juegospichurria.visual.Celda;

public class Mapa {
	
	Array<com.juegospichurria.modelo.Cuadro> cuadros;
	public int contCuadros;
	public int numeroDeMapa;
	
	public Mapa(int numeroDeMapa){
		this.numeroDeMapa = numeroDeMapa;
		contCuadros = 0;
		cuadros = new Array<com.juegospichurria.modelo.Cuadro>();
	}
	
	public void AddCuadro(com.juegospichurria.modelo.Cuadro  cuadro){
	
		contCuadros++;
		cuadros.add(cuadro);
	}
	
	public void eliminarCuadro(int fila , int columna){
		int index = 0 ;
		for(com.juegospichurria.modelo.Cuadro cuadro : cuadros){
			if(cuadro.getFila() == fila  && cuadro.getColumna() == columna){
				cuadros.removeIndex(index);
			}
		}
	}
	
	public Array<com.juegospichurria.modelo.Cuadro> getCuadros(){
		return cuadros;
	}
	
	public com.juegospichurria.modelo.Cuadro getCuadro(Celda celda){
		for(com.juegospichurria.modelo.Cuadro cuadro : cuadros){
			if(cuadro.getCelda().equals(celda)){
				return cuadro;
			}
		}
		return null;
		
	}
	
	public com.juegospichurria.modelo.Cuadro getCuadro(int fila , int columna){
		for(com.juegospichurria.modelo.Cuadro cuadro : cuadros){
			if(cuadro.getFila() == fila  && cuadro.getColumna() == columna){
				return cuadro;
			}
		}
		return null;
	}
	
	public void setCuadroEstado(int fila , int columna, int estado){
		int cont = 0;
		for(com.juegospichurria.modelo.Cuadro cuadro : cuadros){
			if(cuadro.getFila() == fila  && cuadro.getColumna() == columna){
				cuadro.setEstado(estado);
				cuadros.set(cont, cuadro);
			}
			cont++;
		}
	}
	
	public boolean existeCuadro(int fila , int columna){
		for(Cuadro cuadro : cuadros){
			if(cuadro.getFila() == fila  && cuadro.getColumna() == columna){
				return true;
			}
		}
		return false;
	}
	
	

}
