package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;

public class OptionMenu implements Screen {


        private final int BUTTON_WIDTH = 300;
        private final int BUTTON_HEIGHT = 100;

        private final int PLAY_HEIGHT = 325;
        private final int OPTION_HEIGHT = 200;
        private final int EXIT_HEIGHT = 75;
        
        public float velocity = 15;
        public float angle = 0;

        Texture exitButtonActive;
        Texture exitButtonInactive;

        Texture playButtonActive;
        Texture playButtonInactive;

        Texture optionButtonActive;
        Texture optionButtonInactive;

        Texture group20;

        private Main main;

        public OptionMenu(Main main){
            this.main = main;
                exitButtonActive=new Texture("ExitButtonActive.jpg");
                exitButtonInactive=new Texture("ExitButtonInactive.jpg");
            //TODO: make possible to edit settings and such...
        }


        @Override
        public void show() {

        }

        @Override
        public void render(float delta) {
            Gdx.gl.glClearColor(0,0.4f,0,1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


            main.batch.begin();
                if(Gdx.input.getX()<Main.WIDTH-BUTTON_WIDTH-10+BUTTON_WIDTH && Gdx.input.getX() > Main.WIDTH-BUTTON_WIDTH-10
                        && Gdx.input.getY()>Main.HEIGHT-(EXIT_HEIGHT+BUTTON_HEIGHT) && Gdx.input.getY()<Main.HEIGHT-EXIT_HEIGHT) {
                        main.batch.draw(exitButtonInactive, Main.WIDTH - BUTTON_WIDTH -10 , EXIT_HEIGHT, BUTTON_WIDTH, BUTTON_HEIGHT);
                        if(Gdx.input.isTouched()) {
                               main.setScreen(new Menu(main));
                        }
                }else
                        main.batch.draw(exitButtonActive,Main.WIDTH-BUTTON_WIDTH+10,EXIT_HEIGHT, BUTTON_WIDTH, BUTTON_HEIGHT);
            main.batch.end();
        }

        @Override
        public void resize(int width, int height) {

        }

        @Override
        public void pause() {

        }

        @Override
        public void resume() {

        }

        @Override
        public void hide() {

        }

        @Override
        public void dispose() {

        }
    }