package com.mygdx.snake;
import java.util.ArrayList;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;

/*
 * The overall source class for Snake here
 */
public class Snake extends ApplicationAdapter {
	//batch works for textures, shape for simple shapes and arcs
	SpriteBatch batch;
	BitmapFont score;
	ShapeRenderer shape;
	Snek snek;
	ArrayList<Apple> apples = new ArrayList<Apple>();
	public int[] screenDim = {800, 800};
	private OrthographicCamera camera;
	private long lastMoveTime;
	private float radVel;
	private double radPos = 45;
	private long lastRotateTime;
	private Texture compassImage;
	private Rectangle compassRect;
	final double rad = Math.sqrt(400*400 + 400*400);

	/*
	 * Beginning method, setting up defaults and settings for everything
	 */
	@Override
	public void create () {
		int[] startCoords = {0, 0};
		int[] screenDim = {800, 800};
		int[] screenColor = {0, 0, 0};
		int[] gridIntegers = {16, 16};
		int[] appColor = {255, 0, 0};
		ArrayList<int[]> coord = new ArrayList<int[]>();
		shape = new ShapeRenderer();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, screenDim[0],screenDim[1]);
		camera.zoom += Math.sqrt(2)-1;
		batch = new SpriteBatch();
		score = new BitmapFont();
		compassImage = new Texture(Gdx.files.internal("compassWhite.png"));
		compassRect = new Rectangle();
		keepCompassStill(0.00001f);
		snek = new Snek(startCoords, screenDim, screenColor, 50, coord);
		for(int i=0;i<3;i++){
			Apple app = new Apple(appColor, gridIntegers, snek.gridMap);
			apples.add(i, app);
		}
	}

	/*
	 * The equivalent of the main() method for this library setup, first clear the screen and update the camera.
	 * Then, render all of the various boxes/textures for the snake.
	 * Afterwards, deal with user input, and death events if any.
	 */
	@Override
	public void render () {
		ScreenUtils.clear(0, 0, 0.2f, 1);
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.draw(compassImage, compassRect.x, compassRect.y, compassRect.x, compassRect.y, compassImage.getWidth(),
		compassImage.getHeight(), 1f, 1f, 1f, 0, 0, 
			compassImage.getWidth(), compassImage.getHeight(), false, false);
		score.draw(batch, "Score: " + (snek.length - 4), 0, 800);
		batch.end();
		shape.setProjectionMatrix(camera.combined);
		ArrayList<int[]> rects = snek.rectPlacements();
		shape.begin(ShapeType.Line);
		shape.setColor(1, 0, 0, 1);
		for(int[] box:rects){
			shape.rect(box[0], box[1], 50, 50);}
		shape.rect(0, 0, screenDim[0], screenDim[1]);
		shape.end();
		shape.begin(ShapeType.Filled);
		shape.setColor(1, 1, 0, 1);
		for(int[] box:rects){
			shape.rect(box[0]+1, box[1]+1, 48, 48);}
		shape.setColor(1, 0, 0, 1);
		for(Apple apple:apples){
			int[] appleRect = apple.rectPlacements();
			shape.rect(appleRect[0], appleRect[1], 50, 50);
		}
 		shape.end();
		if(Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) snek.setDir(1, 0);
		if(Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) snek.setDir(-1, 0);
		if(Gdx.input.isKeyJustPressed(Input.Keys.UP)) snek.setDir(0, 1);
		if(Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) snek.setDir(0, -1);

		if(TimeUtils.nanoTime() - lastMoveTime > snek.timelag*2000000){
			lastMoveTime = TimeUtils.nanoTime();
			lastMoveTime = snek.move(lastMoveTime);
			checkAppleCol();
		}
		if(TimeUtils.nanoTime()-lastRotateTime > 40000000){
			lastRotateTime = TimeUtils.nanoTime();
			rotateCamera();
		}
		if(snek.dead){
			pause();
			dispose();
			System.exit(0);
		}
	}

	/*
	 * Cleaning up at the end if the game has ended.
	 */
	@Override
	public void dispose () {
		batch.dispose();
		shape.dispose();
	}

	/*
	 * A quick check for if the snake has collided with the apple.
	 * If so, generate a new position, and lengthen the snake.
	 */
	public void checkAppleCol(){
		for(Apple apple: apples){
			if(snek.head[0]==apple.coords[0] && snek.head[1]==apple.coords[1]){
			snek.length++;
			apple.newPosition(snek.gridMap, snek.length);
			if(Math.abs(radVel) > 0){
				if(Math.abs(radVel) < 1.2){
					radVel = -(Math.signum(radVel) * (Math.abs(radVel) + 0.03f));
				}
				else{
					radVel = -radVel;
				}
			}
			else{
				radVel = 0.1f;
			}
		}}}
	public void rotateCamera(){
		camera.rotate(radVel);
		radPos += radVel;
		keepCompassStill(radVel);
	}

	public void keepCompassStill(float vel){
		if(vel != 0){
			compassRect.x = (float) (Math.cos((-radPos)/180*Math.PI)*rad)+325;
			compassRect.y = (float) (Math.sin((-radPos)/180*Math.PI)*rad)+340;
	}}
}
