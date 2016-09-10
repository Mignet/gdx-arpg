/**
 * Project Name:arpg-core
 * File Name:AbstractObject.java
 * Package Name:com.v5ent.game.entitys
 * Date:2014-7-26下午5:23:23
 * Copyright (c) 2014, DXTX All Rights Reserved.
 *
*/

package com.v5ent.game.model;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * 	 对象基础类
 */
public abstract class BaseEntity extends Sprite{
	/**速度，极限速度，摩擦力，加速度和边界。*/
	public Vector2 velocity;
	public Vector2 terminalVelocity;
	public Vector2 friction;
	public Vector2 acceleration;
	public Rectangle bounds;
	
	/**
	 * 位置
	 */
    public Vector2 position;
    /**
     * 大小
     */
    public Vector2 dimension;
    /**
     * 原点
     */
    public Vector2 origin;
    /**
     * 缩放
     */
    public Vector2 scale;
    /**
     * 旋转度
     */
    public float rotation;

    public BaseEntity() {
        position = new Vector2();
        dimension = new Vector2(1, 1);
        origin = new Vector2();
        scale = new Vector2(1, 1);
        rotation = 0;
    }

    public void update(float deltaTime) {
    }

    /**
     * 画自己
     */
    public abstract void render(SpriteBatch batch);
}
