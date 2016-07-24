/**
 * Project Name:arpg-core
 * File Name:Map.java
 * Package Name:com.v5ent.game.map
 * Date:2014-7-28下午10:02:28
 * Copyright (c) 2014, DXTX All Rights Reserved.
 *
*/

package com.v5ent.game.map;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.v5ent.game.Assets;
import com.v5ent.game.domain.Player;
import com.v5ent.game.tools.CameraHelper;
import com.v5ent.game.util.Constants;

/**
 * 场景
 */
public class Map{
	private static final String TAG = Map.class.getName();
	
	private String name;
	private Sprite bg;
	private Sprite bg_;
	private float x, y;
	float width, height;
	
	public int rows;
	public int cols;
	//cell point
	public List<Vector2> blockPoints = new ArrayList<Vector2>();
	private Vector2 startPosition;//游戏角色起始位置

	public Map(String mapId){
		this.name = mapId;
		bg = new Sprite(Assets.bg);
    	bg_ = new Sprite(Assets.bg_);
    	loadBlockData();
		this.width = bg.getWidth();
		this.height = bg.getHeight();
		rows = (int)Math.ceil(width/Constants.CELL_SIZE);
    	cols = (int)Math.ceil(height/Constants.CELL_SIZE);
	}
	
	private void loadBlockData() {
		FileHandle file = Gdx.files.local(this.getName()+".map");
		if(file.exists()){
			String s = file.readString();
			for(String p:s.split(":")){
				blockPoints.add(new Vector2(Integer.valueOf(p.split(",")[0]).floatValue(),Integer.valueOf(p.split(",")[1]).floatValue()));
			}
		}
	}

	/**
	 * drawBackground
	 * @param batch
	 */
	public void drawBackground(SpriteBatch batch){
		batch.draw(bg, x, y);
	}
	/**
	 * drawFrontground
	 * @param batch
	 */
	public void drawFrontground(SpriteBatch batch){
		batch.draw(bg_, x, y);
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public float getX() {
		return x;
	}
	public void setX(float x) {
		this.x = x;
	}
	public float getY() {
		return y;
	}
	public void setY(float y) {
		this.y = y;
	}
	public float getWidth() {
		return width;
	}
	public void setWidth(float width) {
		this.width = width;
	}
	public float getHeight() {
		return height;
	}
	public void setHeight(float height) {
		this.height = height;
	}

	public Vector2 getStartPosition() {
		return startPosition;
	}

	public void setStartPosition(Vector2 startPosition) {
		this.startPosition = startPosition;
	}

	/**
	 * 放置player
	 * @param player
	 */
	public void locationPlayer(Player player,CameraHelper camera) {
		float px=0,py=0;
		this.x = -startPosition.x*Constants.CELL_SIZE;
		this.y = -startPosition.y*Constants.CELL_SIZE;
		if(startPosition.x*Constants.CELL_SIZE<Constants.VIEWPORT_WIDTH/2){
			this.x  = -Constants.VIEWPORT_WIDTH/2;
		}
		if(this.getWidth()-startPosition.x*Constants.CELL_SIZE<Constants.VIEWPORT_WIDTH/2){
			this.x  = -this.getWidth()+Constants.VIEWPORT_WIDTH;
		}
		if(startPosition.y*Constants.CELL_SIZE<Constants.VIEWPORT_HEIGHT/2){
			this.y  = -Constants.VIEWPORT_HEIGHT/2;
		}
		if(this.getHeight()-startPosition.y*Constants.CELL_SIZE<Constants.VIEWPORT_HEIGHT/2){
			this.y  = -this.getHeight() + Constants.VIEWPORT_HEIGHT/2;
		}
		// x -400 y -320 offsetCameraByMap
		camera.position.x = -this.x + Constants.VIEWPORT_WIDTH/2;
		camera.position.y = -this.y + Constants.VIEWPORT_HEIGHT/2;
		this.x = -Constants.VIEWPORT_WIDTH/2;
		this.y = -Constants.VIEWPORT_HEIGHT/2;
		player.setPosition(this.x+startPosition.x*Constants.CELL_SIZE,this.y+startPosition.y*Constants.CELL_SIZE);
		//根据player在地图上的位置，作为反向计算地图显示位置的依据。
    	bg.setPosition(x,y);
    	bg_.setPosition(x, y);
    	//offsetCameraByMap every second
//    	offsetCameraByMap(camera);
	}
	/**
	 * 在地图上的坐标
	 * @param point
	 * @param camara
	 * @return
	 */
	public Vector2 getMapCellPostion(Vector2 point){
		int x = 
				Math.round((point.x-this.x)/Constants.CELL_SIZE);
		int y = 
				Math.round((point.y-this.y)/Constants.CELL_SIZE);
		return new Vector2(x,y);
	}

	/**是否阻塞*/
	public boolean isBlocked(Vector2 p){
		return blockPoints.contains(p);
	}
	/**
	 * 地图编辑器<br>
	 * 获取地图坐标，加上camara
	 * @param p
	 */
	public void markBlock(Vector2 p,Vector2 camara) {
		Gdx.app.debug(TAG, "map x:"+p.x+",y:"+p.y);
		Vector2 m = this.getMapCellPostion(p);
		Vector2 v = new Vector2(Math.round((m.x * Constants.CELL_SIZE+camara.x)/Constants.CELL_SIZE)
				,Math.round((m.y * Constants.CELL_SIZE+camara.y)/Constants.CELL_SIZE));
		Gdx.app.debug(TAG, "map x:"+v.x+",y:"+v.y);
		if(blockPoints.contains(v)){
			Gdx.app.debug(TAG, "remove x:"+v.x+",y:"+v.y);
//			blockPoints.remove(v);
			Iterator<Vector2> iter = blockPoints.iterator();
			while(iter.hasNext()){
				Vector2 b = iter.next();
				if(b.equals(v)){
					iter.remove();
				}
			}
		}else{
			Gdx.app.debug(TAG, "add x:"+v.x+",y:"+v.y);
			blockPoints.add(v);
		}
	}

	 /**
     * 
     * offset:make camera always in map
     * @author dxtx
     * @param width
     * @param height
     * @param cam
     * @since JDK 1.6
     */
	public void offsetCameraByMap(CameraHelper cam){
		// These values likely need to be scaled according to your world coordinates.
		// The left boundary of the map (x)
		float mapLeft = -Constants.VIEWPORT_WIDTH/2;
		// The right boundary of the map (x + width)
		float mapRight = -Constants.VIEWPORT_WIDTH/2 + width;
		// The bottom boundary of the map (y)
		float mapBottom = -Constants.VIEWPORT_HEIGHT/2;
		// The top boundary of the map (y + height)
		float mapTop = -Constants.VIEWPORT_HEIGHT/2 + height;
		// The camera dimensions, halved
		float cameraHalfWidth = Constants.VIEWPORT_WIDTH * .5f;
		float cameraHalfHeight = Constants.VIEWPORT_HEIGHT * .5f;

		// Move camera after player as normal

		float cameraLeft = cam.position.x - cameraHalfWidth;
		float cameraRight = cam.position.x + cameraHalfWidth;
		float cameraBottom = cam.position.y - cameraHalfHeight;
		float cameraTop = cam.position.y + cameraHalfHeight;

		// Horizontal axis
		if(width < Constants.VIEWPORT_WIDTH)
		{
		    cam.position.x = mapRight / 2;
		}
		else if(cameraLeft <= mapLeft)
		{
		    cam.position.x = mapLeft + cameraHalfWidth;
		}
		else if(cameraRight >= mapRight)
		{
		    cam.position.x = mapRight - cameraHalfWidth;
		}

		// Vertical axis
		if(height < Constants.VIEWPORT_HEIGHT)
		{
		    cam.position.y = mapTop / 2;
		}
		else if(cameraBottom <= mapBottom)
		{
		    cam.position.y = mapBottom + cameraHalfHeight;
		}
		else if(cameraTop >= mapTop)
		{
		    cam.position.y = mapTop - cameraHalfHeight;
		}
	}

}

