package server;

import javax.swing.*;
import java.awt.*;

public class ClientInfo {
  String Name;
  String Ip;
  Icon Ic;



 public ClientInfo(String name,String ip,Icon ic){
    Name = name;
    Ip = ip;
    Ic = ic;
  }

  public String getName(){
    return Name;
  }
  public String getIp(){
    return Ip;
  }
  public Icon getIcon(){
    return Ic;
  }
}
