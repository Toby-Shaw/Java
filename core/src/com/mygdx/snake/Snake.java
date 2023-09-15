package com.mygdx.snake;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import java.util.ArrayList;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;

public class Snake extends ApplicationAdapter {
	SpriteBatch batch;
	ShapeRenderer shape;
	Snek snek;
	private OrthographicCamera camera;
	private long lastDropTime;
	
	@Override
	public void create () {
		int[] startCoords = {0, 0};
		int[] screenDim = {800, 800};
		int[] screenColor = {0, 0, 0};
		int[] gridIntegers = {16, 16};
		ArrayList<int[]> coord = new ArrayList<int[]>();
		shape = new ShapeRenderer();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, screenDim[0],screenDim[1]);
		batch = new SpriteBatch();
		
		snek = new Snek(startCoords, screenDim, screenColor, gridIntegers, 50, coord);
	}

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
 		shape.end();
		//if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) bucket.x -= 400*Gdx.graphics.getDeltaTime();
		if(Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) snek.setDir(1, 0);
		if(Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) snek.setDir(-1, 0);
		if(Gdx.input.isKeyJustPressed(Input.Keys.UP)) snek.setDir(0, 1);
		if(Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) snek.setDir(0, -1);

		if(TimeUtils.nanoTime() - lastDropTime > snek.timelag*2000000){
			lastDropTime = TimeUtils.nanoTime();
			snek.move();
		}
		


	}
	
	@Override
	public void dispose () {
		//batch.dispose();
	}
}
