package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ChatServer {
  boolean statred = false;
  ServerSocket ss = null;

  List<Client> clients = new ArrayList<Client>();//保存每个客户端的连接

  public static void main(String[] args) {
    new ChatServer().start();
  }

  public void start() {
    try {
      ss = new ServerSocket(8888);//8888是tcp的端口号
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
        Client c = new Client(s);//这里原本会报错,因为我们没办法在一个
        // 静态的main方法里面new一个动态内部类,解决方法就是代码都放到
        // 一个start方法里

        System.out.println("a client connected");
        new Thread(c).start();//每起来一个链接我就new一个线程
        clients.add(c);//保存该线程类
        //dis.close();
      }
    } catch (IOException e) {//说明读写socket过程中出错
      e.printStackTrace();
    } finally {
      try {
        ss.close();//整个serversocket关掉
      } catch (IOException e1) {
        e1.printStackTrace();
      }
    }
  }

  class Client implements Runnable {
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
          // String str = dis.readUTF(); readutf是阻塞式的,客户端关闭以后会报错,并且第二个客户端无法连接
          String str = dis.readUTF();
          System.out.println(str);

          for (int i = 0; i < clients.size(); i++) {
            Client c = clients.get(i);
            c.send(str);//服务器由此实现转发功能
          }

//          for (Iterator<Client> it = clients.iterator();it.hasNext();){
//            Client c =it.next();
//            c.send(str);//另一种实现
//          }

//          Iterator<Client> it = clients.iterator();
//          while (it.hasNext()){
//            Client c = it.next();
//            c.send(str);//这样写也可以
//          }
          //这两个方法都需要进行锁定,但实际上没必要锁定,否则效率低
        }
      } catch (Exception e2) {
        System.out.println("client closed");
      }
// catch (IOException e) {//说明读写socket过程中出错
//        e.printStackTrace();
//      }
      finally {
        try {
          if (dis != null) dis.close();//不等于null的话相当于未初始化,没必要关
          if (dos != null) dos.close();
          if (s != null) s.close();//捕获到任何异常都要把s关掉
        } catch (IOException e1) {
          e1.printStackTrace();
        }
      }
    }
  }
}

