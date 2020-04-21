package Model;

public interface PhysicsEngine {
	
	
	public double get_height(double x, double y);
	public void NextStep();
	
	public void setPosX(double d);
	public void setPosY(double d);// => change to..
	public void setPosition(Vector2d v);
	public void setPosZ(double d);
	
	public void setVelX(double d);
	public void setVelY(double d);//=> changes to
	public void setVelocity(Vector2d v);
	
	public void setMu(double mu);
	public void setVMax (double vMax);
	
	public double getPosX();
	public double getPosY();//=>change to...
	public Vector2d getPosition();
	public double getPosZ();
	
	public double getVelX();
	public double getVelY();
	
}
