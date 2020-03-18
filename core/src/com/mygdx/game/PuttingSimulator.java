package com.mygdx.game;

import Model.Vector2d;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.Bullet;
import com.badlogic.gdx.physics.bullet.collision.CollisionObjectWrapper;
import com.badlogic.gdx.physics.bullet.collision.btBoxShape;
import com.badlogic.gdx.physics.bullet.collision.btCollisionAlgorithm;
import com.badlogic.gdx.physics.bullet.collision.btCollisionAlgorithmConstructionInfo;
import com.badlogic.gdx.physics.bullet.collision.btCollisionConfiguration;
import com.badlogic.gdx.physics.bullet.collision.btCollisionDispatcher;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObject;
import com.badlogic.gdx.physics.bullet.collision.btCollisionShape;
import com.badlogic.gdx.physics.bullet.collision.btDefaultCollisionConfiguration;
import com.badlogic.gdx.physics.bullet.collision.btDispatcher;
import com.badlogic.gdx.physics.bullet.collision.btDispatcherInfo;
import com.badlogic.gdx.physics.bullet.collision.btManifoldResult;
import com.badlogic.gdx.physics.bullet.collision.btSphereBoxCollisionAlgorithm;
import com.badlogic.gdx.physics.bullet.collision.btSphereShape;
import com.badlogic.gdx.utils.Array;
import Model.PhysicsEngine;


public class PuttingSimulator extends Game implements Screen{
    private PuttingCourse course;
    private PhysicsEngine eulerSolver;

    private Vector2d ballPosition;

    PerspectiveCamera cam;
    CameraInputController camController;
    ModelBatch modelBatch;
    Array<ModelInstance> instances;
    Environment environment;
    Model model;
    ModelInstance groundBall;
    ModelInstance ball;
    ModelInstance flagPole;
    ModelInstance flag;

    Main main;
    OptionMenu menu;

    btCollisionShape groundShape;
    btCollisionShape ballShape;
    btCollisionShape wallShape;

    btCollisionObject groundObject;
    btCollisionObject ballObject;
    btCollisionObject wallObject;

    int count;


    public PuttingSimulator(PuttingCourse course, PhysicsEngine euler){
        this.course=course;
        this.eulerSolver=euler;
    }
    public PuttingSimulator(PuttingCourse course, PhysicsEngine euler, Main main, OptionMenu menu){
        this.course=course;
        this.eulerSolver=euler;
        this.main=main;
        this.menu=menu;

    }
    public void set_ball_position(Vector2d v){
        this.ballPosition=v;
    }

    //has to be Vector2d i think
    public void get_ball_position(){
       // return this.ballPosition;
    }

    public void take_shot(Vector2d initial_ball_velocity){
    	eulerSolver.setVelX((float)initial_ball_velocity.getX());
    	eulerSolver.setVelY((float)initial_ball_velocity.getY());
    }

    @Override
    public void create() {
        Bullet.init();
        this.ballPosition=this.course.get_start_position();


        modelBatch = new ModelBatch();
        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
        environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));

        cam = new PerspectiveCamera(105, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        cam.position.set(3f, 7f, 10f);
        cam.update();

        camController = new CameraInputController(cam);
        Gdx.input.setInputProcessor(camController);

        // to create a new object add the id to the mb.node()
        // then call mb.part and give the type of object, shape etc
        ModelBuilder mb = new ModelBuilder();
        mb.begin();
        mb.node().id = "ground";
        mb.part("box", GL20.GL_TRIANGLES, Usage.Position | Usage.Normal, new Material(ColorAttribute.createDiffuse(Color.RED)))
                .box(5f, 1f, 5f);
        mb.node().id = "ball";
        mb.part("sphere", GL20.GL_TRIANGLES, Usage.Position | Usage.Normal, new Material(ColorAttribute.createDiffuse(Color.WHITE)))
                .sphere(1f, 1f, 1f, 10, 10);
        mb.node().id = "wall";
        mb.part("wall",  GL20.GL_TRIANGLES, Usage.Position | Usage.Normal, new Material(ColorAttribute.createDiffuse(Color.RED)))
                .box(5f, 2f, 1f);
        mb.node().id = "groundBalls";
        mb.part("sphere", GL20.GL_TRIANGLES, Usage.Position | Usage.Normal, new Material(ColorAttribute.createDiffuse(Color.GREEN)))
                .sphere(0.5f, 0.5f, 0.5f, 5, 5);
        mb.node().id = "flagpole";
        mb.part("cylinder", GL20.GL_TRIANGLES, Usage.Position | Usage.Normal, new Material(ColorAttribute.createDiffuse(Color.GRAY)))
        		.cylinder(0.5f, 5f, 0.5f, 10);
        mb.node().id = "flag";
        mb.part("box", GL20.GL_TRIANGLES, Usage.Position | Usage.Normal, new Material(ColorAttribute.createDiffuse(Color.RED)))
        		.box(0.25f,1f,1f);
        model = mb.end();

        // create a modelinstance of the new model

        ball = new ModelInstance(model, "ball");
        groundBall = new ModelInstance(model, "groundBalls");
      //TODO: double check variables
        // translate is to a certain position if wanted
        // remember that the middle of the object is 0.0.0
        ball.transform.setToTranslation((float)course.get_start_position().getX(),
                (float)course.get_height().evaluate(new Vector2d(course.get_start_position().getX(),course.get_start_position().getY())) + 1f,
                (float)course.get_start_position().getY());
        // add the modelinstance of the object to the array of objects that has to be rendered
        instances = new Array<ModelInstance>();
        instances.add(ball);


        for (float j = -5f; j <= 5f; j += 0.3f) {
            for (float i = 0; i <= 199; i += 0.3f) {
                groundBall = new ModelInstance(model, "groundBalls");
                groundBall.transform.setToTranslation(i,(float)course.get_height().evaluate(new Vector2d(i,j))-.25f, j);
                instances.add(groundBall);
            }
        }
        
        flagPole = new ModelInstance(model, "flagpole");
        flagPole.transform.setToTranslation((float)course.get_flag_position().getX(), 2.5f + (float)course.get_height().evaluate(new Vector2d(course.get_flag_position().getX(), course.get_flag_position().getY())), (float)course.get_flag_position().getY());
        instances.add(flagPole);
        
        flag = new ModelInstance(model, "flag");
        flag.transform.setToTranslation((float)course.get_flag_position().getX(),(float)course.get_height().evaluate(new Vector2d(course.get_flag_position().getX(), course.get_flag_position().getY())) + 4.5f, (float)course.get_flag_position().getY()- .5f);
        instances.add(flag);
        
        // give the object a collision shape if you want it to have collision
        ballShape = new btSphereShape(0.5f);
        groundShape = new btBoxShape(new Vector3(2.5f, 0.5f, 2.5f));
        wallShape = new btBoxShape(new Vector3(2.5f, 1f, .5f));

        // create a collision object with that shape
        groundObject = new btCollisionObject();
        groundObject.setCollisionShape(groundShape);
        //groundObject.setWorldTransform(ground.transform);

        ballObject = new btCollisionObject();
        ballObject.setCollisionShape(ballShape);
        ballObject.setWorldTransform(ball.transform);

        wallObject = new btCollisionObject();
        wallObject.setCollisionShape(wallShape);
      //  ballObject.setWorldTransform(wall.transform);

//        collisionConfig = new btDefaultCollisionConfiguration();
//        dispatcher = new btCollisionDispatcher(collisionConfig);

        eulerSolver.setPosX((float)ballPosition.getX());
        eulerSolver.setPosY((float)ballPosition.getY());
        eulerSolver.setPosZ(eulerSolver.get_height((float)ballPosition.getX(), (float)ballPosition.getY()) + 1f);


        take_shot(calcInit());
        System.out.println(course.get_flag_position().getX() + " " + course.get_flag_position().getY());
        
        count = 0;
    }

    @Override
    public void render (float delta) {

        //TODO: add game over
        if (((((course.get_flag_position().getX() - course.get_hole_tolerance() <= this.ballPosition.getX()) &&
                (this.ballPosition.getX() <= course.get_flag_position().getX()+ course.get_hole_tolerance()))
                &&((course.get_flag_position().getY() - course.get_hole_tolerance() <= this.ballPosition.getY())
                && (this.ballPosition.getY() <= course.get_flag_position().getY() + course.get_hole_tolerance())))
                && (eulerSolver.getVelX()<= 5 && eulerSolver.getVelY()<= 5)) || count == 2*60) {
            main.setScreen(new Menu(main));
        }
        else {

        	System.out.println(eulerSolver.getVelX() + " " + eulerSolver.getVelY());

        	eulerSolver.NextStep();
        	eulerSolver.setPosZ(eulerSolver.get_height(eulerSolver.getPosX(), eulerSolver.getPosY()));

//            System.out.println(golfBall.currentPosZ + " " + (golfBall.currentPosZ - golfBall.get_height(golfBall.currentPosX+golfBall.currentVelX, golfBall.currentPosY+golfBall.currentVelY)));

        	this.ballPosition.setX(eulerSolver.getPosX());
        	this.ballPosition.setY(eulerSolver.getPosY());
        	
            ball.transform.setToTranslation(eulerSolver.getPosX(), eulerSolver.getPosZ()+.5f, eulerSolver.getPosY());
            ballObject.setWorldTransform(ball.transform);

            cam.position.set(eulerSolver.getPosX() - 5f, Math.max(5f,eulerSolver.getPosZ()+3f), eulerSolver.getPosY());
            cam.update();
            camController.update();


            Gdx.gl.glClearColor(0.3f, 0.3f, 0.3f, 1.f);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

            modelBatch.begin(cam);
            modelBatch.render(instances, environment);
            modelBatch.end();
            
            if (Math.abs(eulerSolver.getVelX()) <= 0.2f &&  Math.abs(eulerSolver.getVelY()) <= 0.2f) {
            	count++;
            }
            else {
            	count = 0;
            }

        }
    }

    boolean checkCollision () {
        // add a collisionObject wrapper for your new object
        CollisionObjectWrapper co0 = new CollisionObjectWrapper(ballObject);
        CollisionObjectWrapper co1 = new CollisionObjectWrapper(groundObject);
        CollisionObjectWrapper co2 = new CollisionObjectWrapper(wallObject);

        btCollisionAlgorithmConstructionInfo ci = new btCollisionAlgorithmConstructionInfo();
 //       ci.setDispatcher1(dispatcher);
        btCollisionAlgorithm algorithmBallGround = new btSphereBoxCollisionAlgorithm(null, ci, co0.wrapper, co1.wrapper, false);
        btCollisionAlgorithm algorithmBallWall = new btSphereBoxCollisionAlgorithm(null, ci, co0.wrapper, co2.wrapper, false);

        btDispatcherInfo info = new btDispatcherInfo();
        btManifoldResult resultBallGround = new btManifoldResult(co0.wrapper, co1.wrapper);
        btManifoldResult resultBallWall = new btManifoldResult(co0.wrapper, co2.wrapper);

        algorithmBallGround.processCollision(co0.wrapper, co1.wrapper, info, resultBallWall);

        boolean r = resultBallWall.getPersistentManifold().getNumContacts() > 0;

        resultBallWall.dispose();
        info.dispose();
        algorithmBallGround.dispose();
        ci.dispose();
        co1.dispose();
        co0.dispose();

        return r;
    }

    @Override
    public void dispose () {
        groundObject.dispose();
        groundShape.dispose();

        ballObject.dispose();
        ballShape.dispose();

//        dispatcher.dispose();
//        collisionConfig.dispose();

        modelBatch.dispose();
        model.dispose();
    }

    @Override
    public void pause () {
    }

    @Override
    public void resume () {
    }

    @Override
    public void resize (int width, int height) {
    }
    //TODO: height formula becomes puttingCourse.getHeight().evaluate(vector2D)
    public static float heightFormula(float x, float y) {
        return (float)(Math.sin(x) + Math.pow(Math.abs(y), 1.5));
    }
    public Vector2d calcInit() {

    	return new Vector2d(Math.cos(menu.angle/360*2*Math.PI)*menu.velocity, Math.sin(menu.angle/360*2*Math.PI)*menu.velocity);

    }

    @Override
    public void show() {


    }

    @Override
    public void hide() {

    }
    
    public void setOption(OptionMenu option) {
    	this.menu = option;
    }
}