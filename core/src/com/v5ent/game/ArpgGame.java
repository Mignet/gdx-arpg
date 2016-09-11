package com.v5ent.game;

import com.badlogic.gdx.Game;

public class ArpgGame extends Game {
	
	private static MainGameScreen _mainGameScreen;
	
	private static final String TAG = ArpgGame.class.getName();
	
	@Override
    public void create() {
		_mainGameScreen = new MainGameScreen();
		setScreen(_mainGameScreen);
       
    }

    @Override
    public void dispose() {
    	_mainGameScreen.dispose();
    }
}
