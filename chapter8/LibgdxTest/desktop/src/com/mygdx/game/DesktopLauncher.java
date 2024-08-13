package com.mygdx.game;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.mygdx.game.steering.SteeringBehaviorExample;

public class DesktopLauncher {
    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setTitle("Steering Behavior Example");
        config.setWindowedMode(1080, 920);
        new Lwjgl3Application(new SteeringBehaviorExample(), config);
    }
}
