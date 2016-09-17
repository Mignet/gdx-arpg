package com.v5ent.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class ArpgGame extends Game {
	
	private static MainGameScreen mainGameScreen;
	
	private static final String TAG = ArpgGame.class.getName();
	
	@Override
    public void create() {
		Preferences prefs = Gdx.app.getPreferences("config.ini");
		prefs.putBoolean("EditMode", false);
		prefs.flush();
		mainGameScreen = new MainGameScreen();
		setScreen(mainGameScreen);
    }

    @Override
    public void dispose() {
    	mainGameScreen.dispose();
    }
}
