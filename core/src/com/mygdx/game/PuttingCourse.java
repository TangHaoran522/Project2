package com.mygdx.game;


import Model.Function2d;
import Model.Vector2d;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.utils.Array;
//import jdk.nashorn.internal.objects.annotations.Function;

public class PuttingCourse{
    private Function2d height;
    private Vector2d flag;
    private Vector2d start;
    private double friction;
    private double maximumVelocity;
    private double holeTolerance;


    /**
     * constructor
     * @param height function for the shape of the terrain
     * @param flag position of the hole
     * @param start position of the ball at the start of the game
     */
    public PuttingCourse(Function2d height, Vector2d flag, Vector2d start){
        this.height=height;
        this.flag=flag;
        this.start=start;
        this.friction =  0.131;
        this.maximumVelocity=10.0;
        this.holeTolerance = 0.5f;
    }
//TODO: should we move everthing related to the course modeling etc here?

    /**
     * accessor get the instance model for the terrain
     * @param model
     * @return
     */
    public Array<ModelInstance> getCourseModel(Model model){

        ModelBuilder mb = new ModelBuilder();
        mb.begin();
        Array<ModelInstance> instances = new Array<>();
        mb.node().id = "groundBalls";
//        mb.manage(new Texture(Gdx.files.internal("Grass.jpg")));
        mb.part("parcel", GL20.GL_TRIANGLES, VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal, new Material(ColorAttribute.createDiffuse(Color.GREEN)))
                .sphere(0.5f, 0.5f, 0.5f, 5, 5);
        mb.node().id = "waterBalls";
//      mb.manage(new Texture(Gdx.files.internal("Grass.jpg")));
      mb.part("parcel", GL20.GL_TRIANGLES, VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal, new Material(ColorAttribute.createDiffuse(Color.BLUE)))
              .sphere(0.5f, 0.5f, 0.5f, 5, 5);

        model = mb.end();
        ModelInstance groundBall = new ModelInstance(model, "groundBalls");
        for (float j = -15f; j <= 15f; j += 0.3f) {
            for (float i = -50; i <= 149; i += 0.3f) {
            	
            	if ((float)get_height().evaluate(new Vector2d(i,j)) < 0) {
            		groundBall = new ModelInstance(model, "waterBalls");
            		groundBall.transform.setToTranslation(i,-.25f, j);
            		instances.add(groundBall);
            	}
            	groundBall = new ModelInstance(model, "groundBalls");
                groundBall.transform.setToTranslation(i,(float)get_height().evaluate(new Vector2d(i,j))-.25f, j);
                instances.add(groundBall);
            }
        }

        return instances;
    }

    /**
     * accessor
     * @return get the function shaping the course
     */
    public Function2d get_height(){
        return this.height;
    }

    /**
     * accessor
     * @return flag position
     */
    public Vector2d get_flag_position(){
        return this.flag;
    }

    /**
     * accessor
     * @return get start position
     */
    public Vector2d get_start_position(){
        return this.start;
    }

    /**
     * mutator
     * @param d define the start position
     */
    public void set_start_position(Vector2d d) {
    	this.start = d;
    }

    /**
     * mutator
     * @param d define hole position
     */
    public void set_flag_positon(Vector2d d) {
    	this.flag = d;
    }

    /**
     * accessor
     * @return friction coefficient
     */
    public double get_friction_coefficient(){
        return this.friction;
    }

    /**
     * accessor
     * @return maximum speed authorized by this course
     */
    public double get_maximum_velocity(){
        return this.maximumVelocity;
    }

    /**
     * accessor
     * @return the radius of the hole / tolerance for the win
     */
    public double get_hole_tolerance(){
        return this.holeTolerance;
    }

    /**
     * mutator
     * @param tol the radius of the hole / tolerance for the win
     */
    public void set_hole_tolerance(double tol) {
    	this.holeTolerance = tol;
    }

    /**
     * mutator
     * @param add change the function shapping the course
     */
    public void set_Func2d(Function2d add) {
    	this.height = add;
    }
}
