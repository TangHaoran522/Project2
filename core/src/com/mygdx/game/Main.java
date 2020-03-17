package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Main extends Game {
    public SpriteBatch batch;
    public boolean play = true;
    public boolean close = false;

    public static int WIDTH = 1080;
    public static int HEIGHT = 720;

    @Override
    public void create() {
        batch = new SpriteBatch();
        this.setScreen(new Menu(this));
    }

    @Override
    public void render(){
        super.render();
    }
}
