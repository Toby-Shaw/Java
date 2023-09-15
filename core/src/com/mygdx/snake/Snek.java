package com.mygdx.snake;
import java.util.ArrayList;

import com.badlogic.gdx.math.Rectangle;

public class Snek {
    public Rectangle rect;
    private int[] head;
    private int[] dimen;
    public int[] color;
    private int[] gridDim;
    public ArrayList<int[]> coords;
    private int movex;
    private int movey;
    public int timelag;
    private int length;
    private int perx;
    private int pery;

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
        coords = coord;
        coords.add(0, head);
        perx = dimen[0] / gridDim[0];
        pery = dimen[1] / gridDim[1];
    }

    public void move(){
        int f = head[0];
        int j = head[1];
        int[] copy = {f, j};
        coords.add(1, copy);
        head[0] = head[0] + movex;
        head[1] = head[1] + movey;
        if(coords.size()>length){
            coords.remove(coords.size()-1);
        }
    }

    public void setDir(int x, int y){
        if(coords.size()>1){
            if(movex != -x){
                movex = x;
            }
            if(movey != -y){
            movey = y; 
            }
        }
        else{
            movex = x;
            movey = y;
        }
    }

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

