package com.mygdx.game;


import Model.Function2d;
import Model.Vector2d;
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
        this.holeTolerance = 1.5;
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
}
