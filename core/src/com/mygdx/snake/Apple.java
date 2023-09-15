package com.mygdx.snake;

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
    public Apple(int[] appleColor, int[] dimen, int[][] gridMap){
        color = appleColor;
        gridDimen = dimen;
        newPosition(gridMap, 4);
    }

    /*
     * Create a gridmap of every "square" and eliminate it from being chosen if the snake is
     * present there. Then, choose a random option
     */
    public void newPosition(int[][] gridMap, int snakeLength){
        randomSelec(gridMap, snakeLength);}

    /*
     * Set new coordinates to a new random position based on the options given
     */
    public void randomSelec(int[][] options, int snakeLength){
        //Get a random 1d index, not counting snake squares
        int dest = MathUtils.random(0, gridDimen[0]*gridDimen[1]-snakeLength-1);
        int sum = 0;
        int[] output = {0, 0};
        for(int i = 0; i<gridDimen[0]; i++){
            for(int j = 0; j<gridDimen[1]; j++){
                //If a square is a snake square, it does not count towards the sum
                //Therefore, the dest number only counts towards open squares
                sum = sum + options[i][j];
                if(sum==dest){
                    output[0] = i;
                    output[1] = j;
                    //Break only breaks the inner loop, so adding to the sum insures that there
                    //won't be an incorrect logic overwrite due to a snake square starting
                    //a new column.
                    sum++;
                    break;
                }
            }
            }
        coords[0] = output[0];
        coords[1] = output[1];
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
