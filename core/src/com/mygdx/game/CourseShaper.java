package com.mygdx.game;

import Model.Function2d;
import Model.Vector2d;

public class CourseShaper implements Function2d {
    public CourseShaper(String function2d){

        //TODO: okay seriously? a function that returns a function?
    }

    @Override
    public double evaluate(Vector2d p) {
        return Math.sin(p.getX()) + p.getY2();
    }

    @Override
    public Vector2d gradient(Vector2d p) {
        //JD PHYSICS METHOD FOR DzDx and DzDy???

        return null;
    }
}
