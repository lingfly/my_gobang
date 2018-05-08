package FiveChess;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.*;
public class FiveChess {
    public static void main(String[] args){
        EventQueue.invokeLater(()->{
            ChessFrame frame=new ChessFrame();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);

        });
    }

}
class ChessFrame extends JFrame{
    private JButton restart;
    private JButton regret;
    private JPanel toolbar;
    ChessBoard board;
    ButtonListener listen;
    public ChessFrame(){
        setSize(660,700);
        setResizable(false);
        board=new ChessBoard();
        restart=new JButton("重新开始");
        regret=new JButton("悔棋");
        toolbar=new JPanel();
        toolbar.setLayout(new FlowLayout(FlowLayout.LEFT));

        toolbar.add(restart);
        toolbar.add(regret);
        add(toolbar,BorderLayout.SOUTH);
        add(board,BorderLayout.CENTER);

        listen=new ButtonListener();
        restart.addActionListener(listen);
        regret.addActionListener(listen);
    }
    private class ButtonListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            Object obj =e.getSource();
            //System.out.println(obj);
            if(obj.equals(restart)){
                System.out.println(obj);
                board.restartGame();
            }
            else{
                board.regretGame();
            }
        }

    }
}