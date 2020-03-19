package Model;

public interface PhysicsEngine {
	
	
	public float get_height(float x, float y); 
	public void NextStep();
	
	public void setPosX(float d);
	public void setPosY(float d);
	public void setPosZ(float d);
	
	public void setVelX(float d);
	public void setVelY(float d);
	
	public void setMu(float mu);
	
	public float getPosX();
	public float getPosY();
	public float getPosZ();
	
	public float getVelX();
	public float getVelY();
	
}
