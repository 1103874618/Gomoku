package gui;


import java.awt.event.ActionListener ;
import java.awt.event.WindowListener ;
import java.awt.event.WindowAdapter ;
import java.awt.event.WindowEvent ;
import java.awt.event.KeyEvent ;
import java.awt.event.MouseEvent ;
import java.awt.event.KeyAdapter ;
import java.awt.event.KeyListener ;
import java.awt.event.MouseListener ;
import javax.swing.JTextArea ;
import javax.swing.JFrame ;
import javax.swing.JScrollPane ;
import javax.swing.JPasswordField ;


class MyMouse extends JFrame implements MouseListener
{
  JTextArea text = new JTextArea() ;
  public MyMouse()
  {
    super.setTitle("鼠标精灵") ;
    JScrollPane scr = new JScrollPane(text) ;
    scr.setBounds(5,5,300,200) ;
    super.add(scr) ;
    text.addMouseListener(this) ;
    super.addWindowListener(new WindowAdapter()
    {
      public void WindowClosing(WindowEvent e)
      {
        System.exit(1) ;
      }
    }) ;

    super.setSize(400,300) ;
    super.setVisible(true) ;
    super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }

  public void mouseClicked(MouseEvent e)    //得到鼠标键值
  {
    int c = e.getButton() ;
    String mouseinfo = null;
    if(c==MouseEvent.BUTTON1)
    {
      mouseinfo = "左键\n" ;
    }
    if(c==MouseEvent.BUTTON2)
    {
      mouseinfo = "滚轮\n" ;
    }
    if(c==MouseEvent.BUTTON3)
    {
      mouseinfo = "右键\n" ;
    }
    text.append("被按下的是"+mouseinfo) ;
  }

  public void mouseEntered(MouseEvent e)   //鼠标进入组件
  {
    text.append("鼠标进入组件\n") ;
  }

  public void mouseExited(MouseEvent e)     //鼠标离开组件
  {
    text.append("鼠标离开组件\n") ;
  }

  public void mousePressed(MouseEvent e)   //鼠标被按下
  {
    text.append("鼠标被按下\n") ;
  }

  public void mouseReleased(MouseEvent e)   //鼠标被放开
  {
    text.append("鼠标被松开\n") ;
  }


} ;

class Tester
{
  public static void main(String args[])
  {
    MyMouse mhk = new MyMouse() ;
  }
} ;
