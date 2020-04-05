package Model;

import com.mygdx.game.CourseShaper;
import com.mygdx.game.PuttingCourse;

public class EulerSolver implements PhysicsEngine {

	public static int fps = 165;
	
	String[] hold;
	CourseShaper shape;
	
	public  float stepSize = 0.00001f;
		
		//just storing the previous position to check when to stop loop

		public float previousPosX;
		public float previousPosY;

		
		public float currentPosX;
		public float currentPosY;
		public float currentPosZ;
		
		//this is x0 and y0
		public float currentVelX;
		public float currentVelY;
		
		//this is x1 and y1
		public float nextPosX;
		public float nextPosY;
		
		//this is the the h,x(x,y) and h,y(x,y), the first derivative of the height function using Euler's method
		public float DzDx;
		public float DzDy;
		
		public float accelerationX;
		public float accelerationY;
		
		//the variables they require for the import, some not used yet:
		public float g = 9.81f;
		public float m = 45.93f;
		public float mu = 0.3f;
		public float vmax= 15.0f;
		public float tol = 0.02f;
		
		public float goalX = 0.0f;
		public float goalY = 10.0f;
		
		public EulerSolver(String ab) {
			shape = new CourseShaper(ab);
		}
		

	//(Math.round(currentPosX*1000000)!=Math.round(previousPosX*1000000))||(Math.round(currentPosY*1000000))!=(Math.round(previousPosY*1000000))

	/**
	 * @param x Co-ordinate
	 * @param y Co-ordinate
	 * @return Height at Given x, y.
	 */
		public float get_height(float x, float y) {
			return (float)shape.evaluate(new Vector2d(x,y));
			//return (float)(Math.sin(x) + y*y);
		}
		
		public void NextStep() {
			//each method required per step:
			//it's looping until the current position and the previous position stays the same
			//acceleration never reaches 0, the net acceleration between the previous step and the current step reaches 0 thus the positions does not change, this is the logic i am most unsure about
			//and also why based odd of the formula's and implementation they explained in the appendix mass does not effect the resulting movement
//			do {
				
			
			setNextPositions();
			setSlopes();
			setAcceleration();
			setNextVelocities();	
				
				previousPosX = currentPosX;
				previousPosY = currentPosY;
				currentPosX= nextPosX;
				currentPosY = nextPosY;
//				System.out.println(accelerationX + " "+previousPosX + " " + previousPosY + " "+ currentPosX +" "+ currentPosY);

//			} while ((Math.round(currentPosX*1000000)!=Math.round(previousPosX*1000000))||(Math.round(currentPosY*1000000))!=(Math.round(previousPosY*1000000)));
			//System.out.println(accelerationX + " " + accelerationY + " "+ currentPosX +" "+ currentPosY);

		}
		/**
		 *Euler's method finding the slope
		 */
		private void setSlopes() {
			float change = stepSize;
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
			}else if((currentPosY>nextPosY)&&(get_height(currentPosX, currentPosY)>get_height(currentPosX, nextPosY))){
				DzDy = (get_height(currentPosX, currentPosY) - get_height(currentPosX, currentPosY-change))/stepSize;
			}else if((nextPosY>currentPosY)&&(get_height(currentPosX, nextPosY)<get_height(currentPosX, currentPosY))){
				DzDy = -((get_height(currentPosX, currentPosY) - get_height(currentPosX, currentPosY+change))/stepSize);
			}else{
				DzDy = -((get_height(currentPosX, currentPosY-change) - get_height(currentPosX, currentPosY))/stepSize);
			}

		}
		/**
		 * Setting the acceleration using the formula provided
		 */
		private void setAcceleration() {
			accelerationX = -(float)(g*DzDx) - (float)(mu*g*(currentVelX/(Math.sqrt((currentVelX*currentVelX) + (currentVelY*currentVelY)))));
			accelerationY = -(float)(g*DzDy) - (float)(mu*g*(currentVelY/(Math.sqrt((currentVelX*currentVelX) + (currentVelY*currentVelY)))));
		}
		/**
		 * Setting the next position, after a step, by multiplying the current velocity by the step size
		 */
		private void setNextPositions() {
			nextPosX = currentPosX + currentVelX/fps;
			nextPosY = currentPosY + currentVelY/fps;
		}
		
		/**
		 * Setting the new velocity after a step
		 */
		private void setNextVelocities() {
			currentVelX = currentVelX + accelerationX/fps;
			currentVelY = currentVelY + accelerationY/fps;
			
			if (currentVelX < -vmax) {
				currentVelX = -vmax;
			}
			else if (currentVelX > vmax){
				currentVelX = vmax;
			}
			
			if (currentVelY < -vmax) {
				currentVelY = -vmax;
			}
			else if (currentVelY > vmax){
				currentVelY = vmax;
			}
		}
		
		public void setPosX(float d) {
			currentPosX = d;
		}
		
		public void setPosY(float d) {
			currentPosY = d;
		}
		
		public void setPosZ(float d) {
			currentPosZ = d;
		}
		
		public void setVelX(float d) {
			currentVelX = d;
		}
		
		public void setVelY(float d) {
			currentVelY = d;
		}
		
		public float getPosX() {
			return currentPosX;
		}
		
		public float getPosY() {
			return currentPosY;
		}
		
		public float getPosZ() {
			return currentPosZ;
		}
		
		public float getVelX() {
			return currentVelX;
		}
		
		public float getVelY() {
			return currentVelY;
		}
		
		public void setMu(float mu) {
			this.mu = mu;
		}
		
		public void setVMax(float vMax) {
			this.vmax = vMax;
		}
		
	}
