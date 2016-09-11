package com.v5ent.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;


public class MainGameScreen implements Screen {

	private static final String TAG = MainGameScreen.class.getName();
	
	public MainGameScreen(){
		 	SCREEN_WITDH = Gdx.graphics.getWidth();
	        SCREEN_HEIGHT = Gdx.graphics.getHeight();
	        
	        //资源先加载
	        Assets.instance.init(new AssetManager());
	        // Initialize controller and renderer
	        worldController = new WorldController();
	        worldRenderer = new WorldRenderer(worldController);
	        // Game world is active on start
	        paused = false;
	}
	/**
	 * 专门用于Android的暂停键
	 */
	private boolean paused;
	public static int SCREEN_WITDH;
	public static int SCREEN_HEIGHT;
	
	private WorldController worldController;
	private WorldRenderer worldRenderer;
	
	
	@Override
	public void dispose() {
		Assets.instance.dispose();
    	worldRenderer.dispose();
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		 paused = true;
	}

	@Override
	public void render(float arg0) {
		// Do not update game world when paused.
        if (!paused) {
            // Update game world by the time that has passed
            // since last rendered frame.
            worldController.update(Gdx.graphics.getDeltaTime());
        }
        // Sets the clear screen color to: Cornflower Blue
        Gdx.gl.glClearColor(0x64 / 255.0f, 0x64 / 255.0f, 0x64 / 255.0f,
                0xff / 255.0f);
        // Clears the screen
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        // Render game world to screen
        worldRenderer.render();
	}

	@Override
	public void resize(int arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		Assets.instance.init(new AssetManager());
        paused = false;
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

}
