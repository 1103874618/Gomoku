package work;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.Flow;

public class Hall extends JFrame {
  JTabbedPane top = new  JTabbedPane(JTabbedPane.TOP);

  JLabel topBarInside = new JLabel();//顶部便签容器
  JPanel topBar = new JPanel();
  JPanel body = new JPanel();

  JPanel info = new JPanel();//左侧信息栏
  JTabbedPane userInfo = new JTabbedPane();//
  JTabbedPane severInfo = new JTabbedPane();

  JPanel sit = new JPanel();//大厅主界面


  JPanel userInfoF = new JPanel();
  JPanel severInfoF = new JPanel();
  JSplitPane hor = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,info,sit);
  JSplitPane infoSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT,userInfoF,severInfoF);

  public Hall(){
    super("大厅界面");
    //topBar.setLayout(null);
    //top.addTab("游戏大厅",topBarInside);
    hor.setOrientation(SwingConstants.VERTICAL);
    hor.setDividerSize(6);

    sit.setLayout(new BorderLayout());//大厅主界面
    JPanel sitGroud = new JPanel();

    JPanel topButton = new JPanel();
    JLabel title = new JLabel("<<<  五子棋对战  >>>",JLabel.LEFT);
    JButton exit = new JButton("退 出");
    exit.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        dispose();
      }
    });
    JButton sitDown = new JButton("自 动 入 座");

//    exit.setPreferredSize(new Dimension(40,20));
////    sitDown.setPreferredSize(new Dimension(40,20));
////    exit.setSize(50,10);

    topButton.setPreferredSize(new Dimension(650,30));
    topButton.setLayout(new BorderLayout());
    topButton.add(title,"West");
    JPanel topButtonEast = new JPanel();//顶右部按钮
    topButtonEast.setLayout(new BoxLayout(topButtonEast,BoxLayout.X_AXIS));
    topButtonEast.setPreferredSize(new Dimension(250,10));
    //topButtonEast.setBackground(Color.red);
    topButtonEast.add(Box.createRigidArea(new Dimension(10, 0)));
    topButtonEast.add(sitDown);
    topButtonEast.add(Box.createRigidArea(new Dimension(10, 0)));
    topButtonEast.add(exit);
    topButtonEast.add(Box.createRigidArea(new Dimension(10, 0)));
    topButton.add(topButtonEast,"East");

    //用户信息栏
    JPanel userInfoP = new JPanel();
    JPanel severInfoP= new JPanel();
    userInfoF.add(userInfo);
    userInfoF.setLayout(new GridLayout(1,1));
    severInfoF.setLayout(new GridLayout(1,1));
    severInfoF.add(severInfo);
    userInfo.addTab("                用户信息",userInfoP);
    severInfo.addTab("               服务器信息",severInfoP);
    info.setPreferredSize(new Dimension(250,670));
    info.setBackground(Color.red);
    info.setLayout(new BoxLayout(info,BoxLayout.X_AXIS));
    info.add(infoSplit);
    infoSplit.setDividerSize(6);
    infoSplit.setDividerLocation(300);
    userInfoF.setPreferredSize(new Dimension(250,335));
    severInfoF.setPreferredSize(new Dimension(250,335));

    sitGroud.setBackground(Color.blue);
    sit.add(sitGroud,"Center");
    sit.add(topButton,"North");



    sit.setPreferredSize(new Dimension(650,670));
    top.addTab("游戏大厅",body);
    body.setSize(800,670);
    body.add(hor);
    top.setSize(800,670);


    setLayout(null);
    add(top);
    //add(hor);
    this.setSize(820,720);
    setVisible(true);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

  }

  private  static  JPanel makePanel(String text){
    JPanel p = new JPanel();
    p.add(new Label(text));
    p.setLayout(new GridLayout(1,1));
    return p;
  }


}

class HallDemo{
  public static void main (String[] args){
    Hall a= new Hall();
  }
}