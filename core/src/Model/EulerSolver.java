package Model;

public class EulerSolver implements PhysicsEngine {

        private double stepSize = 0.00001;

        //just storing the previous position to check when to stop loop
        private Vector2d previousPosition;


        private Vector2d currentPosition;

        //this is x0 and y0
        private Vector2d currentVelocity;

        //this is x1 and y1
        private Vector2d nextPosition;

        //this is the the h,x(x,y) and h,y(x,y), the first derivative of the height function using Euler's method
        private double DzDx;
        private double DzDy;

        private Vector2d acceleration;

        //the variables they require for the import, some not used yet:
        private double g = 9.81;
        private double m = 45.93;
        private double mu = 0.131;
        private double vmax= 3.0;
        private double tol = 0.02;

        private double goalX = 0.0;
        private double goalY = 10.0;

        public void setStepSize(double h){
            this.stepSize = h;
        }

        //(Math.round(currentPosX*1000000)!=Math.round(previousPosX*1000000))||(Math.round(currentPosY*1000000))!=(Math.round(previousPosY*1000000))
        public double get_height(double x, double y) {
            return ((-0.01*x) + (0.003*(x*x)) + (0.04*y));
        }

        public void main(String args[]) {
            //each method required per step:
            //it's looping until the current position and the previous position stays the same
            //acceleration never reaches 0, the net acceleration between the previous step and the current step reaches 0 thus the positions does not change, this is the logic i am most unsure about
            //and also why based odd of the formula's and implementation they explained in the appendix mass does not effect the resulting movement
            do {
                setNextPositions();
                setSlopes();
                setAcceleration();
                setNextVelocities();
                previousPosition = currentPosition;
                currentPosition = nextPosition;
                System.out.println(acceleration + " "+ previousPosition+ " "+ currentPosition );

            } while ((Math.round(currentPosition.getX()*1000000)!=Math.round(previousPosition.getX()*1000000))||
                    (Math.round(currentPosition.getY()*1000000))!=(Math.round(previousPosition.getY()*1000000)));
            //System.out.println(accelerationX + " " + accelerationY + " "+ currentPosX +" "+ currentPosY);

        }
        /**
         *Euler's method finding the slope
         */
        private void setSlopes() {
            DzDx = (get_height(nextPosition.getX(), currentPosition.getY()) - get_height(currentPosition.getX(), currentPosition.getY())) /stepSize;
            DzDy = (get_height(currentPosition.getX(), nextPosition.getY()) - get_height(currentPosition.getX(), currentPosition.getY()))/stepSize;

        }
        /**
         * Setting the acceleration using the formula provided
         */
        private void setAcceleration() {
            acceleration.setX( -(g*DzDx) - (mu*g*(currentVelocity.getX()/(Math.sqrt((currentVelocity.getX2()) + (currentVelocity.getY2()))))));
            acceleration.setY(-(g*DzDy) - (mu*g*(currentVelocity.getY()/(Math.sqrt((currentVelocity.getX2()) + (currentVelocity.getY2()))))));
        }
        /**
         * Setting the next position, after a step, by multiplying the current velocity by the step size
         */
        private void setNextPositions() {
            nextPosition.setX(currentPosition.getX() + currentVelocity.getX()*stepSize);
            nextPosition.setY(currentPosition.getY() + currentVelocity.getY()*stepSize);

        }

        /**
         * Setting the new velocity after a step
         */
        private void setNextVelocities() {
            currentVelocity.setX(currentVelocity.getX() + acceleration.getX()*stepSize);
            currentVelocity.setY(currentVelocity.getY() + acceleration.getY()*stepSize);
        }




}

