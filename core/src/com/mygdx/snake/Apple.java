package com.mygdx.snake;
import java.util.ArrayList;

import com.badlogic.gdx.math.MathUtils;
/*
 * A simple class to represent the Apple that the Snake eats to grow longer
 */
public class Apple {

    public int[] coords = new int[2];
    public int[] color;
    public int[] gridDimen;
    /*
     * Constructor for the apple, input color and the size of the grid, the position will be random
     * excluding any spaces the snake is on
     */
    public Apple(int[] appleColor, int[] dimen){
        color = appleColor;
        gridDimen = dimen;
        ArrayList<int[]> temp = new ArrayList<int[]>();
        int[] start = {0, 0};
        temp.add(start);
        newPosition(temp);
    }

    /*
     * Create a gridmap of every "square" and eliminate it from being chosen if the snake is
     * present there. Then, choose a random option
     */
    public void newPosition(ArrayList<int[]> snakeCoords){
        int[][] options = new int[gridDimen[0]][gridDimen[1]];
        for(int i=0; i < gridDimen[0]; i++){
            for(int j=0; j < gridDimen[1]; j++){
                options[i][j] = 1;
        }
        }
        for(int k = 0; k < snakeCoords.size(); k++){
            options[snakeCoords.get(k)[0]][snakeCoords.get(k)[1]] = 0;
        }
        randomSelec(options);}

    /*
     * Set new coordinates to a new random position based on the options given
     */
    public void randomSelec(int[][] options){
        //Get a random 1d index
        int dest = MathUtils.random(0, gridDimen[0]*gridDimen[1]);
        //Convert the index to 2d to match the options
        int x = dest % gridDimen[1];
        int y = Math.floorDiv(dest, gridDimen[1]);
        //If it is open, congrats
        if(options[x][y] == 1){
            coords[0] = x;
            coords[1] = y;
        }
        else{
            //Recursion until a correct spot is received
            randomSelec(options);
        }
        }

    /*
     * Return the pixel x and y coordinates of the apple rectangle based on its grid coordinates
     */
    public int[] rectPlacements(){
        int fx = 50*coords[0];
        int fy = 50*coords[1];
        int[] pair = {fx, fy};
        return pair;
    }
    }
