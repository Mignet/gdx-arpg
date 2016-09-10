/**
 * Project Name:arpg-core
 * File Name:WorldController.java
 * Package Name:com.v5ent.game.controller
 * Date:2014-7-26下午5:14:55
 * Copyright (c) 2014, DXTX All Rights Reserved.
 *
*/

package com.v5ent.game;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Vector2;
import com.v5ent.game.map.Map;
import com.v5ent.game.model.Cursor;
import com.v5ent.game.model.Player;
import com.v5ent.game.model.Player.Direction;
import com.v5ent.game.model.Player.Status;
import com.v5ent.game.skill.Skill;
import com.v5ent.game.tools.CameraHelper;
import com.v5ent.game.util.Constants;
import com.v5ent.game.util.Trace;

/**
 * 控制器
 */
public class WorldController extends InputAdapter{
	/**
	 * 日志标签
	 */
	private static final String TAG = WorldController.class.getName();
	
	public Player player;
    public CameraHelper cameraHelper;

    public WorldController() {
        init();
    }
    
    /**
     * 所有可能控制的对象
     */
    private void init() {
    	Gdx.input.setInputProcessor(this);
    	initAllObjects();
        Gdx.input.setCursorCatched(true);
    }
    
    public Skill skill;
    public Cursor cursor;
    public Map map;

    private void initAllObjects() {
    	cameraHelper = new CameraHelper();
    	player = new Player(Direction.UP);
    	//设定跟踪目标
    	cameraHelper.setTarget(player);
    	//地图
        map = new Map("001");
        map.setStartPosition(new Vector2(10,20));
        map.locationPlayer(player,cameraHelper);
        //GUI
        cursor = new Cursor();
        //将鼠标放屏幕中间偏右
        Gdx.input.setCursorPosition((int)Constants.VIEWPORT_WIDTH/2,(int)Constants.VIEWPORT_HEIGHT/2);
        cursor.setPosition(0, 0);
        Gdx.app.debug(TAG, "set Cursor at:("+cursor.getX()+","+cursor.getY()+")");
    }
    
    /**
     * 表示控制器每个单位间隔<b>deltaTime</b>所做的操作
     */
    public void update(float deltaTime) {
    	//处理按键输入(持续动作)
    	handleDebugInput(deltaTime);
        player.update(deltaTime);
        if(skill!=null)skill.update(deltaTime);
        if(skill!=null&&skill.isEnded())skill = null;
        
        cursor.update(deltaTime);
        cameraHelper.update(deltaTime);
        //offset position
        map.offsetCameraByMap(cameraHelper);
    }
    
    /**主角移动速度**/
    private float sprMoveSpeed = 5;
    /**主角分方向移动速度**/
    private float sprSpliteMoveSpeed = 5/1.414f;
    private float camMoveSpeed = 1;
    private float camMoveSpeedAccelerationFactor = 4;
    private float camZoomSpeed = 0.4f;
    private float camZoomSpeedAccelerationFactor = 2;
    private void handleDebugInput(float deltaTime) {
        if (Gdx.app.getType() != ApplicationType.Desktop)
            return;
        
        // Selected Sprite Controls
        //约定：持续动作在update里，瞬时动作用监听器
        Vector2 p = map.getMapCellPostion(player.getPosition());
        if (Gdx.input.isKeyPressed(Keys.A)&&!Gdx.input.isKeyPressed(Keys.D)&&!Gdx.input.isKeyPressed(Keys.W)&&!Gdx.input.isKeyPressed(Keys.S)){
        	player.dir = Direction.LEFT;
        	if(!map.isBlocked(new Vector2(p.x-1,p.y))){
        		player.move(-sprMoveSpeed, 0);
        	}
        }
        if (Gdx.input.isKeyPressed(Keys.D)&&!Gdx.input.isKeyPressed(Keys.A)&&!Gdx.input.isKeyPressed(Keys.W)&&!Gdx.input.isKeyPressed(Keys.S)){
        	player.dir = Direction.RIGHT;
        	if(!map.isBlocked(new Vector2(p.x+1,p.y))){
        		player.move(sprMoveSpeed, 0);
        	}
        }
        if (Gdx.input.isKeyPressed(Keys.W)&&!Gdx.input.isKeyPressed(Keys.S)&&!Gdx.input.isKeyPressed(Keys.A)&&!Gdx.input.isKeyPressed(Keys.D)){
        	player.dir = Direction.UP;
        	if(!map.isBlocked(new Vector2(p.x,p.y+1))){
        		player.move(0, sprMoveSpeed);
        	}
        }
        if (Gdx.input.isKeyPressed(Keys.S)&&!Gdx.input.isKeyPressed(Keys.W)&&!Gdx.input.isKeyPressed(Keys.A)&&!Gdx.input.isKeyPressed(Keys.D)){
        	player.dir = Direction.DOWN;
        	if(!map.isBlocked(new Vector2(p.x,p.y-1))){
        		player.move(0, -sprMoveSpeed);
        	}
        }
        if (Gdx.input.isKeyPressed(Keys.A)&&Gdx.input.isKeyPressed(Keys.W)&&!Gdx.input.isKeyPressed(Keys.S)&&!Gdx.input.isKeyPressed(Keys.D)){
        	player.dir = Direction.UP_LEFT;
        	if(!map.isBlocked(new Vector2(p.x-1,p.y+1))){
        		player.move(-sprSpliteMoveSpeed, sprSpliteMoveSpeed);
        	}
        }
        if (Gdx.input.isKeyPressed(Keys.D)&&Gdx.input.isKeyPressed(Keys.W)&&!Gdx.input.isKeyPressed(Keys.A)&&!Gdx.input.isKeyPressed(Keys.S)){
        	player.dir = Direction.UP_RIGHT;
        	if(!map.isBlocked(new Vector2(p.x+1,p.y+1))){
        		player.move(sprSpliteMoveSpeed, sprSpliteMoveSpeed);
        	}
        }
        if (Gdx.input.isKeyPressed(Keys.A)&&Gdx.input.isKeyPressed(Keys.S)&&!Gdx.input.isKeyPressed(Keys.W)&&!Gdx.input.isKeyPressed(Keys.D)){
        	player.dir = Direction.DOWN_LEFT;
        	if(!map.isBlocked(new Vector2(p.x-1,p.y-1))){
        		player.move(-sprSpliteMoveSpeed, -sprSpliteMoveSpeed);
        	}
        }
        if (Gdx.input.isKeyPressed(Keys.D)&&Gdx.input.isKeyPressed(Keys.S)&&!Gdx.input.isKeyPressed(Keys.A)&&!Gdx.input.isKeyPressed(Keys.W)){
        	player.dir = Direction.DOWN_RIGHT;
        	if(!map.isBlocked(new Vector2(p.x+1,p.y-1))){
        		player.move(sprSpliteMoveSpeed, -sprSpliteMoveSpeed);
        	}
        }
		// 行动键按下，切换为行动。都抬起了，切换为待机。
		if (Gdx.input.isKeyPressed(Keys.W) || Gdx.input.isKeyPressed(Keys.S)
				|| Gdx.input.isKeyPressed(Keys.A)
				|| Gdx.input.isKeyPressed(Keys.D)) {
			player.status = Status.WALK;
		} else {
			player.status = Status.IDLE;
		}
        
        // Camera Controls (move)
        if (Gdx.input.isKeyPressed(Keys.M)){
            camMoveSpeed *= camMoveSpeedAccelerationFactor;
        }
        if (Gdx.input.isKeyPressed(Keys.LEFT)){
            moveCamera(-camMoveSpeed, 0);
        }
        if (Gdx.input.isKeyPressed(Keys.RIGHT))
            moveCamera(camMoveSpeed, 0);
        if (Gdx.input.isKeyPressed(Keys.UP))
            moveCamera(0, camMoveSpeed);
        if (Gdx.input.isKeyPressed(Keys.DOWN))
            moveCamera(0, -camMoveSpeed);
        if (Gdx.input.isKeyPressed(Keys.BACKSPACE))
            cameraHelper.setPosition(0, 0);
        
        // Camera Controls (zoom)
        if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT))
            camZoomSpeed *= camZoomSpeedAccelerationFactor;
        if (Gdx.input.isKeyPressed(Keys.COMMA))
            cameraHelper.addZoom(camZoomSpeed);
        if (Gdx.input.isKeyPressed(Keys.PERIOD))
            cameraHelper.addZoom(-camZoomSpeed);
        if (Gdx.input.isKeyPressed(Keys.SLASH))
            cameraHelper.setZoom(1);
    }
    
    private void moveCamera(float x, float y) {
        x += cameraHelper.getPosition().x;
        y += cameraHelper.getPosition().y;
        cameraHelper.setPosition(x, y);
    }
    boolean alt = false;
	@Override
	public boolean keyUp(int keycode) {
		// Reset game world
        if (keycode == Keys.R) {
            init();
            Gdx.app.debug(TAG, "Game world resetted");
        }else if(keycode == Keys.Q){
        	Gdx.app.exit();
        }
        if (keycode == Keys.ALT_RIGHT) {
        	alt = false;
        }
        if  ( keycode  ==  Keys.ENTER  &&   alt  ) {
                      //   do something......
        	Gdx.app.debug(TAG, "Game world FullScreen");
        	boolean fullscreen = Gdx.graphics.isFullscreen(); //设置全屏和垂直同步
        	if(!fullscreen){
        		// 设置分辨率和全屏 
        		Gdx.graphics.setDisplayMode(Gdx.graphics.getDesktopDisplayMode().width, Gdx.graphics.getDesktopDisplayMode().height, true);
        		//垂直同步设置：
//        		Gdx.graphics.setVSync(true);
        	}else{
        		Gdx.graphics.setDisplayMode(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT, false);
        	}
         }
        // 保存编辑的地图数据
        else if (keycode == Keys.SPACE) {
        	FileHandle file = Gdx.files.local(map.getName()+".map");
        	StringBuilder sb = new StringBuilder();
        	for(Vector2 p:map.blockPoints){
        		sb.append((int)p.x+","+(int)p.y+":");
        	}
        	file.writeString(sb.toString(),false);
        	Gdx.app.debug(TAG, "map file saved!");
        }/*else if(keycode == Keys.ENTER){
        	cameraHelper.setTarget(cameraHelper.hasTarget() ? null : player);
            Gdx.app.debug(TAG, "Camera follow enabled: " + cameraHelper.hasTarget());
        }*/
        return false;
	}
	
	@Override
	public boolean keyTyped (char character) {
		return false;
	}
	
	@Override
	public boolean keyDown(int keycode) {
		 if (keycode == Keys.ALT_RIGHT) {
			 alt = true;
		}
		return false;
	}
	
	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		cursor.setPosition(Gdx.input.getX()-Constants.VIEWPORT_WIDTH/2, -Gdx.input.getY()+Constants.VIEWPORT_HEIGHT/2);
//		Gdx.app.debug(TAG, "Cursor Moved:("+cursor.getX()+","+cursor.getY()+")=>Screen("+screenX+","+screenY+")");
		return false;
	}

	@Override
	public boolean touchDown(int x, int y, int pointer, int button) {
		Gdx.app.debug(TAG, "pointer START:"+pointer+"=>("+x+","+y+")=>Cursor:("+cursor.getX()+","+cursor.getY()+")");
		startPoint.set(x, y);
		return false;
	}

	@Override
	public boolean touchUp(int x, int y, int pointer, int button) {
		//屏幕点击的坐标系是左上角
		Gdx.app.debug(TAG, "pointer END:"+pointer+"=>("+x+","+y+")=>Cursor:("+cursor.getX()+","+cursor.getY()+")");
		endPoint.set(x,y);
		if(startPoint.equals(endPoint)){
			//地图编辑
			map.markBlock(new Vector2(x-Constants.SCREEN_WIDTH/2-cursor.getWidth()/2,cursor.getHeight()/2+-y+Constants.SCREEN_HEIGHT/2),cameraHelper.getPosition());
		}
		//判断是否施法成功
		Trace.symbol(this,points,player);
		points.clear();
		return false;
	}

	/**轨迹点*/
	List<Vector2> points = new ArrayList<Vector2>();
	Vector2 startPoint = new Vector2(),endPoint = new Vector2();
	
	@Override
	public boolean touchDragged(int x, int y, int pointer) {
		cursor.move(Gdx.input.getDeltaX(), -Gdx.input.getDeltaY());
//		Gdx.app.debug(TAG, "pointer:"+pointer+"=>("+x+","+y+")=>Cursor:("+cursor.getX()+","+cursor.getY()+")");
		points.add(new Vector2(cursor.getX(),cursor.getY()));
		return false;
	}

}

