package com.gdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import Screens.*;
import Tools.Controller;

public class MarioGame extends Game {
	public static final int V_WIDTH = 480;
	public static final int V_HEIGHT =288;
	public static SpriteBatch batch;//static ist experimentell
	public static final float PPM = 100;

	public static final short GROUND2_BIT = 128;
	public static final short GROUND_BIT = 1;
	public static final short MARIO_BIT = 2;
	public static final short BRICK_BIT = 4;
	public static final short COIN_BIT = 8;
	public static final short DESTROYED_BIT = 16;
	public static final short OBJECT_BIT = 32;
	public static final short ENEMY_BIT = 64;
	public static final short ZIEL_BIT = 256;
	public static final short ENEMY_HEAD_BIT = 512;

	public Controller controller;


	/**
	 * !!ACHTUNG!!
	 * AssetManager sollte unbedingt herumgegeben werden und nicht statisch verwendet werden!
	 */
	public static AssetManager manager;

	@Override
	public void create () {
		batch = new SpriteBatch();
		manager = new AssetManager();
		manager.load("audio/music/mario_music.ogg", Music.class);
		manager.load("audio/sounds/coin.wav", Sound.class);
		manager.load("audio/sounds/bump.wav", Sound.class);
		manager.load("audio/sounds/breakblock.wav", Sound.class);
		manager.finishLoading();//Brent Aureli Part 15 noch man anschauen, um AssetManager umzustellen

		controller = new Controller(this);
		setScreen(new MenuScreen(this));
	}

	@Override
	public void render () {
		super.render();

	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
