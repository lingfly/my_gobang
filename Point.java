package FiveChess;

import java.awt.Color;

public class Point {

    int g5,live4,c4,rc4,live3,rlive3,live2,rlive2,m3,dead4,dead3,dead2;
    int value,kill;
    private int x,y;
    final public static int radius=18;
    private Color color;
    public Point(int x,int y,Color color){
        this.x=x;
        this.y=y;
        this.color=color;
    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    public Color getColor(){
        return color;
    }
}
