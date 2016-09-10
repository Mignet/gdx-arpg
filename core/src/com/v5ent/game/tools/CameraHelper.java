/**
 * Project Name:arpg-core
 * File Name:CameraHelper.java
 * Package Name:com.v5ent.game.controller
 * Date:2014-7-26下午5:18:30
 * Copyright (c) 2014, DXTX All Rights Reserved.
 *
 */

package com.v5ent.game.tools;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

/**
 * 摄像机助手
 */
public class CameraHelper {
	private static final String TAG = CameraHelper.class.getName();
	private final float MAX_ZOOM_IN = 0.25f;
	private final float MAX_ZOOM_OUT = 10.0f;
	/**
	 * 摄像机坐标
	 */
	public Vector2 position;
	/**
	 * 视角比例，默认1.0
	 */
	private float zoom;
	/**
	 * 摄像机目标
	 */
	private Sprite target;

	public CameraHelper() {
		position = new Vector2();
		zoom = 1.0f;
	}
	
	/**
	 * 摄像机随时间间隔移动
	 */
	public void update(float deltaTime) {
		if (!hasTarget())
			return;
		position.x = target.getX() + target.getOriginX();
		position.y = target.getY() + target.getOriginY();
	}
	
	/**
	 * 是否有拍摄目标
	 */
	public boolean hasTarget(Sprite target) {
		return hasTarget() && this.target.equals(target);
	}

	/**
	 * 应用于摄像机
	 */
	public void applyTo(OrthographicCamera camera) {
		camera.position.x = position.x;
		camera.position.y = position.y;
		camera.zoom = zoom;
		camera.update();
	}
	
	public void setPosition(float x, float y) {
		this.position.set(x, y);
	}

	public Vector2 getPosition() {
		return position;
	}

	public void addZoom(float amount) {
		setZoom(zoom + amount);
	}

	public void setZoom(float zoom) {
		this.zoom = MathUtils.clamp(zoom, MAX_ZOOM_IN, MAX_ZOOM_OUT);
	}

	public float getZoom() {
		return zoom;
	}
	
	public void setTarget(Sprite target) {
		this.target = target;
	}

	public Sprite getTarget() {
		return target;
	}

	public boolean hasTarget() {
		return target != null;
	}

}
