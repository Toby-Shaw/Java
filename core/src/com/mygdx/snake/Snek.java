package com.mygdx.snake;
import java.util.ArrayList;


/*
 * The class that represents the actual snake, with its squares and amazing eating
 */
public class Snek {
    public int[] head;
    private int[] dimen;
    public int[] color;
    private int[] gridDim;
    public ArrayList<int[]> coords;
    private int movex;
    private int movey;
    //1 of two factors determining the speed of the snake
    public int timelag;
    public int length;
    private int perx;
    private int pery;
    public boolean dead;
    private int[] lastMovement = new int[2];

    /*
     * Constructor for the snake, setting up defaults for coordinates/movement and receiving info
     * on the grid size, snake speed, and color
     */
    public Snek(int[] coordinates, int[] screenDimensions, int[] snakeColor, int[] gridIntegers,
        int speedOffset, ArrayList<int[]> coord){
        head = coordinates;
        dimen = screenDimensions;
        color = snakeColor;
        gridDim = gridIntegers;
        timelag = speedOffset;
        movex = 0;
        movey = 0;
        length = 4;
        dead = false;
        coords = coord;
        coords.add(0, head);
        lastMovement[0] = 99;
        lastMovement[1] = 99;
        perx = dimen[0] / gridDim[0];
        pery = dimen[1] / gridDim[1];
    }

    /*
     * Move the snake based on the movex and movey variables, and then check collisions with the side of the screen.
     * After that, check if the snake has run into itself, update lastMovement, and then
     * finish up correcting the list/order of snake coordinate squares
     */
    public void move(){
        int f = head[0];
        int j = head[1];
        int[] copy = {f, j};
        coords.add(1, copy);
        head[0] = head[0] + movex;
        head[1] = head[1] + movey;
        if(head[0]>=gridDim[0] || head[1]>=gridDim[1] || head[0]<0 || head[1]<0 ){
            dead = true;
        }
        if(movex!=0 || movey!=0){
            for(int i = 1; i<coords.size(); i++){
                if(coords.get(i)[0] == coords.get(0)[0] && coords.get(i)[1]==coords.get(0)[1]){
                    dead = true;
                    break;
                }
        }
            if(!dead){
                lastMovement[0] = movex;
                lastMovement[1] = movey;
        }}
        if(coords.size()>length){
            coords.remove(coords.size()-1);
        }
    }

    /*
     * Based on the input, check if it is a legal speed input, and act accordingly
     */
    public void setDir(int x, int y){
        if(coords.size()>1){
            if((x != -lastMovement[0]) && (y != -lastMovement[1])){
                movex = x;
                movey = y;
            }
        }
        else{
            movex = x;
            movey = y;
        }
    }

    /*
     * Return an ArrayList of all the coordinate positions of each snake square, based on
     * their coordinates in the grid
     */
    public ArrayList<int[]> rectPlacements(){
        ArrayList<int[]> output = new ArrayList<int[]>();
        for(int i=0; i<coords.size(); i++){
            int fx = perx*coords.get(i)[0];
            int fy = pery*coords.get(i)[1];
            int[] pair = {fx, fy};
            output.add(pair);
        }
        return output;
    }
    }

