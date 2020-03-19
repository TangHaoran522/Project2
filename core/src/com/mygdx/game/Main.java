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


    public static int WIDTH = 1080;
    public static int HEIGHT = 720;
    
    public int count = 0;



    @Override
    public void create() {
        batch = new SpriteBatch();
        eulerSolver = new EulerSolver("1 sin x + y ^ 2");
        course = new PuttingCourse(new CourseShaper("1 sin x + y ^ 2"), new Vector2d(50,0), new Vector2d(0,0));
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
