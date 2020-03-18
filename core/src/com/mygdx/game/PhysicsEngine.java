package com.mygdx.game;

public class PhysicsEngine{
	static double stepSize = 0.1;

	//just storing the previous position to check when to stop loop
	static double previousPosX;
	static double previousPosY;


	static double currentPosX = 0.0;
	static double currentPosY= 0.0;

	//this is x0 and y0
	static double currentVelX = 0.0;
	static double currentVelY = 2.0;

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
	static double mu = 0.0;
	static double vmax= 3.0;
	static double tol = 0.02;

	static double goalX = 0.0;
	static double goalY = 10.0;

	//(Math.round(currentPosX*1000000)!=Math.round(previousPosX*1000000))||(Math.round(currentPosY*1000000))!=(Math.round(previousPosY*1000000))
	public static double get_height(double x, double y) {
			return y*y;//(-0.01*x+.003*x*x+.04*y);
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
			System.out.println("Previous Vel X: "+ currentVelX +" Previous Vel Y"+currentVelY);
			setNextVelocities();
			previousPosX = currentPosX;
			previousPosY = currentPosY;
			currentPosX = nextPosX;
			currentPosY = nextPosY;
			System.out.println("Previous X: " + previousPosX + " Previous Y: " + previousPosY);
			System.out.println("Current X: "+ currentPosX +" Current Y: "+ currentPosY);
			System.out.println("dxdz: "+DzDx+" dydz: "+DzDy);
			System.out.println("Acceleration X: " + accelerationX + " Acceleration Y: "+ accelerationY);
			System.out.println("Current Vel X: "+currentVelX+" Current Vel Y"+currentVelY);
			System.out.println(" ");

		} while (true);
		//System.out.println(accelerationX + " " + accelerationY + " "+ currentPosX +" "+ currentPosY);

	}
	/**
	 *Euler's method finding the slope
	 *There are different scenarios depending on the direction of current and next position and we need to know if the slope,
	 *positive or negative, these if statements fix the bug where the ball stopped on a slope.
	 */
	private static void setSlopes() {
		double change =0.01;
		if((nextPosX>currentPosX)&&(get_height(nextPosX, currentPosY)>get_height(currentPosX, currentPosY))){
			DzDx = (get_height(currentPosX+change, currentPosY) - get_height(currentPosX, currentPosY))/stepSize;
		}else if((currentPosX>nextPosX)&&(get_height(currentPosX, currentPosY)>get_height(nextPosX, currentPosY))){
			DzDx = (get_height(currentPosX, currentPosY) - get_height(currentPosX-change, currentPosY))/stepSize;
		}else if((nextPosX>currentPosX)&&(get_height(nextPosX, currentPosY)<get_height(currentPosX, currentPosY))){
			DzDx = -((get_height(currentPosX, currentPosY) - get_height(currentPosX+change, currentPosY))/stepSize);
		}else{
			DzDx = -((get_height(currentPosX-change, currentPosY) - get_height(currentPosX, currentPosY))/stepSize);
		}
		if((nextPosY>currentPosY)&&(get_height(currentPosX, nextPosY)>get_height(currentPosX, currentPosY))){
			DzDy = (get_height(currentPosX, currentPosY+change) - get_height(currentPosX, currentPosY))/stepSize;
		}else if((currentPosY>nextPosY)&&(get_height(currentPosX, currentPosY)>get_height(nextPosX, currentPosY))){
			DzDy = (get_height(currentPosX, currentPosY) - get_height(currentPosX, currentPosY-change))/stepSize;
		}else if((nextPosY>currentPosY)&&(get_height(nextPosX, nextPosY)<get_height(currentPosX, currentPosY))){
			DzDy = -((get_height(currentPosX, currentPosY) - get_height(currentPosX, currentPosY+change))/stepSize);
		}else{
			DzDy = -((get_height(currentPosX, currentPosY-change) - get_height(currentPosX, currentPosY))/stepSize);
		}
	}
	/**
	 * Setting the acceleration using the formula provided
	 */
	private static void setAcceleration() {
		//if((Math.signum(g*DzDx)!=Math.signum(mu*g*(currentVelX/(Math.sqrt((currentVelX*currentVelX) + (currentVelY*currentVelY))))))&&
				//(Math.abs(g*DzDx)<(Math.abs(mu*g*(currentVelX/(Math.sqrt((currentVelX*currentVelX) + (currentVelY*currentVelY)))))))){
//if DzDx is zero, flat surface then friction won't be acting on this..put in another condition that allows this, if friction is greater than gravity on a flat serface then it should stop
// likewise if the ball changes directional velocity on an axis and if there is a slope and the force of gravity needs to get bigger than the force of friction
			//if it should have stopped how do we know this, does force of gravity get smaller than friction again
			//does the force of gravity never get bigger than friction again.
			//should we change our step sizes to when coming close to changing direction, as the acceleration gets smaller step size gets bigger.
			//if acceleration goes to 0 then stop the ball on the hill as the velocity goes to 0 and changes
			//accelerationX = (-1)*(g*DzDx);//if it comes into this method a second time after changing direction then the ball has stopped rolling? or if it doesn't after a certain distance?

		//}else{
			accelerationX = -(g*DzDx) - (mu*g*(currentVelX/(Math.sqrt((currentVelX*currentVelX) + (currentVelY*currentVelY)))));

		System.out.println("Accekeration calc: (g*DzDy): "+(g*DzDy)+" mu: "+(mu*g*(currentVelY/(Math.sqrt((currentVelX*currentVelX) + (currentVelY*currentVelY))))));

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