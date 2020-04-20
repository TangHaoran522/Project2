package Model;

public interface PhysicsEngine {
	
	
	public double get_height(double x, double y);
	public void NextStep();
	
	public void setPosX(double d);
	public void setPosY(double d);
	public void setPosZ(double d);
	
	public void setVelX(double d);
	public void setVelY(double d);
	
	public void setMu(double mu);
	public void setVMax (double vMax);
	
	public double getPosX();
	public double getPosY();
	public double getPosZ();
	
	public double getVelX();
	public double getVelY();
	
}
