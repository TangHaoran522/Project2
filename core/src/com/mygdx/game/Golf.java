package com.mygdx.game;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
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
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.graphics.PerspectiveCamera;


public class Golf extends Game {
	public PerspectiveCamera cam;
	public ModelBatch modelBatch;
	public Model model;
	public Environment environment;
	public CameraInputController camController;
    ModelInstance ground;
    ModelInstance ball;
    Array<ModelInstance> instances;
    boolean collision;

    
	@Override
	public void create () {
		modelBatch = new ModelBatch();
		
		 environment = new Environment();
	        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
	        environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));


		cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		cam.position.set(10f, 10f, 10f);
		cam.lookAt(0,0,0);
		cam.near = 0.1f;
		cam.far = 300f;
		cam.update();
		
		camController = new CameraInputController(cam);
        Gdx.input.setInputProcessor(camController);
        
        ModelBuilder mb = new ModelBuilder();
        mb.begin();
        mb.node().id = "ground";
        mb.part("box", GL20.GL_TRIANGLES, Usage.Position | Usage.Normal, new Material(ColorAttribute.createDiffuse(Color.RED)))
            .box(5f, 1f, 5f);
        mb.node().id = "ball";
        mb.part("sphere", GL20.GL_TRIANGLES, Usage.Position | Usage.Normal, new Material(ColorAttribute.createDiffuse(Color.GREEN)))
            .sphere(1f, 1f, 1f, 10, 10);
        model = mb.end();
        
        ground = new ModelInstance(model, "ground");
        ball = new ModelInstance(model, "ball");
        ball.transform.setToTranslation(0, 9f, 0);

        instances = new Array<ModelInstance>();
        instances.add(ground);
        instances.add(ball);
        
        
	}

	@Override
	public void render () {
		camController.update();
		
		Gdx.gl20.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		
		modelBatch.begin(cam);
		modelBatch.render(instances, environment);
        modelBatch.end();
        
        final float delta = Math.min(1f/30f, Gdx.graphics.getDeltaTime());

        if (!collision) {
            ball.transform.translate(0f, -delta, 0f);
            collision = checkCollision();
        }
	}
	
	@Override
	public void dispose () {
		modelBatch.dispose();
	}
	
	public static float heightFormula(float x, float y) {
		return (float)(Math.sin(x) + y*y);
	}
	
	boolean checkCollision() {
        return false;
    }
}
