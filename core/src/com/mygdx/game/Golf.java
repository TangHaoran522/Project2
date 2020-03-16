package com.mygdx.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
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

import Model.Ball;

public class Golf  implements ApplicationListener {
	
	PerspectiveCamera cam;
	CameraInputController camController;
	ModelBatch modelBatch;
	Array<ModelInstance> instances;
	Environment environment;
	Model model;
	ModelInstance ground;
	ModelInstance ball;
	ModelInstance wall;
	ModelInstance groundBall;
	boolean collision;

	btCollisionShape groundShape;
	btCollisionShape ballShape;
	btCollisionShape wallShape;

	btCollisionObject groundObject;
	btCollisionObject ballObject;
	btCollisionObject wallObject;

	btCollisionConfiguration collisionConfig;
	btDispatcher dispatcher;
	
	Ball golfBall;
	
	int fps;
	
	public float posX;
	public float posY;
	public float posZ;
	
	public float VX;
	public float VY;

	@Override
	public void create () {
		Bullet.init();
		golfBall = new Ball();

		fps = golfBall.fps;
		
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
		model = mb.end();

		// create a modelinstance of the new model
		ground = new ModelInstance(model, "ground");
		ball = new ModelInstance(model, "ball");
		wall = new ModelInstance(model, "wall");
		groundBall = new ModelInstance(model, "groundBalls");
		
		// translate is to a certain position if wanted
		// remember that the middle of the object is 0.0.0
		ball.transform.setToTranslation(0, 1f, 0);
		// add the modelinstance of the object to the array of objects that has to be rendered
		instances = new Array<ModelInstance>();
		instances.add(ball);
		
		for (float j = -5f; j <= 5f; j += 0.3f) {
			for (float i = 0; i <= 199; i += 0.3f) {
				groundBall = new ModelInstance(model, "groundBalls");
				groundBall.transform.setToTranslation(i,golfBall.get_height(i,j)-0.25f, j);
				instances.add(groundBall);
			}
		}

		// give the object a collision shape if you want it to have collision
		ballShape = new btSphereShape(0.5f);
		groundShape = new btBoxShape(new Vector3(2.5f, 0.5f, 2.5f));
		wallShape = new btBoxShape(new Vector3(2.5f, 1f, .5f));

		// create a collision object with that shape
		groundObject = new btCollisionObject();
		groundObject.setCollisionShape(groundShape);
		groundObject.setWorldTransform(ground.transform);

		ballObject = new btCollisionObject();
		ballObject.setCollisionShape(ballShape);
		ballObject.setWorldTransform(ball.transform);
		
		wallObject = new btCollisionObject();
		wallObject.setCollisionShape(wallShape);
		ballObject.setWorldTransform(wall.transform);

		collisionConfig = new btDefaultCollisionConfiguration();
		dispatcher = new btCollisionDispatcher(collisionConfig);
		
		golfBall.currentPosX = 0;
		golfBall.currentPosY = 0;
		golfBall.currentPosZ = 2f;
		
		golfBall.currentVelX = 3f;
		golfBall.currentVelY = 0f;

	}

	@Override
	public void render () {
		final float delta = Math.min(1f / 30f, Gdx.graphics.getDeltaTime());

			
//			posX += VX;
//			posY += VY;
			golfBall.NextStep();
			golfBall.currentPosZ = heightFormula(golfBall.currentPosX*fps, golfBall.currentPosY*fps);
			System.out.println(golfBall.currentPosZ);
			
			ball.transform.translate(golfBall.currentVelX, (golfBall.currentPosZ - golfBall.get_height(golfBall.currentPosX*60+golfBall.currentVelX, golfBall.currentPosY*60+golfBall.currentVelY)), golfBall.currentVelY);
			ballObject.setWorldTransform(ball.transform);

		cam.position.set(golfBall.currentPosX*fps - 5f, 5f, 0);
		cam.update();
		camController.update();


		Gdx.gl.glClearColor(0.3f, 0.3f, 0.3f, 1.f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

		modelBatch.begin(cam);
		modelBatch.render(instances, environment);
		modelBatch.end();
	}

	boolean checkCollision () {
		// add a collisionObject wrapper for your new object
		CollisionObjectWrapper co0 = new CollisionObjectWrapper(ballObject);
		CollisionObjectWrapper co1 = new CollisionObjectWrapper(groundObject);
		CollisionObjectWrapper co2 = new CollisionObjectWrapper(wallObject);

		btCollisionAlgorithmConstructionInfo ci = new btCollisionAlgorithmConstructionInfo();
		ci.setDispatcher1(dispatcher);
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

		dispatcher.dispose();
		collisionConfig.dispose();

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
	
	public static float heightFormula(float x, float y) {
		return (float)(Math.sin(x) + Math.pow(Math.abs(y), 1.5));
	}
}