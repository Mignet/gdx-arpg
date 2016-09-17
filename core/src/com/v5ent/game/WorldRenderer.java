/**
 * Project Name:arpg-core
 * File Name:WorldRenderer.java
 * Package Name:com.v5ent.game.renderer
 * Date:2014-7-26下午5:16:37
 * Copyright (c) 2014, DXTX All Rights Reserved.
 *
*/

package com.v5ent.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import com.v5ent.game.util.Constants;
import com.v5ent.game.util.DebugMarker;
import com.v5ent.game.util.PolygonUtil;

/**
 * 视图(渲染)层
 */
public class WorldRenderer implements Disposable {
	/**
	 * 日志标签
	 */
	private static final String TAG = WorldRenderer.class.getName();
	/**
	 * 正交投影仪
	 * 笛卡尔坐标系，坐标原点
	 */
    private static OrthographicCamera camera;
    
    /**
     * GUI
     */
    private OrthographicCamera cameraGUI;
    
    /**
     * 批量绘制刷
     */
    private SpriteBatch batch;
    private WorldController worldController;

    public WorldRenderer(WorldController worldController) {
        this.worldController = worldController;
        init();
    }
/**
 * 画矩形
 */
    Polygon polygon1, polygon2;
    float[] vertices1, vertices2;
    Vector2 point = new Vector2(100, 50);
    
    private void init() {
        batch = new SpriteBatch();
        camera = new OrthographicCamera(Constants.VIEWPORT_WIDTH,Constants.VIEWPORT_HEIGHT);
        //camera.translate(Constants.VIEWPORT_WIDTH/2,Constants.VIEWPORT_HEIGHT/2);
        camera.position.set(0, 0, 0);
        camera.update();
        
        //GUI
        cameraGUI = new OrthographicCamera(Constants.VIEWPORT_GUI_WIDTH, Constants.VIEWPORT_GUI_HEIGHT);
        cameraGUI.position.set(0, 0, 0);
        cameraGUI.update();
        //字体画笔
        font = Assets.font;
        Gdx.app.debug(TAG, "renderer init complete.");
        //
        polygon1 = new Polygon();
        vertices1 = new float[]{ 100, 100, 200, 100, 300, 300, 100, 200 };
        polygon1.setVertices(vertices1);
        
        vertices2 = new float[]{ 100, 50, 200, 70, 300, 150, 150, 100};
        polygon2 = new Polygon(vertices2);
    }

    public void render() {
    	worldController.cameraHelper.applyTo(camera);
    	renderAllObjects(batch);
//    	renderMapConlision(batch);
//    	DebugMarker.drawMapGrid(worldController.map,camera.combined);
    	Vector2 p = worldController.map.getMapCellPostion(worldController.player.getPosition());
//    	DebugMarker.drawCell(worldController.map, p, camera.combined);
    	renderGui(batch);
    }
    /**
     * 测试，绘制碰撞矩形
     * @param batch
     */
    private void renderMapConlision(SpriteBatch batch) {
    	ShapeRenderer rend = DebugMarker.getDebugRenderer();
    	rend.setProjectionMatrix(camera.combined);
    	rend.begin(ShapeType.Line);
        rend.setColor(Color.RED);
        for(int i=0; i<polygon1.getVertices().length; i+=2){
            rend.line(vertices1[i], vertices1[i+1], vertices1[(i+2)%vertices1.length], vertices1[(i+3)%vertices1.length]);
        }
        float[] vertices3 = polygon2.getVertices();
        for(int i=0; i<polygon2.getVertices().length; i+=2){
            rend.line(vertices3[i], vertices3[i+1], vertices3[(i+2)%vertices3.length], vertices3[(i+3)%vertices3.length]);
        }
        rend.end();
        
        //if(polygon1.contains(point.x, point.y)){
        if( PolygonUtil.isOverlap(polygon1, polygon2) ){
            rend.setColor(Color.RED);
        }else{
            rend.setColor(Color.BLUE);
        }
        rend.begin(ShapeType.Filled);
        rend.circle(point.x, point.y, 5);
        rend.end();
	}
    
	private void renderGui(SpriteBatch batch) {
    	batch.setProjectionMatrix(cameraGUI.combined);
        batch.begin();
        // draw collected gold coins icon + text
        // (anchored to top left edge)
        renderGuiScore(batch);
        worldController.cursor.render(batch);
        if(!worldController.points.isEmpty()){
        	DebugMarker.drawTrace(worldController.points, cameraGUI.combined);
        }
        batch.end();
	}
	/**
	 * 字体
	 */
	BitmapFont font; 
    private void renderAllObjects(SpriteBatch batch) {
    	//设置画刷的投影矩阵
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        worldController.map.drawBackground(batch);
        worldController.player.render(batch);
        if(worldController.skill!=null)worldController.skill.render(batch);
        worldController.map.drawFrontground(batch);
        batch.end();
    }

    public void resize(int width, int height) {
        camera.viewportWidth = (Constants.VIEWPORT_HEIGHT/ height) * width;
        camera.update();
        worldController.cursor.setPosition(0, 0);
        cameraGUI.viewportHeight = Constants.VIEWPORT_GUI_HEIGHT;
        cameraGUI.viewportWidth = (Constants.VIEWPORT_GUI_HEIGHT/ (float)height) * (float)width;
        cameraGUI.position.set(cameraGUI.viewportWidth / 2,cameraGUI.viewportHeight / 2, 0);
        cameraGUI.update();
    }
    
    private void renderGuiScore(SpriteBatch batch) {
    	//debug
        font.draw(batch,"FPS:"+Gdx.graphics.getFramesPerSecond(), cameraGUI.position.x - Constants.VIEWPORT_WIDTH/ 2, cameraGUI.position.y + Constants.VIEWPORT_HEIGHT/ 2);
        font.draw(batch,"视角宽度:"+Constants.SCREEN_WIDTH+",高度:"+Constants.SCREEN_HEIGHT+"("+worldController.cameraHelper.getPosition().x+","+worldController.cameraHelper.getPosition().y+")", cameraGUI.position.x - Constants.VIEWPORT_WIDTH/ 2, cameraGUI.position.y - 20 + Constants.VIEWPORT_HEIGHT/ 2);
        font.draw(batch,"地图(x:"+worldController.map.getX()+",y:"+worldController.map.getY(), cameraGUI.position.x - Constants.VIEWPORT_WIDTH/ 2, cameraGUI.position.y - 40 + Constants.VIEWPORT_HEIGHT/ 2);
        font.draw(batch,"角色坐标(x:"+worldController.player.getX()+",y:"+worldController.player.getY(), cameraGUI.position.x - Constants.VIEWPORT_WIDTH/ 2, cameraGUI.position.y - 60 + Constants.VIEWPORT_HEIGHT/ 2);
        Vector2 m = worldController.map.getMapCellPostion(worldController.player.getPosition());
        font.draw(batch,"地图坐标(x:"+m.x +",y:"+m.y+")",cameraGUI.position.x - Constants.VIEWPORT_WIDTH/ 2, cameraGUI.position.y - 80 + Constants.VIEWPORT_HEIGHT/ 2);
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
        DebugMarker.dispose();
    }
}

