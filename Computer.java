package FiveChess;
import javax.swing.*;
import java.awt.*;
import java.util.*;
public class Computer {
    final int G5 = 20000;
    final int LIVE4 = 2000;
    final int C4 = 300; /* 冲四 */
    final int RC4 = 250; /* 跳冲四 */
    final int LIVE3 = 450;
    final int RLIVE3 = 300;
    final int LIVE2 = 100;
    final int RLIVE2 = 70;
    final int M3 = 50;
    final int DEAD4 = -10;
    final int DEAD3 = -10;
    final int DEAD2 = -10;
    final int inf = 9000000;
    final int unknow = 9900000;
    int DEPTH;
    int counter;
    int find;
    int[][] map=ChessBoard.map;
    int[][] dir=ChessBoard.dir;
    int palyer=ChessBoard.isBlack?1:2;
    public ArrayList<Point> ValueList;
    public Computer(){
        ValueList=new ArrayList<>();

    }
    Point getPoint(){
        calAllChess();
        int mx=0;

        Point ans=ValueList.get(0);
        for(Point p:ValueList){
            if(mx<p.value){
                mx=p.value;
                ans=p;
            }

        }
        //System.out.printf("电脑下棋中...\n");
        return ans;
    }
    void calAllChess(){
        for(int i=0;i<ChessBoard.ROW;i++){
            for(int j=0;j<ChessBoard.COL;j++){
                if(map[i][j]==0){
                    Point po=new Point(i,j,palyer==1? Color.BLACK:Color.WHITE);
                    po.value=0;

                    po.value+=CalValue(palyer,po,i,j);

                    //System.out.printf("x=%d,y=%d,攻击力：%d\n",i,j,po.value);
                    po.value+=CalValue(palyer==1?2:1,po,i,j);
                    //System.out.printf("x=%d,y=%d,防御力：%d\n",i,j,po.value);
                    ValueList.add(po);
                }
            }
        }

    }
    void CalChess(int play,Point po,int d1,int d2){
        int x=po.getX();
        int y=po.getY();
        int i,j,mid=0;
        int[] lchess=new int[]{0,0},rchess=new int[]{0,0};
        int[] lempty=new int[]{0,0},rempty=new int[]{0,0};

        i=x-d1;j=y-d2;
        while(ChessBoard.check(i,j)&&map[i][j]==play){ lchess[0]++; i -= d1; j -= d2;}
        while(ChessBoard.check(i,j)&&map[i][j]==0){ lempty[0]++; i -= d1; j -= d2; }
        while(ChessBoard.check(i,j)&&map[i][j]==play){ lchess[1]++; i -= d1; j -= d2; }
        while(ChessBoard.check(i,j)&&map[i][j]==0){ lempty[1]++; i -= d1; j -= d2; }

        i=x+d1;j=y+d2;
        while(ChessBoard.check(i,j)&&map[i][j]==play) { rchess[0]++; i += d1; j += d2; }
        while(ChessBoard.check(i,j)&&map[i][j] == 0) { rempty[0]++; i += d1; j += d2; }
        while(ChessBoard.check(i,j)&&map[i][j]==play) { rchess[1]++; i += d1; j += d2; }
        while(ChessBoard.check(i,j)&&map[i][j] == 0) { rempty[1]++; i += d1; j += d2; }


        mid=lchess[0]+rchess[0]+1;
        if(mid>=5)po.g5++;
        else if(mid==4){
            if(lempty[0]>=1&&rempty[0]>=1)po.live4++;
            else if(lempty[0]>=1||rempty[0]>=1)po.c4++;
            else po.dead4++;
        }
        else if(mid==3){
            boolean flag=false;
            if((lempty[0]==1&&lchess[0]>=1)||(rempty[0]==1&&rchess[0]>=1)){
                po.rc4++;
                flag=true;
            }
            else if(!flag&&lempty[0]+rempty[0]>=3&&lempty[0]>=1&&rempty[0]>=1){
                po.live3++;
            }
            else if(lempty[0]>=1||rempty[0]>=1){
                po.m3++;
            }
            else po.dead3++;
        }
        else if(mid==2){
            boolean flag=false;
            if(lempty[0]==1&&lchess[1]>=2||rempty[0]==1&&rchess[1]>=2){
                po.rc4++;
                flag=true;
            }
            else if(!flag&&(lempty[0]==1&&lchess[1]==1&&rempty[0]>=1&&lempty[1]>=1)
                    ||(rempty[0]==1&&rchess[1]==1&&lempty[0]>=1&&rempty[1]>=1)){
                po.rlive3++;
            }
            else if((lempty[0] == 1 && lchess[1] == 1 && rempty[0] + lempty[1] >= 1)
                    || (rempty[0] == 1 && rchess[1] == 1 && lempty[0] + rempty[1] >= 1)){
                po.m3++;
            }
            if(lempty[0] + rempty[0] >= 4 && lempty[0] >= 1 && rempty[0] >= 1)
                po.live2++;
            else if(lempty[0] + rempty[0] == 0)
                po.dead2++;
        }
        else if(mid == 1){
            boolean flag = false;
            if((lempty[0] == 1 && lchess[1] >= 3) || (rempty[0] == 1 && rchess[1] >= 3)){
                po.rc4++;
                flag = true;
            }
            if(!flag && ((lempty[0] == 1 && lchess[1] == 2 && rempty[0] >= 1 && lempty[1] >= 1)
                    || (rempty[0] == 1 && rchess[1] == 2 && lempty[0] >= 1 && rempty[1] >= 1))){
                po.rlive3++;
            }
            else if((lempty[0] == 1 && lchess[1] == 2 && rempty[0] + lempty[1] >= 1)
                    || (rempty[0] == 1 && rchess[1] == 2 && lempty[0] + rempty[1] >= 1))
                po.m3++;
            if((lempty[0] == 1 && lchess[1] == 1 && rempty[0] + lempty[1] >= 3 && rempty[0] >= 1 && lempty[1] >= 1)
                    || (rempty[0] == 1 && rchess[1] == 1 && rempty[1] + lempty[0] >= 3 && lempty[0] >= 1 && rempty[1] >= 1)){
                po.rlive2++;
            }
            if((lempty[0] == 2 && lchess[1] == 1 && rempty[0] >= 1 && lempty[1] >= 1)
                    || (rempty[0] == 2 && rchess[1] == 1 && lempty[0] >= 1 && rempty[1] >= 1)){
                po.rlive2++;
            }
        }
    }
    int CalValue(int play,Point po,int x,int y){
        int value=0;
        po.g5=po.live4=po.c4=po.rc4=po.live3=po.rlive3=po.live2=po.rlive2=po.m3=po.dead4=po.dead3=po.dead2=po.kill=0;
        for(int i=0;i<4;i++) {
            CalChess(play, po, dir[i][0], dir[i][1]);
        }
        int nc4,nl3;
        nc4=po.c4+po.rc4;
        nl3=po.live3+po.rlive3;
        if(po.g5>=1){
            po.kill=3;
            value=G5;
        }
        else if(po.live4>=1||nc4>=2||(nc4==1&&nl3>=1)){
            po.kill=2;
            value=LIVE4;
        }
        else{
            if(nl3>=2)po.kill=1;
            value=po.live3*LIVE3 + po.rlive3*RLIVE3 + po.live2*LIVE2 + po.rlive2*RLIVE2 + po.c4*C4
                    + po.rc4*RC4 + po.m3*M3 + po.dead4*DEAD4 + po.dead3*DEAD3 + po.dead2*DEAD2;
        }
        return value;
    }
}
