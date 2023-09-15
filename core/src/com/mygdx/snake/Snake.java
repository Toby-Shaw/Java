package com.mygdx.snake;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import java.util.ArrayList;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;

/*
 * The overall source class for Snake here
 */
public class Snake extends ApplicationAdapter {
	//batch works for textures, shape for simple shapes and arcs
	SpriteBatch batch;
	ShapeRenderer shape;
	Snek snek;
	Apple apple;
	private OrthographicCamera camera;
	private long lastMoveTime;
	
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
		batch = new SpriteBatch();
		
		snek = new Snek(startCoords, screenDim, screenColor, gridIntegers, 50, coord);
		apple = new Apple(appColor,gridIntegers);
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
		batch.end();

		shape.setProjectionMatrix(camera.combined);
		ArrayList<int[]> rects = snek.rectPlacements();
		shape.begin(ShapeType.Line);
		shape.setColor(1, 0, 0, 1);
		for(int[] box:rects){
			shape.rect(box[0], box[1], 50, 50);}
		shape.end();
		shape.begin(ShapeType.Filled);
		shape.setColor(1, 1, 0, 1);
		for(int[] box:rects){
			shape.rect(box[0]+1, box[1]+1, 48, 48);}
		shape.setColor(1, 0, 0, 1);
		int[] appleRect = apple.rectPlacements();
		shape.rect(appleRect[0], appleRect[1], 50, 50);
 		shape.end();
		if(Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) snek.setDir(1, 0);
		if(Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) snek.setDir(-1, 0);
		if(Gdx.input.isKeyJustPressed(Input.Keys.UP)) snek.setDir(0, 1);
		if(Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) snek.setDir(0, -1);

		if(TimeUtils.nanoTime() - lastMoveTime > snek.timelag*2000000){
			lastMoveTime = TimeUtils.nanoTime();
			snek.move();
			checkAppleCol();
		}
		if(snek.dead){
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
		if(snek.head[0]==apple.coords[0] && snek.head[1]==apple.coords[1]){
			snek.length++;
			apple.newPosition(snek.coords);
		}
	}
}
