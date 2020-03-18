package com.mygdx.game;


import Model.Function2d;
import Model.Vector2d;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.utils.Array;
//import jdk.nashorn.internal.objects.annotations.Function;

public class PuttingCourse{
    private Function2d height;
    private Vector2d flag;
    private Vector2d start;
    private double friction;
    private double maximumVelocity;
    private double holeTolerance;

    public PuttingCourse(Function2d height, Vector2d flag, Vector2d start){
        this.height=height;
        this.flag=flag;
        this.start=start;
        this.friction =  0.131;
        this.maximumVelocity=10.0;
        this.holeTolerance = 0.5f;
    }
//TODO: should we move everthing related to the course modeling etc here?
    public Array<ModelInstance> getCourseModel(){
        Model model = new Model();
        ModelBuilder mb = new ModelBuilder();
        mb.begin();
        Array<ModelInstance> instances = new Array<>();
        ModelInstance groundBall = new ModelInstance(model, "groundBalls");

        for (float j = -5f; j <= 5f; j += 0.3f) {
            for (float i = 0; i <= 199; i += 0.3f) {
                groundBall = new ModelInstance(model, "groundBalls");
                groundBall.transform.setToTranslation(i,(float)get_height().evaluate(new Vector2d(i,j))-.25f, j);
                instances.add(groundBall);
            }
        }

        return instances;
    }

    public Function2d get_height(){
        return this.height;
    }

    public Vector2d get_flag_position(){
        return this.flag;
    }

    public Vector2d get_start_position(){
        return this.start;
    }

    public double get_friction_coefficient(){
        return this.friction;
    }

    public double get_maximum_velocity(){
        return this.maximumVelocity;
    }


    public double get_hole_tolerance(){
        return this.holeTolerance;
    }
    
    public void set_Func2d(Function2d add) {
    	this.height = add;
    }
}
