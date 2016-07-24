package com.v5ent.game.test;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.v5ent.game.util.Constants;
import com.v5ent.test.PolygonTest;

public class Startup {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Test";
		//1280 x 720 HD
		config.width = Constants.SCREEN_WIDTH;
		config.height = Constants.SCREEN_HEIGHT;
		config.fullscreen = false;
		config.vSyncEnabled = true;
		new LwjglApplication(new PolygonTest(), config);
	}
}
