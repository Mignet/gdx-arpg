package com.v5ent.game.desktop;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.v5ent.game.ArpgGame;
import com.v5ent.game.util.Constants;

public class ArpgLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		

 LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration(); 
// cfg.width = 1280; 
// cfg.height = 720; 
 // 全屏 
// cfg.fullscreen = true; 
 // 垂直同步 
// cfg.vSyncEnabled = true; 
 
		config.title = "ARPG";
		//1280 x 720 HD
		config.width = Constants.SCREEN_WIDTH;
		config.height = Constants.SCREEN_HEIGHT;
		config.fullscreen = false;
		config.vSyncEnabled = true;
		new LwjglApplication(new ArpgGame(), config);
		 // Set Libgdx log level to DEBUG
        Gdx.app.setLogLevel(Application.LOG_DEBUG);
	}
}
