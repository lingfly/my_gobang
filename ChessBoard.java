package FiveChess;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.*;
import java.awt.event.*;
import java.awt.Graphics2D.*;
import java.awt.RadialGradientPaint.*;

public class ChessBoard extends JPanel implements MouseListener{
    public final static int MARGIN=35;
    public final static int interval=40;
    public final static int ROW=15;
    public final static int COL=15;
    public static int gameover=0;
    public static boolean isBlack=true;
    private static ArrayList<Point> chessList;
    public static int[][] map;//1为黑，2为白，0为无子
    public static int[][] dir={{0,1},{1,1},{1,0},{1,-1}};
    public ChessBoard(){
        setBackground(new Color(241,215,122));
        chessList=new ArrayList<>();
        map=new int[ROW][COL];

        addMouseListener(this);
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        for(int i=0;i<ROW;i++){//列
            g.drawLine(MARGIN+interval*i,MARGIN,MARGIN+interval*i,MARGIN+interval*(COL-1));
        }
        for(int i=0;i<COL;i++){//行
            g.drawLine(MARGIN,MARGIN+interval*i,MARGIN+interval*(ROW-1),MARGIN+interval*i);
        }
        int i=0;
        int count=chessList.size();
        for(Point p:chessList){
            int x=p.getX()*interval+MARGIN;
            int y=p.getY()*interval+MARGIN;
            RadialGradientPaint paint;
            if(p.getColor()==Color.BLACK){
                paint =new RadialGradientPaint(x+Point.radius/2, y-Point.radius/2, 18
                    , new float[]{0f, 1f}
                    , new Color[]{Color.WHITE, Color.BLACK});
            }
            else{
                paint =new RadialGradientPaint(x+Point.radius/2, y-Point.radius/2, 70
                        , new float[]{0f, 1f}
                        , new Color[]{Color.WHITE, Color.BLACK});
            }

            Graphics2D g2=(Graphics2D)g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setPaint(paint);
            Ellipse2D circle=new Ellipse2D.Double(x-Point.radius,y-Point.radius,Point.radius*2,Point.radius*2);
            g2.fill(circle);
            i++;
            if(i==count){

                g.setColor(Color.RED);

                g.drawRect(x-Point.radius,y-Point.radius,Point.radius*2,Point.radius*2);
            }
        }

    }
    public void mousePressed(MouseEvent e){
        int player=isBlack?1:2;
        int xIndex=(e.getX()-MARGIN+interval/2)/interval;
        int yIndex=(e.getY()-MARGIN+interval/2)/interval;
        if(!check(xIndex,yIndex)||map[xIndex][yIndex]!=0||gameover==1)return;

        Point ch=new Point(xIndex,yIndex,isBlack?Color.BLACK:Color.WHITE);
        chessList.add(ch);
        if(isBlack)map[xIndex][yIndex]=1;
        else map[xIndex][yIndex]=2;
        if(isWin(player,xIndex,yIndex)){
            if(isBlack)
                System.out.printf("黑方胜\n");
            else System.out.printf("白方胜\n");
            gameover=1;
        }

        isBlack=!isBlack;
        repaint();
        if(gameover==1)return ;
        Computer com=new Computer();
        player=isBlack?1:2;
        ch=com.getPoint();
        xIndex=ch.getX();yIndex=ch.getY();
        if(isBlack)map[xIndex][yIndex]=1;
        else map[xIndex][yIndex]=2;
        chessList.add(ch);
        if(isWin(player,xIndex,yIndex)){
            if(isBlack)
                System.out.printf("黑方胜\n");
            else System.out.printf("白方胜\n");
            gameover=1;
        }

        isBlack=!isBlack;
        repaint();

    }
    public void mouseExited(MouseEvent e){

    }
    public void mouseReleased(MouseEvent e){

    }
    public void mouseEntered(MouseEvent e){

    }
    public void mouseClicked(MouseEvent e){

    }
    public static boolean check(int x,int y){
        if(x<0||x>=ROW||y<0||y>=COL)
            return false;
        return true;
    }
    boolean isWin(int play,int x,int y){
        int[] link={1,1,1,1};
        for(int i=0;i<4;i++){
            int xx=x+dir[i][0];
            int yy=y+dir[i][1];
            while(check(xx,yy)&&map[xx][yy]==play){
                link[i]++;
                xx=xx+dir[i][0];
                yy=yy+dir[i][1];
            }
            xx=x-dir[i][0];
            yy=y-dir[i][1];
            while(check(xx,yy)&&map[xx][yy]==play){
                link[i]++;
                xx=xx-dir[i][0];
                yy=yy-dir[i][1];
            }
        }
        for(int i=0;i<4;i++){
            if(link[i]>=5)return true;
        }
        return false;
    }
    public void restartGame(){
        gameover=0;
        chessList.clear();
        Arrays.fill(map,0);
        isBlack=true;
        repaint();
    }
    public void regretGame(){
        int last=chessList.size()-1;
        Point p=chessList.get(last);
        map[p.getX()][p.getY()]=0;
        chessList.remove(last);

        last--;
        p=chessList.get(last);
        map[p.getX()][p.getY()]=0;
        chessList.remove(last);
        repaint();

        gameover = 0;
        //isBlack=true;

    }
}
