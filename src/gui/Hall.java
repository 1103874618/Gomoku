package gui;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Hall extends JFrame {
  //传入的用户信息
  ImageIcon userHead;//头像
  JLabel userN;//名字


  JTabbedPane top = new JTabbedPane(JTabbedPane.TOP);

  JLabel topBarInside = new JLabel();//顶部便签容器
  JPanel topBar = new JPanel();
  JPanel body = new JPanel();

  JPanel info = new JPanel();//左侧信息栏
  JTabbedPane userInfo = new JTabbedPane();//
  JTabbedPane severInfo = new JTabbedPane();

  JPanel sit = new JPanel();//大厅主界面


  JPanel userInfoF = new JPanel();
  JPanel severInfoF = new JPanel();
  JSplitPane hor = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, info, sit);
  JSplitPane infoSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT, userInfoF, severInfoF);

  public Hall() {
    super("大厅界面");
    //topBar.setLayout(null);
    //top.addTab("游戏大厅",topBarInside);
    Board b = new Board();//棋盘预加载
    hor.setOrientation(SwingConstants.VERTICAL);
    hor.setDividerSize(6);

    sit.setLayout(new BorderLayout());//大厅主界面
    JPanel sitGroud = new JPanel();

    JPanel topButton = new JPanel();
    JLabel title = new JLabel("<<<  五子棋对战  >>>", JLabel.LEFT);
    JButton exit = new JButton("退 出");
    exit.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        dispose();
      }
    });
    JButton sitDown = new JButton("自 动 入 座");


    topButton.setPreferredSize(new Dimension(650, 30));
    topButton.setLayout(new BorderLayout());
    topButton.add(title, "West");
    JPanel topButtonEast = new JPanel();//顶右部按钮
    topButtonEast.setLayout(new BoxLayout(topButtonEast, BoxLayout.X_AXIS));
    topButtonEast.setPreferredSize(new Dimension(250, 10));
    //topButtonEast.setBackground(Color.red);
    topButtonEast.add(Box.createRigidArea(new Dimension(10, 0)));
    topButtonEast.add(sitDown);
    topButtonEast.add(Box.createRigidArea(new Dimension(10, 0)));
    topButtonEast.add(exit);
    topButtonEast.add(Box.createRigidArea(new Dimension(10, 0)));
    topButton.add(topButtonEast, "East");

    //用户信息栏
    JPanel userInfoP = new JPanel();
    JPanel severInfoP = new JPanel();
    userInfoF.add(userInfo);
    userInfoF.setLayout(new GridLayout(1, 1));
    severInfoF.setLayout(new GridLayout(1, 1));
    severInfoF.add(severInfo);
    userInfo.addTab("                用户信息", userInfoP);
    severInfo.addTab("               服务器信息", severInfoP);
    info.setPreferredSize(new Dimension(250, 670));
    info.setBackground(Color.red);
    info.setLayout(new BoxLayout(info, BoxLayout.X_AXIS));
    info.add(infoSplit);
    infoSplit.setDividerSize(6);
    infoSplit.setDividerLocation(300);
    userInfoF.setPreferredSize(new Dimension(250, 335));
    severInfoF.setPreferredSize(new Dimension(250, 335));
    JLabel userImage = new JLabel(new ImageIcon("IconRes\\res\\img\\boy1.gif"));//用户头像
    JLabel userNmae = new JLabel("无 名 氏");//用户名字
//    severInfoP.setBackground(Color.yellow);
//    userInfoP.setBackground(Color.yellow);
    SpringLayout p = new SpringLayout();//用户信息面板布局
    userInfoP.setLayout(p);
    p.putConstraint(SpringLayout.WEST, userImage, 75, SpringLayout.WEST, userInfoP);//定位用户图片
    p.putConstraint(SpringLayout.NORTH, userImage, 15, SpringLayout.NORTH, userInfoP);
    p.putConstraint(SpringLayout.WEST, userNmae, 124, SpringLayout.WEST, userInfoP);//定位名字
    p.putConstraint(SpringLayout.NORTH, userNmae, 10, SpringLayout.SOUTH, userImage);
    userInfoP.add(userImage);
    userInfoP.add(userNmae);


    //座位区
    JLabel[] roomNum = new JLabel[15];//房间号
    JPanel[] sitSingle = new JPanel[15];
    JLabel[] userNL = new JLabel[15];
    JLabel[] sitTable = new JLabel[15];
    JButton[] sitL = new JButton[15];
    JButton[] sitR = new JButton[15];
    sitGroud.setLayout(new GridLayout(5, 3));
    //sitGroud.setLocale();
    //userNL = userN;
    for (int i = 0; i < 15; i++) {
      roomNum[i] = new JLabel("- "+(i+1)+" -");
      sitSingle[i] = new JPanel();
      userNL[i] = new JLabel();
      sitSingle[i].setLayout(null);
      sitSingle[i].setPreferredSize(new Dimension(135, 75));
      userNL[i].setBounds(20, 70, 60, 20);
      sitSingle[i].setBackground(Color.decode("#51719E"));
      //sitSingle[i].setBackground(Color.red);
      sitTable[i] = new JLabel(new ImageIcon("IconRes/res/img/xqnoone.gif"));
      sitTable[i].setBounds(60, 20, 53, 53);
      roomNum[i].setBounds(80,66,40,30);
      roomNum[i].setForeground(Color.white);
      sitL[i] = new JButton(new ImageIcon("IconRes/res/img/noone.gif"));
      sitL[i].setBounds(20, 25, 40, 45);
      sitL[i].addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          JButton i = (JButton) e.getSource();
          for(int k = 0;k<15;k++){
            sitL[k].setIcon(new ImageIcon("IconRes/res/img/noone.gif"));
            sitR[k].setIcon(new ImageIcon("IconRes/res/img/noone.gif"));
          }
          i.setIcon(userHead);
          //Board b = new Board();
          if (top.getTabCount() == 2){
            top.removeTabAt(1);
          }

          top.addTab("五子棋游戏",b.boardAll);
        }
      });
      sitR[i] = new JButton(new ImageIcon("IconRes/res/img/noone.gif"));
      sitR[i].setBounds(113, 25, 40, 45);
      sitR[i].addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          JButton i = (JButton) e.getSource();
          for(int k = 0;k<15;k++){
            sitL[k].setIcon(new ImageIcon("IconRes/res/img/noone.gif"));
            sitR[k].setIcon(new ImageIcon("IconRes/res/img/noone.gif"));
          }
          i.setIcon(userHead);

          if (top.getTabCount() == 2){
            top.removeTabAt(1);
          }
          top.addTab("五子棋游戏",b.boardAll);
        }
      });
      sitSingle[i].add(sitL[i]);
      sitSingle[i].add(sitTable[i]);
      sitSingle[i].add(sitR[i]);
      sitSingle[i].add(userNL[i]);
      sitGroud.add(sitSingle[i]);
      sitSingle[i].add(roomNum[i]);
    }


    sitGroud.setBackground(Color.decode("#51719E"));
    sit.add(sitGroud, "Center");
    sit.add(topButton, "North");


    sit.setPreferredSize(new Dimension(650, 670));
    top.addTab("游戏大厅", body);
    body.setSize(800, 670);
    body.add(hor);
    top.setSize(800, 670);


    setLayout(null);
    add(top);
    //add(hor);
    this.setSize(820, 720);
    setVisible(true);
    this.addWindowFocusListener(new WindowAdapter() {//窗口关闭时断开连接
      @Override
      public void windowClosing(WindowEvent e) {
        b.disconnect();
        System.out.println("disconnect");
        System.exit(0);
      }
    });
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);



  }

  private static JPanel makePanel(String text) {//画出棋盘
    JPanel p = new JPanel();
    p.add(new Label(text));
    p.setLayout(new GridLayout(1, 1));
    return p;
  }

}

class HallDemo {
  public static void main(String[] args) {
    Hall a = new Hall();
  }
}