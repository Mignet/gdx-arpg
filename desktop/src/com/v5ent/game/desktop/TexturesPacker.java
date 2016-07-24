/**
 * Project Name:arpg-core
 * File Name:Packer.java
 * Package Name:com.v5ent.game.util
 * Date:2014-7-26下午9:50:23
 * Copyright (c) 2014, DXTX All Rights Reserved.
 *
*/

package com.v5ent.game.desktop;

import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import com.badlogic.gdx.tools.texturepacker.TexturePacker.Settings;

/**
 * Package	 
 */
public class TexturesPacker {
	public static void main(String[] args) {
		Settings settings = new Settings();
		//2的倍数
        settings.maxWidth = 1024;//109*101
        settings.maxHeight = 1024;
        settings.debug = true;
		TexturePacker.process(settings, "assets/gjl/move", "assets/gjl", "gjl-move.pack");
	}
}

