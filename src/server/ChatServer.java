package server;

import java.io.*;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ChatServer {
  //ClientInfo[] userInfo = new ClientInfo[30];//存储用户注册信息
  boolean statred = false;
  ServerSocket ss = null;
  List<Client> clients = new ArrayList<Client>();//保存每个客户端的连接

  public static void main(String[] args) {
    new ChatServer().start();
  }

  public void start() {
    try {
      ss = new ServerSocket(8888);
      statred = true;//连接状态
    } catch (BindException e1) {
      System.out.println("端口使用中...");
      System.exit(0);
    } catch (IOException e) {
      e.printStackTrace();
    }
    try {

      while (statred) {
        // boolean bConnected = false;
        Socket s = ss.accept();
        Client c = new Client(s);

        System.out.println("a client connected");
        new Thread(c).start();//每起来一个链接我就new一个线程
        clients.add(c);//保存该线程类
        //dis.close();
      }
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        ss.close();
      } catch (IOException e1) {
        e1.printStackTrace();
      }
    }
  }

  class Client implements Runnable {
    //下面为客户端所需要传输的数据类型
    private Socket s;
    private DataInputStream dis = null;
    private DataOutputStream dos = null;

    private boolean bConnected = false;

    public Client(Socket s) {
      this.s = s;
      try {
        dis = new DataInputStream(s.getInputStream());
        dos = new DataOutputStream(s.getOutputStream());
        bConnected = true;//被连状态
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    public void send(String str) {
      try {
        dos.writeUTF(str);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    @Override
    public void run() {

      //dis = new DataInputStream(s.getInputStream());//从连接中读取输入的字符流
      try {
        while (bConnected) {
          // String str = dis.readUTF();
          String str = dis.readUTF();
          //Object info = obi.readObject();
          //userInfo.add(info);
          System.out.println(str);

          for (int i = 0; i < clients.size(); i++) {
            Client c = clients.get(i);
            c.send(str);//服务器由此实现转发功能
          }


        }
      } catch (SocketException e3) {
        clients.remove(this);//
        System.out.println("a client quit");

      } catch (EOFException e2) {
        System.out.println("a client closed");
      } catch (IOException e) {
        System.out.println("a client closed");
      } finally {
        try {
          if (dis != null) dis.close();//不等于null的话相当于未初始化,没必要关
          if (dos != null) dos.close();
          if (s != null) s.close();//
          clients.remove(this);

        } catch (IOException e1) {
          e1.printStackTrace();
        }


      }
    }
  }


}

