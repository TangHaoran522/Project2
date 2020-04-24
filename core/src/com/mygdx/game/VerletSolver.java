package com.mygdx.game;

import Model.Solver;
import Model.Vector2d;

import static java.lang.Math.sqrt;

public class VerletSolver extends Solver {
    Vector2d position;
    Vector2d velocity;
    Vector2d acceleration;
    int x =0;
    //update so that the only fields that reference velocity and directions are in solvers not menu classes
    public VerletSolver(String ab) {
        super(ab);
    }
    @Override
    public void nextStep(){
        if (x==0) {
            acceleration = getNextAcceleration(position, velocity);
            x++;
        }
        position = getNextPosition(position, velocity, acceleration, solverStepSize );
        Vector2d tempAcc = acceleration.clone();
        acceleration = getNextAcceleration(position, velocity);
        velocity = getNextVelocity(velocity,acceleration,tempAcc, stepSize);
    }

    private Vector2d getNextVelocity(Vector2d velocity, Vector2d acceleration,Vector2d oldAcceleration, double stepSize) {
        return new Vector2d(velocity.getX()+0.5*(oldAcceleration.getX()+acceleration.getX())*stepSize,
                velocity.getY()+0.5*(oldAcceleration.getY()+acceleration.getY())*stepSize);
    }

    private Vector2d getNextPosition (Vector2d currentPosition, Vector2d currentVelocity, Vector2d currentAcceleration, double stepSize){
       return new Vector2d(currentPosition.getX()+currentVelocity.getX()*stepSize+0.5*currentAcceleration.getX()*stepSize*stepSize,
               currentPosition.getY()+currentVelocity.getY()*stepSize+0.5*currentAcceleration.getY()*stepSize*stepSize);
    }


    @Override
    public void setPosition(Vector2d v) {
        position = v;
    }

    @Override
    public void setVelocity(Vector2d v) {
        velocity = v;
    }

    @Override
    public Vector2d getPosition() {
        return position;
    }
}
