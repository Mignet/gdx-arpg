/**
 * Project Name:arpg-core
 * File Name:Cursor.java
 * Package Name:com.v5ent.game.entitys
 * Date:2014-7-28下午11:52:15
 * Copyright (c) 2014, DXTX All Rights Reserved.
 *
*/

package com.v5ent.game.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.v5ent.game.Assets;

/**
 * 光标	 
 */
public class Cursor extends BaseEntity{

	private static final String TAG = Cursor.class.getName();
	
	private static final int FRAMES = 11;
	Animation curAnimation;
	private TextureRegion[] walkFrames;
	private TextureRegion currentFrame;
	
	public Cursor(){
		init();
	}
	float stateTime;
	
	private void init() {
		Gdx.app.debug(TAG, "---init cursor curAnimation by walkFrames---");
		walkFrames = new TextureRegion[FRAMES];
		for (int i = 0; i < FRAMES; i++) {
			walkFrames[i] = Assets.cursor[i];
		}
		curAnimation = new Animation(0.15f, walkFrames);
		stateTime = 0;
		currentFrame = curAnimation.getKeyFrame(stateTime, true);
	}
	
	public void update(float deltaTime){
		stateTime += deltaTime;
		currentFrame = curAnimation.getKeyFrame(stateTime, true);
	}

	@Override
	public void render(SpriteBatch batch) {
		batch.draw(currentFrame,this.getX()+15, this.getY());
	}
	
	public void move(float x, float y) {
		this.translate(x, y);
	}

}