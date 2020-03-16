package com.mygdx.game;

public class PhysicsEngine{
	static double stepSize = 0.00001;
	
	//just storing the previous position to check when to stop loop
	static double previousPosX;
	static double previousPosY;

	
	static double currentPosX = 0.0;
	static double currentPosY= 0.0;
	
	//this is x0 and y0
	static double currentVelX = 10.0;
	static double currentVelY = 0.0;
	
	//this is x1 and y1
	static double nextPosX;
	static double nextPosY;
	
	//this is the the h,x(x,y) and h,y(x,y), the first derivative of the height function using Euler's method
	static double DzDx;
	static double DzDy;
	
	static double accelerationX;
	static double accelerationY;
	
	//the variables they require for the import, some not used yet:
	static double g = 9.81;
	static double m = 45.93;
	static double mu = 0.131;
	static double vmax= 3.0;
	static double tol = 0.02;
	
	static double goalX = 0.0;
	static double goalY = 10.0;
	
//(Math.round(currentPosX*1000000)!=Math.round(previousPosX*1000000))||(Math.round(currentPosY*1000000))!=(Math.round(previousPosY*1000000))
	public static double get_height(double x, double y) {
		return ((-0.01*x) + (0.003*(x*x)) + (0.04*y));
	}
	
	public static void main(String args[]) {
		//each method required per step:
		//it's looping until the current position and the previous position stays the same
		//acceleration never reaches 0, the net acceleration between the previous step and the current step reaches 0 thus the positions does not change, this is the logic i am most unsure about
		//and also why based odd of the formula's and implementation they explained in the appendix mass does not effect the resulting movement
		do {
			setNextPositions();
			setSlopes();
			setAcceleration();
			setNextVelocities();
			previousPosX = currentPosX;
			previousPosY = currentPosY;
			currentPosX = nextPosX;
			currentPosY = nextPosY;
			System.out.println(accelerationX + " "+previousPosX + " " + previousPosY + " "+ currentPosX +" "+ currentPosY);

		} while ((Math.round(currentPosX*1000000)!=Math.round(previousPosX*1000000))||(Math.round(currentPosY*1000000))!=(Math.round(previousPosY*1000000)));
		//System.out.println(accelerationX + " " + accelerationY + " "+ currentPosX +" "+ currentPosY);

	}
	/**
	 *Euler's method finding the slope
	 */
	private static void setSlopes() {
		DzDx = (get_height(nextPosX, currentPosY) - get_height(currentPosX, currentPosY))/stepSize;
		DzDy = (get_height(currentPosX, nextPosY) - get_height(currentPosX, currentPosY))/stepSize;

	}
	/**
	 * Setting the acceleration using the formula provided
	 */
	private static void setAcceleration() {
		accelerationX = -(g*DzDx) - (mu*g*(currentVelX/(Math.sqrt((currentVelX*currentVelX) + (currentVelY*currentVelY)))));
		accelerationY = -(g*DzDy) - (mu*g*(currentVelY/(Math.sqrt((currentVelX*currentVelX) + (currentVelY*currentVelY)))));
	}
	/**
	 * Setting the next position, after a step, by multiplying the current velocity by the step size
	 */
	private static void setNextPositions() {
		nextPosX = currentPosX + currentVelX*stepSize;
		nextPosY = currentPosY + currentVelY*stepSize;
	}
	
	/**
	 * Setting the new velocity after a step
	 */
	private static void setNextVelocities() {
		currentVelX = currentVelX + accelerationX*stepSize;
		currentVelY = currentVelY + accelerationY*stepSize;
	}
	
	
	
	
}