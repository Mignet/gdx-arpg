/**
 * Project Name:arpg-core
 * File Name:Player.java
 * Package Name:com.v5ent.game.entitys
 * Date:2014-7-26下午5:24:49
 * Copyright (c) 2014, DXTX All Rights Reserved.
 *
*/

package com.v5ent.game.model;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.v5ent.game.Assets;

/**
 * Player Character	 
 */
public class Player extends BaseEntity{

	private static final String TAG = Player.class.getName();
	
	public Vector2 getPosition() {
		return position;
	}

	public enum Direction {
		UP,DOWN,LEFT,RIGHT,
		UP_LEFT,UP_RIGHT,DOWN_LEFT,DOWN_RIGHT
	}
	public enum Status {
		STAND,
		WALK,
		FIGHT,
		MAGIC,
		BEAT,
		CRY,DIE,DEFEND,IDLE
	}
	
	public Direction dir = Direction.UP;
	public Status status = Status.IDLE;
	/**
	 * 帧数
	 */
	private static final int FRAME_COLS = 8;
	private static final int FRAME_ROWS = 8;
	Animation walkAnimation, standAnimation;
	TextureRegion[][] walkFrames, standFrames;
	TextureRegion currentFrame;
	float stateTime;

	/**
	 * Creates a new instance of Player.
	 * 行走图、等待图、战斗-物理攻击图、战斗-法术攻击图、战斗-挨打图、战斗-死亡图
	 * 8方向
	 * @param up 
	 */
	public Player(Direction dir){
		this.dir = dir;
		init();
	}
	
	public void init() {
//		position = new Vector2();
		walkFrames = new TextureRegion[FRAME_ROWS][FRAME_COLS];
		standFrames = new TextureRegion[FRAME_ROWS][FRAME_COLS];
		for (int i = 0; i < FRAME_ROWS; i++) {
			for (int j = 0; j < FRAME_COLS; j++) {
				walkFrames[j][i] = Assets.player_move[i][j];
				standFrames[j][i] = Assets.player_stand[i][j];
			}
		}
		standAnimation = new Animation(0.15f, standFrames[3]);
		stateTime = 0;
		currentFrame = standAnimation.getKeyFrame(stateTime, true);
	}
	public void move(float x,float y){
		this.status = Status.WALK;
		if(this.dir == Direction.DOWN){
			walkAnimation = new Animation(0.15f, walkFrames[0]);
			standAnimation = new Animation(0.15f, standFrames[0]);
		}
		if(this.dir == Direction.LEFT){
			walkAnimation = new Animation(0.15f, walkFrames[1]);
			standAnimation = new Animation(0.15f, standFrames[1]);
		}
		if(this.dir == Direction.RIGHT){
			walkAnimation = new Animation(0.15f, walkFrames[2]);
			standAnimation = new Animation(0.15f, standFrames[2]);
		}
		if(this.dir == Direction.UP){
			walkAnimation = new Animation(0.15f, walkFrames[3]);
			standAnimation = new Animation(0.15f, standFrames[3]);
		}
		if(this.dir == Direction.DOWN_LEFT){
			walkAnimation = new Animation(0.15f, walkFrames[4]);
			standAnimation = new Animation(0.15f, standFrames[4]);
		}
		if(this.dir == Direction.DOWN_RIGHT){
			walkAnimation = new Animation(0.15f, walkFrames[5]);
			standAnimation = new Animation(0.15f, standFrames[5]);
		}
		if(this.dir == Direction.UP_LEFT){
			walkAnimation = new Animation(0.15f, walkFrames[6]);
			standAnimation = new Animation(0.15f, standFrames[6]);
		}
		if(this.dir == Direction.UP_RIGHT){
			walkAnimation = new Animation(0.15f, walkFrames[7]);
			standAnimation = new Animation(0.15f, standFrames[7]);
		}
		//下左、下右、↖、↗
		this.translate(x, y);
//		Gdx.app.debug(TAG, "move key-("+x+","+y+")");
	}
	public void update(float deltaTime){
		stateTime += deltaTime;    
		if(this.status == Status.IDLE){
			currentFrame = standAnimation.getKeyFrame(stateTime, true);
		}else if(this.status == Status.WALK){
			currentFrame = walkAnimation.getKeyFrame(stateTime, true);
		}
		position.x = this.getX() + this.getOriginX();
		position.y = this.getY() + this.getOriginY();
	}
	
	@Override
	public void render(SpriteBatch batch) {
		//绘图从锚点开始,比如(0.5,0)
	    batch.draw(currentFrame,
	    		this.position.x-10,
	    		this.position.y-10);
	}

}

