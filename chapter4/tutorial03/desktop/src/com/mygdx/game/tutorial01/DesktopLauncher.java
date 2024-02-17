package com.mygdx.game.tutorial01;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.mygdx.game.tutorial01.animo.FrameAnimationExample;
import com.mygdx.game.tutorial01.particle.ParticleSystemExample;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
		config.setTitle("tutorial01");
		new Lwjgl3Application(new FrameAnimationExample(), config);
	}
}
