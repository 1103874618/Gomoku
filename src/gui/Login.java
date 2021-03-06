package gui;

import server.ClientInfo;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.*;
import java.net.Socket;

public class Login extends JFrame {
  JLabel userLabel = new JLabel("用 户 名");
  JLabel serverLabel = new JLabel("服 务 器");
  JLabel icon = new JLabel("头   像");
  JLabel userInfo = new JLabel("请输入您的个人信息");
  JLabel userImage = new JLabel(new ImageIcon("IconRes\\res\\face\\1-1.gif"), JLabel.LEFT);
  JTextField userName = new JTextField();
  JTextField serverIP = new JTextField();


  Socket s = null;//将socket共享出来
  DataOutputStream dos = null;
  DataInputStream dis = null;
  ObjectOutputStream obo = null;
  boolean beConnected = false;

  public Login() {
    super("登陆界面");
    JPanel pNorth = new JPanel();
    JPanel pSouth = new JPanel();

    //北区
    pNorth.setLayout(new BorderLayout());
    pNorth.add(userInfo, "North");
    //北区-东区
    JPanel p11 = new JPanel();
    p11.setLayout(new GridLayout(3, 1));
    p11.add(userLabel);
    p11.add(serverLabel);
    p11.add(icon);
    pNorth.add(p11, "West");
    //北-中
    JPanel p22 = new JPanel();
    p22.setLayout(new GridLayout(3, 1));
    p22.add(userName);
    p22.add(serverIP);
    p22.add(userImage);//
    pNorth.add(p22, "Center");

    //中区
    JPanel faceGroud = new JPanel();
    faceGroud.setLayout(null);
    faceGroud.setSize(300, 300);
    JButton buttons[] = new JButton[204];

    String[] img = new String[65];
//
    for (int i = 1; i <= 6; i++) {
      img[i - 1] = "IconRes\\res\\face\\" + i + "-" + 1 + ".gif";
    }
    for (int i = 10; i <= 68; i++) {
      img[i - 4] = "IconRes\\res\\face\\" + i + "-" + 1 + ".gif";
    }


    int j = 0;
    for (int i = 0; i < 65; i++) {
      JButton button = new JButton(new ImageIcon(img[i]));
      int k = i;
      button.setBounds(36 * (i % 10), (k / 10) * 36, 36, 36);//十个一行
      button.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          Object b = e.getSource();
          JButton jb = (JButton) b;
          Icon i = jb.getIcon();
          userImage.setIcon(i);
        }
      });
      faceGroud.add(button);
    }

    //南
    JButton loginB = new JButton("连 接");
    JButton resetB = new JButton("重 置");
    JButton exitB = new JButton("退 出");


    //按钮事件
    loginB.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        String[] userInfo = new String[2];
        Icon userIco;
        userInfo[0] = userName.getText();
        userInfo[1] = serverIP.getText();
        userIco = userImage.getIcon();
        ClientInfo clInfo = new ClientInfo(userInfo[0],userInfo[1],userIco);
        Hall h = new Hall(clInfo);
        dispose();
//        try {
//          obo.writeObject(clInfo);
//          obo.flush();
//        } catch (IOException e1) {
//          e1.printStackTrace();
//        }

        System.out.println("the user name is :" + userInfo[0]);
        System.out.println("the IP is :" + userInfo[1]);
      }
    });
    resetB.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        userName.setText("");
        serverIP.setText("");
        userImage.setIcon(new ImageIcon("IconRes\\res\\face\\1-1.gif"));
      }
    });

    exitB.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        dispose();
      }
    });


    pSouth.setLayout(new BoxLayout(pSouth, BoxLayout.X_AXIS));


    pSouth.add(Box.createHorizontalGlue());
    pSouth.add(loginB);
    pSouth.add(Box.createRigidArea(new Dimension(20, 50)));
    pSouth.add(resetB);
    pSouth.add(Box.createRigidArea(new Dimension(20, 50)));
    pSouth.add(exitB);
    pSouth.add(Box.createHorizontalGlue());


    Container con = this.getContentPane();
    con.setLayout(new BorderLayout());
    con.add(pNorth, "North");
    con.add(faceGroud, "Center");
    con.add(pSouth, "South");


    this.setVisible(true);
    this.setSize(400, 450);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    //connect();
  }

  public void connect() {

    try {
      s = new Socket("127.0.0.1", 8888);//这里就不要再定义s了,否则就成了局部变量
      dos = new DataOutputStream(s.getOutputStream());//初始化一个输出流
      dis = new DataInputStream(s.getInputStream());
      obo = new ObjectOutputStream(s.getOutputStream());
      beConnected = true;
      System.out.println("Connected!!");
    } catch (IOException e) {
      e.printStackTrace();
    }


  }

}

class Demo {
  public static void main(String[] args) {
    Login l = new Login();
  }
}
