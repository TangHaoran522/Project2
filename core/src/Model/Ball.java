package Model;

public class Ball {
	
public static int fps = 60;
	
public static float stepSize = (float)(1.0/fps);
	
	//just storing the previous position to check when to stop loop
	public static float previousPosX;
	public static float previousPosY;

	
	public static float currentPosX;
	public static float currentPosY;
	public static float currentPosZ;
	
	//this is x0 and y0
	public static float currentVelX;
	public static float currentVelY;
	
	//this is x1 and y1
	public static float nextPosX;
	public static float nextPosY;
	
	//this is the the h,x(x,y) and h,y(x,y), the first derivative of the height function using Euler's method
	public static float DzDx;
	public static float DzDy;
	
	public static float accelerationX;
	public static float accelerationY;
	
	//the variables they require for the import, some not used yet:
	public static float g = 9.81f;
	public static float m = 45.93f;
	public static float mu = 0.0f;
	public static float vmax= 3.0f;
	public static float tol = 0.02f;
	
	public static float goalX = 0.0f;
	public static float goalY = 10.0f;
	
//(Math.round(currentPosX*1000000)!=Math.round(previousPosX*1000000))||(Math.round(currentPosY*1000000))!=(Math.round(previousPosY*1000000))
	public static float get_height(float x, float y) {
		return (float)(Math.sin(x) + y*y);
	}
	
	public static void NextStep() {
		//each method required per step:
		//it's looping until the current position and the previous position stays the same
		//acceleration never reaches 0, the net acceleration between the previous step and the current step reaches 0 thus the positions does not change, this is the logic i am most unsure about
		//and also why based odd of the formula's and implementation they explained in the appendix mass does not effect the resulting movement
//		do {
			
		
		setNextPositions();
		setSlopes();
		setAcceleration();
		setNextVelocities();	
			
			previousPosX = currentPosX;
			previousPosY = currentPosY;
			currentPosX= nextPosX;
			currentPosY = nextPosY;
//			System.out.println(accelerationX + " "+previousPosX + " " + previousPosY + " "+ currentPosX +" "+ currentPosY);

//		} while ((Math.round(currentPosX*1000000)!=Math.round(previousPosX*1000000))||(Math.round(currentPosY*1000000))!=(Math.round(previousPosY*1000000)));
		//System.out.println(accelerationX + " " + accelerationY + " "+ currentPosX +" "+ currentPosY);

	}
	/**
	 *Euler's method finding the slope
	 */
	private static void setSlopes() {
		if((nextPosX>currentPosX)&&(get_height(nextPosX, currentPosY)>get_height(currentPosX, currentPosY))){
	        DzDx = (get_height(nextPosX, currentPosY) - get_height(currentPosX, currentPosY))/stepSize;
	    }else if((currentPosX>nextPosX)&&(get_height(currentPosX, currentPosY)>get_height(nextPosX, currentPosY))){
	        DzDx = (get_height(currentPosX, currentPosY) - get_height(nextPosX, currentPosY))/stepSize;
	    }else if((nextPosX>currentPosX)&&(get_height(nextPosX, currentPosY)<get_height(currentPosX, currentPosY))){
	        DzDx = -((get_height(currentPosX, currentPosY) - get_height(nextPosX, currentPosY))/stepSize);
	    }else{
	        DzDx = -((get_height(nextPosX, currentPosY) - get_height(currentPosX, currentPosY))/stepSize);
	    }
	    if((nextPosY>currentPosY)&&(get_height(currentPosX, nextPosY)>get_height(currentPosX, currentPosY))){
	        DzDy = (get_height(currentPosX, nextPosY) - get_height(currentPosX, currentPosY))/stepSize;
	    }else if((currentPosY>nextPosY)&&(get_height(currentPosX, currentPosY)>get_height(nextPosX, currentPosY))){
	        DzDy = (get_height(currentPosX, currentPosY) - get_height(currentPosX, nextPosY))/stepSize;
	    }else if((nextPosY>currentPosY)&&(get_height(nextPosX, nextPosY)<get_height(currentPosX, currentPosY))){
	        DzDy = -((get_height(currentPosX, currentPosY) - get_height(currentPosX, nextPosY))/stepSize);
	    }else{
	        DzDy = -((get_height(currentPosX, nextPosY) - get_height(currentPosX, currentPosY))/stepSize);
	    }

	}
	/**
	 * Setting the acceleration using the formula provided
	 */
	private static void setAcceleration() {
		accelerationX = -(float)(g*DzDx) - (float)(mu*g*(currentVelX/(Math.sqrt((currentVelX*currentVelX) + (currentVelY*currentVelY)))));
		accelerationY = -(float)(g*DzDy) - (float)(mu*g*(currentVelY/(Math.sqrt((currentVelX*currentVelX) + (currentVelY*currentVelY)))));
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