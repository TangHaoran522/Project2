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