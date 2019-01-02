package gui;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MyMouseListen extends JFrame implements MouseListener {
  Board boardOut;
  JPanel kk;//棋盘体
  MyMouseListen(JPanel k,Board g){
    boardOut = g;
    this.addMouseListener(this);
    kk = k;
    k.addMouseListener(this);
  }

  @Override
  public void mouseClicked(MouseEvent e) {
//    System.out.println(e.getX());
//    System.out.println(e.getY());

  }

  @Override
  public void mousePressed(MouseEvent e) {
    int x =e.getX();
    int y =e.getY();
    if (boardOut.isBlack == true){
      boardOut.myTurn.setText("己方出棋");
      boardOut.opTurn.setText("");
    }else {
      boardOut.opTurn.setText("对方出棋");
      boardOut.myTurn.setText("");
    }
    if (x>=24&& x<=528+30 &&  y>=27 && y<=527+30){
      //System.out.println("在范围之内");
      if (boardOut.isBlack == true){
        if( boardOut.boardGrid[(int)(x-24)/36][(int)(y-27)/36] == 0){//[第i行][第j列]
          boardOut.boardGrid[(int)(x-24)/36][(int)(y-27)/36] = 1;//黑棋为1
          boardOut.blackCount ++;
          boardOut.isBlack = false;
          if (boardOut.blackCount >= 5){

          }
        }

      }else{
        if( boardOut.boardGrid[(int)(x-24)/36][(int)(y-27)/36] == 0) {
          boardOut.boardGrid[(int) (x - 24) / 36][(int) (y - 27) / 36] = 2;//白棋为2
//        boardOut.boardGrid[(int)Math.floor((x-24)/36)][(int)Math.floor((y-27)/36)] = 2;//白棋为2
          boardOut.whiteCount ++;
          boardOut.isBlack = true;
        }
      }
      kk.repaint();
    }

  }

  @Override
  public void mouseReleased(MouseEvent e) {

  }

  @Override
  public void mouseEntered(MouseEvent e) {

  }

  @Override
  public void mouseExited(MouseEvent e) {

  }
}
