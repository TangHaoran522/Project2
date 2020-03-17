package com.mygdx.game.desktop;


import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.Golf;
import com.mygdx.game.Main;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		config.width=Main.WIDTH;
        config.height=Main.HEIGHT;
        config.resizable=false;
		config.foregroundFPS = 165;
		config.backgroundFPS = 165;
		
		new LwjglApplication(new Main(), config);
	}
}
