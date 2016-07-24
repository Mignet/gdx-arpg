/**
 * Project Name:arpg-core
 * File Name:Asserts.java
 * Package Name:com.v5ent.game.asserts
 * Date:2014-7-26下午5:18:00
 * Copyright (c) 2014, DXTX All Rights Reserved.
 *
*/

package com.v5ent.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.v5ent.game.util.Constants;

/**
 * <b>资源库</b><br>
 * 将所有资源放在这里
 */
public class Assets implements Disposable, AssetErrorListener {
    public static final String TAG = Assets.class.getName();
    /***资源管家单例**/
    public static final Assets instance = new Assets();
    /** 地图前景、背景*/
    public static TextureRegion bg,bg_;
    public static TextureRegion[] cursor;
    
    public static TextureRegion[] water;
    public static TextureRegion[] earth;
    public static TextureRegion[] fire;
    public static TextureRegion[] thunder;
    
    /***玩家行走8*8 **/
    public static TextureRegion[][] player_move;
	/***玩家站立8*8 **/
	public static TextureRegion[][] player_stand;
	
	public static BitmapFont font;
	
    private AssetManager assetManager;

    // singleton: prevent instantiation from other classes
    private Assets() {
    }
    

    public void init(AssetManager assetManager) {
        this.assetManager = assetManager;
        // set asset manager error handler
        assetManager.setErrorListener(this);
      //首次加载之后缓存起来
        load();
        
    }

    @Override
    public void dispose() {
        assetManager.dispose();
    }

	@Override
	public void error(AssetDescriptor asset, Throwable throwable) {
		 Gdx.app.error(TAG, "Couldn't load asset '" + asset.fileName + "'", (Exception) throwable);
	}
	
	public static void load () {
		bg = load("stage/观音殿.jpg", 1278, 948);
		bg_ = load("stage/观音殿_2.png", 1278, 948);
		player_stand = split("ltz/ltz8_w1-88-98.png", 88, 98);
		player_move = split("ltz/ltz8-69-96.png", 69, 96);
		water = loads("skill/雨击/雨击 (",").png",35, 495, 331);
		earth = loads("skill/飞沙走石/飞沙走石 (",").png",26, 428, 369);
		fire = loads("skill/地狱烈火/fir (",").png",28, 464, 273);
		thunder = loads("skill/奔雷/benlei (",").png",35, 450, 450);
		cursor = splitU("windowsets/cur_move_strip11.png", 30, 30);
		
	/*	FreeTypeFontGenerator generator = 
				new FreeTypeFontGenerator( Gdx.files.internal( "fonts/Inconsolata.otf" ) );
        FreeTypeFontGenerator.FreeTypeFontParameter fontParameter = 
                new FreeTypeFontGenerator.FreeTypeFontParameter();
        //根据参数设置生成的字体数据
        FreeTypeFontGenerator.FreeTypeBitmapFontData fontData =
        		generator.generateData(fontParameter);
        
        //及时释放资源，避免内存泄漏，中文字体文件一般都比较大
        generator.dispose();
        font = new BitmapFont(fontData, fontData.getTextureRegions(), false);*/
        font =new BitmapFont(Gdx.files.internal("fonts/test.fnt"));
	}

	private static TextureRegion[] loads(String pre, String tail, int mnt,
			int width, int height) {
		TextureRegion[] res = new TextureRegion[mnt];
		for(int i=0;i<mnt;i++){
			res[i] = load(pre+(i+1)+tail,width,height);
		}
		return res;
	}


	/**
	 * split:x轴横向分割素材
	 * @param name - 名称
	 * @param width - tile宽度
	 * @param height - tile高度
	 * @return
	 * @since JDK 1.6
	 */
	private static TextureRegion[] splitU(String name, int width, int height) {
		Texture texture = new Texture(Gdx.files.internal(name));
		int xSlices = texture.getWidth() / width;
		TextureRegion[] res = new TextureRegion[xSlices];
		for (int x = 0; x < xSlices; x++) {
				res[x] = new TextureRegion(texture, x * width, 0, width, height);
		}
		return res;
	}

	/**
	 * split:xy横纵分割素材
	 * @param name - 名称
	 * @param width - tile宽度
	 * @param height - tile高度
	 * @return
	 * @since JDK 1.6
	 */
	private static TextureRegion[][] split (String name, int width, int height) {
		return split(name, width, height, false);
	}

	private static TextureRegion[][] split (String name, int width, int height, boolean flipX) {
		Texture texture = new Texture(Gdx.files.internal(name));
		int xSlices = texture.getWidth() / width;
		int ySlices = texture.getHeight() / height;
		TextureRegion[][] res = new TextureRegion[xSlices][ySlices];
		for (int x = 0; x < xSlices; x++) {
			for (int y = 0; y < ySlices; y++) {
				res[x][y] = new TextureRegion(texture, x * width, y * height, width, height);
//				res[x][y].flip(flipX, true);
			}
		}
		return res;
	}
	/**
	 * 从文件加载Texture
	 *
	 * @param name
	 * @param width
	 * @param height
	 * @return
	 */
	public static TextureRegion load (String name, int width, int height) {
		Texture texture = new Texture(Gdx.files.internal(name));
		TextureRegion region = new TextureRegion(texture, 0, 0, width, height);
//		region.flip(false, true);
		return region;
	}
}

