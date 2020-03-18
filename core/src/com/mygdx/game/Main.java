package com.mygdx.game;

import Model.EulerSolver;
import Model.PhysicsEngine;
import Model.Vector2d;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Main extends Game {
	
	private PuttingCourse course;
	private PhysicsEngine eulerSolver;
	
    public SpriteBatch batch;
    public boolean play = true;
    public boolean close = false;

    public static int WIDTH = 1080;
    public static int HEIGHT = 720;


    @Override
    public void create() {
        batch = new SpriteBatch();
        eulerSolver = new EulerSolver();
        course = new PuttingCourse(new CourseShaper("A function"), new Vector2d(10,0), new Vector2d(0,0));
        this.setScreen(new Menu(this));
    }

    @Override
    public void render(){
        super.render();
    }


    public void SetCourse(PuttingCourse c){
        course=c;
    }

    public void setEngine(PhysicsEngine engine){
        this.eulerSolver = engine;
    }
    public PhysicsEngine getEngine(){
        return this.eulerSolver;
    }
    public void setCourse(PuttingCourse course){
        this.course = course;
    }
    public PuttingCourse getCourse(){
        return this.course;
    }
}
