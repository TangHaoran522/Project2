package Model;

public class Vector2d {
    private double x;
    private double y;

    public Vector2d(double x, double y){
        this.x=x;
        this.y=y;
    }
    public double getX(){
        return this.x;
    }
    public double getX2(){
        return x*x;
    }
    public double getY(){
        return this.y;
    }
    public double getY2(){
        return y*y;
    }
    public void setX(double d){
        this.x=d;
    }
    public void setY(double d){
        this.y=d;
    }
}
