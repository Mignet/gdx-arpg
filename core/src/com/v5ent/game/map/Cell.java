package com.v5ent.game.map;

import com.badlogic.gdx.math.Vector;

public class Cell {

	private int GroundPic = 6; // 地面页面编号（普通0-59 动态60-63）
	private int Ground = 12; // 地面编号
	private int ObjPic = 6; // 物体页面编号（普通0-59 动态60-63）
	private int Obj = 12; // 物体编号
	private int Obj2Pic = 6; // 物体2页面编号（普通0-59 动态60-63）
	private int Obj2 = 12; // 物体2编号
	private int Block = 1; // 是否阻挡
	private int Level = 4; // 物体所在层次(0-15)
	private int CP = 7; // 陷阱
	private int Level2 = 4; // 物体2所在层次(0-15)
	private int MouseType = 3; // 鼠标类型
	private int CPType = 1; // 陷阱类型
	private int res = 14; // 保留

}
