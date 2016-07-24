package com.v5ent.game.util;

import com.badlogic.gdx.math.Polygon;

public class PolygonUtil {
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
}
