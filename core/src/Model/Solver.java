package Model;

import com.mygdx.game.CourseShaper;
import com.mygdx.game.FunctionMaker;

import static java.lang.Math.sqrt;

public abstract class Solver implements PhysicsEngine{
    /**
     * the step size used for finding the partial derivative
     */
    public  double stepSize = 0.0001;
    /**
     * this is the time step that the system will be updating off of
     * 1/165s is every 0.00606060606060606060606060606061 seconds
     * TODO: make an array of all positions at these time intervals so we can set a the screen to update ball position based off of this array instead of calculating on the fly
     */
    public static double fps = 165.0;
    /**
     * Solver Step size
     * This is the step size that is changed for the set_step_size
     */
    public double solverStepSize = 1.0/165.0;


    public double currentPosX;
    public double currentPosY;
    public double currentPosZ;

    public double currentVelX;
    public double currentVelY;

    public double DzDx;
    public double DzDy;


    public double Fx;
    public double Fy;

    public double g = 9.81;
    public double m = 45.93;
    public double mu = 0.3;
    public double vmax= 15.0;
    public double tol = 0.02;

    public double goalX = 0.0;
    public double goalY = 10.0;
    protected static Function2d shape;

    public Solver(String ab) {
        shape = new FunctionMaker(ab);
    }

    /**
     * This is what the different solvers need to implement
     * Gets updates current position to next step in X and Y
     * Before completing this method it should always update the Current Velocity for the class with this.currentVelX = currentVelX + solverStepSize*Fx;
     * Likewise for currentVelY
     */
    public void setNextPositions(double solverStepSize) { }

    public double get_height(double x, double y){
        return shape.evaluate(new Vector2d(x,y));
    }
    //TODO: remove slopes and use the shape.gradient() method
    public double slopeDzDx (double currentPosX, double currentPosY, double h ){
       return  ((get_height(currentPosX+h, currentPosY) - get_height(currentPosX, currentPosY))/h);
    }
    public double slopeDzDy (double currentPosX, double currentPosY, double h ){
        return ((get_height(currentPosX, currentPosY+h) - get_height(currentPosX, currentPosY))/h);
    }
    @Override
    public void nextStep() {

//        DzDx = slopeDzDx(currentPosX, currentPosY, stepSize);
//        DzDy = slopeDzDy(currentPosX, currentPosY, stepSize);
//        setForce();
        setNextPositions(solverStepSize);
        currentPosZ = get_height(currentPosX,currentPosY);

    }

    public void setForce() {
        Fx = -(g*DzDx) - (mu*g*(currentVelX/(sqrt((currentVelX*currentVelX) + (currentVelY*currentVelY)))));
        Fy = -(g*DzDy) - (mu*g*(currentVelY/(sqrt((currentVelX*currentVelX) + (currentVelY*currentVelY)))));
    }
    protected Vector2d  getNextAcceleration (Vector2d position, Vector2d velocity){
        Vector2d slopes = this.shape.gradient(position);
        double velY = velocity.getY(), velX = velocity.getX();
        double sqrtSpeeds = sqrt((velX * velX) + (velY * velY));
        return new Vector2d( (-1)*(g*slopes.getX()) - (mu*g*(velX/ sqrtSpeeds)),
                (-1)*(g*slopes.getY()) - (mu*g*(velY/ sqrtSpeeds)));
    }

    public double velocityX(double h){
        return currentVelX + h*Fx;
    }

    public double velocityY(double h){
        return currentVelY + h*Fy;
    }

    @Override
    public void setPosX(double d) {
        currentPosX =d;
    }

    @Override
    public void setPosY(double d) {
        currentPosY = d;
    }

    @Override
    public void setPosZ(double d) {
        currentPosZ=d;
    }

    @Override
    public void setVelX(double d) {
        currentVelX = d;
    }

    @Override
    public void setVelY(double d) {
        currentVelY=d;
    }

    @Override
    public void setMu(double mu) {
        this.mu=mu;
    }

    @Override
    public void setVMax(double vMax) {
        vmax=vMax;
    }

    @Override
    public double getPosX() {
        return currentPosX;
    }

    @Override
    public double getPosY() {
        return currentPosY;
    }

    @Override
    public double getPosZ() {
        return currentPosZ;
    }

    @Override
    public double getVelX() {
        return currentVelX;
    }

    @Override
    public double getVelY() {
        return currentVelY;
    }

}