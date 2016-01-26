package com.juegospichurria.modelo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.juegospichurria.qbox.qbox;

public abstract class Escena implements Screen {
	
	public static final int ANCHO = 320;
	public static final int ALTO = 480;
	public OrthographicCamera camara;
	public SpriteBatch batch;
	public com.juegospichurria.qbox.qbox qBox;
	public ScreenViewport vista;
	
	public Escena(com.juegospichurria.qbox.qbox qBox){
		this.qBox = qBox;
		camara  = new OrthographicCamera();
		camara.setToOrtho(false, qbox.Pantalla.ANCHO, qbox.Pantalla.ALTO);
	//	camara.position.set(new Vector3(qbox.Pantalla.ANCHO/2 , qbox.Pantalla.ALTO/2,0));
		
		batch = new SpriteBatch();
		vista = new ScreenViewport(camara);
		

		camara.update();
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camara.update();
		batch.setProjectionMatrix(camara.combined);
		
		
		actualizar(delta);
		pintar(delta);
	}
	
	public abstract void pintar(float delta);
	
	public abstract void actualizar(float delta);

	@Override
	public void resize(int width, int height) {
		
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		batch.dispose();
	}

}
