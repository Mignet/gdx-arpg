package com.v5ent.test;


import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;

public class PolygonTest extends ApplicationAdapter{

    Polygon polygon1, polygon2;
    ShapeRenderer rend;
    float[] vertices1, vertices2;
    Vector2 point = new Vector2(100, 50);
    
    InputAdapter adapter = new InputAdapter(){
        @Override
        public boolean touchDown(int screenX, int screenY, int pointer, int button) {            
            for(int i=0; i<polygon2.getVertices().length; i+=2){
                polygon2.getVertices()[i  ] += screenX - point.x;
                polygon2.getVertices()[i+1] += Gdx.graphics.getHeight()-screenY - point.y;
            }
            polygon2.dirty();
            
            point.set(screenX, Gdx.graphics.getHeight()-screenY);
            //polygon2.setVertices(new float[]{ 100+point.x, 50+point.y, 200+point.x, 70+point.y, 300+point.x, 150+point.y, 150+point.x, 100+point.y});
                        
            return true;
        }
        
    };
    
    @Override
    public void create() {
        super.create();
        
        polygon1 = new Polygon();
        vertices1 = new float[]{ 100, 100, 200, 100, 300, 300, 100, 200 };
        polygon1.setVertices(vertices1);
        
        vertices2 = new float[]{ 100, 50, 200, 70, 300, 150, 150, 100};
        polygon2 = new Polygon(vertices2);
        
        rend = new ShapeRenderer();
        Gdx.input.setInputProcessor(adapter);
    }

    
    public static boolean isOverlap(Polygon polygon1, Polygon polygon2){
        for(int i=0; i<polygon2.getVertices().length; i+=2){
            if( polygon1.contains(polygon2.getVertices()[i], polygon2.getVertices()[i+1]) ){
                return true;
            }
        }
        for(int i=0; i<polygon1.getVertices().length; i+=2){
            if( polygon2.contains(polygon1.getVertices()[i], polygon1.getVertices()[i+1]) ){
                return true;
            }
        }
        return false;
    }
    
    
    
    @Override
    public void render() {
        // TODO Auto-generated method stub
        super.render();
     // Sets the clear screen color to: Cornflower Blue
        Gdx.gl.glClearColor(0x64 / 255.0f, 0x64 / 255.0f, 0x64 / 255.0f,
                0xff / 255.0f);
        // Clears the screen
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
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
        if( isOverlap(polygon1, polygon2) ){
            rend.setColor(Color.RED);
        }else{
            rend.setColor(Color.BLUE);
        }
        rend.begin(ShapeType.Filled);
        rend.circle(point.x, point.y, 5);
        rend.end();
        
    }

    @Override
    public void dispose() {
        // TODO Auto-generated method stub
        super.dispose();
    }

}
