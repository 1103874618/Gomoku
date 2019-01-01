package gui;

import jdk.jfr.StackTrace;
import server.ClientInfo;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Observer;

public class Board extends JFrame implements MouseListener{//这里不要继承hall,否则每次调用board都会重新触发hall的构造函数
  //棋盘界面
  Socket s = null;//将socket共享出来
  DataOutputStream dos = null;
  DataInputStream dis = null;
  JPanel boardAll = new JPanel();
  MyPanel mp;
  JTextField msg = new JTextField(5);
  JTextArea display = new JTextArea(3, 4);
  private boolean beConnected = false;

  @Override
  public void mouseClicked(MouseEvent e) {
    System.out.println(e.getX());
    System.out.println(e.getY());
  }

  @Override
  public void mousePressed(MouseEvent e) {
    System.out.println(e.getX());
    System.out.println(e.getY());
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

  Board(ClientInfo in) {
    boardAll.setLayout(null);
    JPanel checkBoard = new JPanel();
    JLabel topTip = new JLabel("<<<   五子棋游戏房间   >>>");
    //以下均为分割线所用标签
    JPanel playInfo = new JPanel();
    JPanel myInfoAndChat = new JPanel();
    JPanel myInfo = new JPanel();
    JPanel opponentInfo = new JPanel();
    JPanel chat = new JPanel();


    JPanel boardButton = new JPanel();
//    JPanel boardButtonIn = new JPanel();
    JButton exit = new JButton("退 出");
    JButton begin = new JButton("开 始");
    JButton peace = new JButton("求 和");
    JButton loose = new JButton("认 输");
    boardButton.setBackground(Color.decode("#51719E"));
    boardButton.setLayout(null);
    exit.setBounds(100, 6, 80, 30);
    begin.setBounds(190, 6, 80, 30);
    peace.setBounds(280, 6, 80, 30);
    loose.setBounds(370, 6, 80, 30);
    exit.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        dispose();
      }
    });
    boardButton.add(exit);
    boardButton.add(begin);
    boardButton.add(peace);
    boardButton.add(loose);
    boardButton.setPreferredSize(new Dimension(500, 40));

    checkBoard.setSize(650, 670);
    checkBoard.setLayout(new BorderLayout());
    checkBoard.add(topTip, "North");
    checkBoard.add(boardButton, "South");
    //checkBoard.setBackground(Color.pink);

    //信息栏
    playInfo.setLayout(null);
    JSplitPane boardToInfo = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, playInfo, checkBoard);
    JSplitPane myToOp = new JSplitPane(JSplitPane.VERTICAL_SPLIT, opponentInfo, myInfoAndChat);
    JSplitPane myToChat = new JSplitPane(JSplitPane.VERTICAL_SPLIT, myInfo, chat);

    myInfoAndChat.setLayout(null);
    boardToInfo.setSize(800, 640);
    boardToInfo.setDividerLocation(200);
    myToOp.setDividerLocation(200);
    myToChat.setDividerLocation(200);
    myToOp.setSize(200, 760);
    myToChat.setSize(200, 420);

    playInfo.add(myToOp);
    myInfoAndChat.add(myToChat);
    boardAll.add(boardToInfo);

    JTabbedPane op = new JTabbedPane();
    JPanel opp = new JPanel();
    opp.setLayout(null);
    JLabel opHead = new JLabel(new ImageIcon("IconRes/res/img/noone.gif"));
    JLabel opName = new JLabel("无名氏");
    opHead.setBounds(60, 10, 36, 36);
    opName.setBounds(100, 15, 80, 30);
    opp.add(opHead);
    opp.add(opName);
    op.addTab("对手", opp);
    opponentInfo.setLayout(null);
    op.setBounds(0, 0, 200, 200);
    opponentInfo.add(op);

    JTabbedPane my = new JTabbedPane();
    JPanel myP = new JPanel();
    myP.setLayout(null);
//    JLabel myHead = new JLabel(new ImageIcon("IconRes/res/img/noone.gif"));
    JLabel myHead = new JLabel(in.getIcon());
    JLabel myName = new JLabel(in.getName());
    myHead.setBounds(60, 10, 36, 36);
    myName.setBounds(100, 15, 80, 30);
    myP.add(myHead);
    myP.add(myName);
    my.addTab("自己", myP);
    myInfo.setLayout(null);
    my.setBounds(0, 0, 200, 200);
    myInfo.add(my);

    //聊天框
    chat.setLayout(new BorderLayout());
    //chat.setBackground(Color.yellow);

    JButton send = new JButton("发送");
    JPanel msgAndSend = new JPanel();

    send.addActionListener(new TFListen(in.getName()));//按钮发送信息
    msg.setBounds(0, 0, 120, 30);
    send.setBounds(120, 0, 80, 30);
    chat.add(display, BorderLayout.NORTH);
    msgAndSend.setLayout(null);
    msgAndSend.setPreferredSize(new Dimension(150, 30));
    display.setPreferredSize(new Dimension(150, 178));
    msgAndSend.add(msg);
    msgAndSend.add(send);
    chat.add(msgAndSend, BorderLayout.SOUTH);

    msg.addActionListener(new TFListen(in.getName()));//回车发送信息
    mp = new MyPanel();
    checkBoard.add(mp, "Center");
    connect();//连接发送信息
    new Thread(new RecvThread()).start();//新建接收线程

    boardAll.addMouseListener(new MyMouse());

  }

  private class TFListen implements ActionListener {

    String name = null;
    @Override
    public void actionPerformed(ActionEvent e) {
      String str = msg.getText().trim();//trim消除两边的空格
      //display.setText(str);
      msg.setText("");
      try {
        dos.writeUTF(name+">>  "+str);//将字符串写出去
        dos.flush();
        //dos.close();
      } catch (IOException e1) {
        e1.printStackTrace();
      }
    }

    public TFListen(String name){
      this.name = name;
    }
  }

  private class RecvThread implements Runnable{//接受数据线程
    @Override
    public void run() {
      try {
        while (beConnected){
          String str = dis.readUTF();
          //System.out.println(str);
          display.setText(display.getText() +str + '\n');
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  public void connect() {

    try {
      s = new Socket("127.0.0.1", 8888);//这里就不要再定义s了,否则就成了局部变量
      dos = new DataOutputStream(s.getOutputStream());//初始化一个输出流
      dis = new DataInputStream(s.getInputStream());
      beConnected = true;
      System.out.println("Connected!!");
    } catch (IOException e) {
      e.printStackTrace();
    }


  }

  public void disconnect(){
    try {
      dos.close();
      s.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}

class MyPanel extends JPanel {
  //paint的真正原理是在一个自定义的JPanel上draw出自己的图案,在将JPanel add 到所需的地方...搞了我半天
  BufferedImage checkBg;

  public void paint(Graphics g) {
    super.paint(g);
    int[][] boardGrid = new int[15][15];


    try {
      checkBg = ImageIO.read(new File("IconRes/res/wuzi/board.gif"));
    } catch (IOException e) {
      e.printStackTrace();
    }
    g.drawImage(checkBg, 0, 0, 550, 550, this);
  }

}

class MyMouse implements MouseListener {
  @Override
  public void mouseClicked(MouseEvent e) {
    System.out.println(e.getX());
    System.out.println(e.getY());
  }

  @Override
  public void mousePressed(MouseEvent e) {

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