/**
 * Project Name:arpg-core
 * File Name:Painter.java
 * Package Name:com.v5ent.game.util
 * Date:2015-7-26下午1:23:36
 * Copyright (c) 2015, DXTX All Rights Reserved.
 *
*/

package com.v5ent.game.util;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.v5ent.game.map.Map;

public class DebugMarker {
	private static ShapeRenderer debugRenderer = new ShapeRenderer();
	
	private static final String TAG = DebugMarker.class.getName();
	
	public static void dispose(){
		debugRenderer.dispose();
	}
    public static void drawDebugLine(Vector2 start, Vector2 end, int lineWidth, Color color, Matrix4 projectionMatrix)
    {
        Gdx.gl.glLineWidth(lineWidth);
        debugRenderer.setProjectionMatrix(projectionMatrix);
        debugRenderer.begin(ShapeRenderer.ShapeType.Line);
        debugRenderer.setColor(color);
        debugRenderer.line(start, end);
        debugRenderer.end();
        Gdx.gl.glLineWidth(1);
    }
    
    public static void drawPolygon(Polygon polygon, Matrix4 projectionMatrix)
    {
    	
    	Gdx.gl.glLineWidth(2);
    	debugRenderer.setProjectionMatrix(projectionMatrix);
    	debugRenderer.begin(ShapeRenderer.ShapeType.Line);
    	
    	debugRenderer.setColor(Color.RED);
    	float[] vertices = polygon.getVertices();
        for(int i=0; i<vertices.length; i+=2){
        	debugRenderer.line(vertices[i], vertices[i+1], vertices[(i+2)%vertices.length], vertices[(i+3)%vertices.length]);
        }
    	debugRenderer.end();
    	Gdx.gl.glLineWidth(1);
    }

    public static void drawDebugLine(Vector2 start, Vector2 end, Matrix4 projectionMatrix)
    {
        Gdx.gl.glLineWidth(2);
        debugRenderer.setProjectionMatrix(projectionMatrix);
        debugRenderer.begin(ShapeRenderer.ShapeType.Line);
        debugRenderer.setColor(Color.WHITE);
        debugRenderer.line(start, end);
        debugRenderer.end();
        Gdx.gl.glLineWidth(1);
    }
    
    public static void drawMapGrid(Map m,Matrix4 projectionMatrix)
    {
    	float offsetX = m.getX();
    	float offsetY = m.getY();
    	Gdx.gl.glLineWidth(2);
    	debugRenderer.setProjectionMatrix(projectionMatrix);
    	debugRenderer.begin(ShapeRenderer.ShapeType.Line);
    	debugRenderer.setColor(Color.CYAN);
    	int w =m. rows;
    	int h = m.cols;
    	for(int i=0;i<=h;i++){
    		debugRenderer.line(new Vector2(offsetX+0,offsetY+i*Constants.CELL_SIZE), new Vector2(offsetX+w*Constants.CELL_SIZE,offsetY+i*Constants.CELL_SIZE));
    	}
    	for(int j=0;j<=w;j++){
    		debugRenderer.line(new Vector2(offsetX+j*Constants.CELL_SIZE,offsetY+0), new Vector2(offsetX+j*Constants.CELL_SIZE,offsetY+h*Constants.CELL_SIZE));
    	}
    	debugRenderer.end();
    	//rect
    	debugRenderer.begin(ShapeRenderer.ShapeType.Filled);
    	for(Vector2 p:m.blockPoints){
    		debugRenderer.rect(offsetX+p.x*Constants.CELL_SIZE, offsetY+p.y*Constants.CELL_SIZE, Constants.CELL_SIZE, Constants.CELL_SIZE);
    	}
    	debugRenderer.end();
    }
    public static void drawCell(Map m,Vector2 p,Matrix4 projectionMatrix)
    {
    	Gdx.gl.glLineWidth(2);
    	debugRenderer.setProjectionMatrix(projectionMatrix);
    	debugRenderer.setColor(Color.CYAN);
    	//rect
    	float offsetX = m.getX();
    	float offsetY = m.getY();
    	debugRenderer.begin(ShapeRenderer.ShapeType.Filled);
    	debugRenderer.rect(offsetX+p.x*Constants.CELL_SIZE, 
    			offsetY+p.y*Constants.CELL_SIZE, 
    			Constants.CELL_SIZE, 
    			Constants.CELL_SIZE);
    	debugRenderer.end();
    	
    	Gdx.gl.glLineWidth(1);
    }

	public static void drawTrace(List<Vector2> points, Matrix4 projectionMatrix) {
		Gdx.gl.glLineWidth(2);
        debugRenderer.setProjectionMatrix(projectionMatrix);
        debugRenderer.begin(ShapeRenderer.ShapeType.Line);
        debugRenderer.setColor(Color.WHITE);
        for(int i=0;i<points.size()-1;i++){
        	debugRenderer.line(points.get(i), points.get(i+1));
        }
        debugRenderer.end();
        Gdx.gl.glLineWidth(1);
	}
	
	public static ShapeRenderer getDebugRenderer() {
		return debugRenderer;
	}
}

