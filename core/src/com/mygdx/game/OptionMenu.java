package com.mygdx.game;

import Model.Vector2d;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class OptionMenu implements Screen {

    String decimalPattern = "([0-9]*)\\.([0-9]*)";
    String naturalPattern = "([0-9]*)";

    private final int BUTTON_WIDTH = 300;
    private final int BUTTON_HEIGHT = 100;

    private final int EXIT_HEIGHT = 75;

    public float velocity = 15;
    public float angle = 15;

    Texture exitButtonActive;
    Texture exitButtonInactive;

    private Main main;
    private Stage stage;

    TextField textFieldSpeed;
    TextField textFieldAngle;
    TextButton buttonShot;

    Label shotLabelSpeed;
    Label shotLabelAngle;

    Label loadMap;
    Label loadSpeed;
    
    Label mode2;

    TextField loadMapTF;
    TextField loadSpeedTF;

    TextButton loadMapB;
    TextButton loadSpeedB;
    
    public String course;
    public float mu;
    public float vMax;
    public float goalRadius;
    public Vector2d start;
    public Vector2d finish;
    
    public int count;

    public OptionMenu(Main main){
        this.main = main;
        Skin skin = new Skin(Gdx.files.internal("uiskin.json"));
        
        mode2 = new Label("enter file here for mode 2", skin);
        mode2.setPosition(150, main.HEIGHT-540);

        shotLabelSpeed= new Label("Speed:", skin);
        shotLabelAngle = new Label("Angle:", skin);

        shotLabelSpeed.setSize(100, 60);
        shotLabelSpeed.setPosition(50,main.HEIGHT-100);
        shotLabelAngle.setSize(100, 60);
        shotLabelAngle.setPosition(50,main.HEIGHT-170);

        textFieldSpeed = new TextField("", skin);
        textFieldSpeed.setPosition(150, main.HEIGHT-100);
        textFieldSpeed.setSize(300,60);
        textFieldAngle = new TextField("", skin);
        textFieldAngle.setPosition(150, main.HEIGHT-170);
        textFieldAngle.setSize(300,60);
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        buttonShot = new TextButton("Take a shot!",skin);
        buttonShot.setPosition(470,main.HEIGHT-170);
        buttonShot.setSize(100,30);
        buttonShot.addListener(new ClickListener(){
            @Override
            public void touchUp(InputEvent e, float x, float y, int point, int button){
                if((Pattern.matches(decimalPattern, textFieldSpeed.getText())||textFieldSpeed.getText().matches(naturalPattern))&&
                        (Pattern.matches(decimalPattern, textFieldAngle.getText())||textFieldAngle.getText().matches(naturalPattern)))
                    play(Float.parseFloat(textFieldSpeed.getText()), Float.parseFloat(textFieldAngle.getText()));
                else if(!Pattern.matches(decimalPattern, textFieldSpeed.getText()))
                    textFieldSpeed.setText("");
                else if(!Pattern.matches(decimalPattern, textFieldAngle.getText()))
                    textFieldAngle.setText("");
            }
        });

        loadMap=new Label("Load a Map:",skin);
        loadSpeed = new Label("Load speeds:", skin);

        loadMap.setPosition(50, main.HEIGHT-430);
        loadSpeed.setPosition(50, main.HEIGHT-500);

        loadMapTF = new TextField("", skin);
        loadSpeedTF = new TextField("", skin);

        loadMapTF.setPosition(150, main.HEIGHT-430);
        loadSpeedTF.setPosition(150, main.HEIGHT-500);

        loadMapTF.setSize(300, 60);
        loadSpeedTF.setSize(300, 60);

        loadMapB=new TextButton("Load", skin);
        loadSpeedB = new TextButton("Load", skin);

        loadMapB.setPosition(470,main.HEIGHT-415);
        loadMapB.setSize(100,30);
        loadMapB.addListener(new ClickListener(){
            @Override
            public void touchUp(InputEvent e, float x, float y, int point, int button){
                loadMap(loadMapTF.getText());
            }
        });

        loadSpeedB.setPosition(470,main.HEIGHT-485);
        loadSpeedB.setSize(100,30);
        loadSpeedB.addListener(new ClickListener(){
            @Override
            public void touchUp(InputEvent e, float x, float y, int point, int button){
                loadSpeed(loadSpeedTF.getText());
            }
        });

        stage.addActor(buttonShot);
        stage.addActor(textFieldSpeed);
        stage.addActor(textFieldAngle);
        stage.addActor(shotLabelSpeed);
        stage.addActor(shotLabelAngle);
        stage.addActor(loadMap);
        stage.addActor(loadMapB);
        stage.addActor(loadMapTF);
        stage.addActor(loadSpeed);
        stage.addActor(loadSpeedB);
        stage.addActor(loadSpeedTF);
        stage.addActor(mode2);
        exitButtonActive=new Texture("ExitButtonActive.jpg");
        exitButtonInactive=new Texture("ExitButtonInactive.jpg");
        //TODO: make possible to edit settings and such...
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0.4f,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();

        main.batch.begin();
        if(Gdx.input.getX()<Main.WIDTH-BUTTON_WIDTH-10+BUTTON_WIDTH && Gdx.input.getX() > Main.WIDTH-BUTTON_WIDTH-10
                && Gdx.input.getY()>Main.HEIGHT-(EXIT_HEIGHT+BUTTON_HEIGHT) && Gdx.input.getY()<Main.HEIGHT-EXIT_HEIGHT) {
            main.batch.draw(exitButtonInactive, Main.WIDTH - BUTTON_WIDTH -10 , EXIT_HEIGHT, BUTTON_WIDTH, BUTTON_HEIGHT);
            if(Gdx.input.isTouched()) {
                main.setScreen(new Menu(main));
            }
        }else
            main.batch.draw(exitButtonActive,Main.WIDTH-BUTTON_WIDTH-10,EXIT_HEIGHT, BUTTON_WIDTH, BUTTON_HEIGHT);
        main.batch.end();
    }

    public void play(float speed, float angle){
        this.velocity=speed;
        this.angle=angle;
        Menu hold = new Menu(main);
        hold.setOptionMenu(this);
        main.setScreen(hold);
    }

    public void loadMap(String path){
    	
    	if (path.charAt(path.length() - 1) == 't') {
    	
    	try{
    		
            FileReader fr = new FileReader("C:\\Users\\Jan Super\\git\\Project2\\core\\assets/" + path);
            BufferedReader br = new BufferedReader (fr);
            String line = br.readLine();
            
            path = line;
            
            fr.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch(IOException e){
            e.printStackTrace();
        }
    	}
    
    	String[] holdarray = new String[14];
    	holdarray = path.split(" ");
    	mu = Float.parseFloat(holdarray[7]);
    	vMax = Float.parseFloat(holdarray[8]);
    	start = new Vector2d(Float.parseFloat(holdarray[9]), Float.parseFloat(holdarray[10]));
    	finish = new Vector2d(Float.parseFloat(holdarray[11]), Float.parseFloat(holdarray[12]));
    	goalRadius = Float.parseFloat(holdarray[13]);
    	
    	path = holdarray[0];
    	for (int i = 1; i <= 6; i++) {
    		path += " ";
    		path += holdarray[i];
    	}
    	
    	course = path;
    	Menu hold = new Menu(main);
        hold.setOptionMenu(this);
        hold.newLVL = true;
        main.setScreen(hold);

    }

    public void loadSpeed(String path){
    	count = main.count;
        try{
            FileReader fr = new FileReader("C:\\Users\\Jan Super\\git\\Project2\\core\\assets/" + path);
            BufferedReader br = new BufferedReader(fr);
            String line = "";
            for (int i = 0; i <= count; i++) {
            	line = br.readLine();
            }
            count++;
            
            String[] holda = line.split(" ");
            this.velocity = Float.parseFloat(holda[0]);
            this.angle = Float.parseFloat(holda[1]);
//            double x=0, y=0;
//            int ycounter = 0;
//            int xcounter =0;
//            ArrayList<Vector2d> speeds = new ArrayList<>();
//            while(line!= null){
//                String[] terms = line.split(",");
//                if(terms.length==2){
//                    if((terms[0].matches(decimalPattern)||terms[0].matches(naturalPattern))&&
//                            (terms[1].matches(decimalPattern)||terms[1].matches(naturalPattern)))
//                        speeds.add(new Vector2d(Double.parseDouble(terms[0]), Double.parseDouble(terms[1])));
//                }else
//                for(int i=0; i<terms.length;i++){
//                    if(terms[i].contains("x") && i+1 < terms.length && (Pattern.matches(terms[i+1], decimalPattern) ||
//                            Pattern.matches(terms[i+1], naturalPattern))) {
//                        x=Double.parseDouble(terms[i+1]);
//                        xcounter ++;
//                    }
//                    else if(terms[i].contains("y") && i+1 < terms.length && (Pattern.matches(terms[i+1], decimalPattern) ||
//                            Pattern.matches(terms[i+1], naturalPattern))) {
//                        y=Double.parseDouble(terms[i+1]);
//                        ycounter++;
//                    }
//                    else if(xcounter == ycounter && xcounter !=0){
//                        speeds.add(new Vector2d(x,y));
//                        xcounter = 0;
//                        ycounter = 0 ;
//                    }
//                }
//                line = br.readLine();
//            }
            
            
            fr.close();
            main.count++;
            Menu hold = new Menu(main);
            hold.setOptionMenu(this);
            main.setScreen(hold);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch(IOException e){
            e.printStackTrace();
        }
        
        

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
