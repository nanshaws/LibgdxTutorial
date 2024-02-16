package com.mygdx.game;

import com.mygdx.game.screen.StartGameScreen;
import org.cyl.framwork.BaseGame;


public class MyGdxGame extends BaseGame {

	@Override
	public void create () {
		super.create();
		StartGameScreen screen=new StartGameScreen(800,600,this);
		setScreen(screen);
	}

}
