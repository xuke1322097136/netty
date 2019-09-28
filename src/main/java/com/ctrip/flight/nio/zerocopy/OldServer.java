package com.ctrip.flight.nio.zerocopy;

import java.io.DataInputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by xuke
 * Description:
 * Date: 2019-07-20
 * Time: 13:36
 */
public class OldServer {
    public static void main(String[] args) throws Exception{
        ServerSocket serverSocket = new ServerSocket(8899);
        while (true){
            Socket socket = serverSocket.accept();
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());

            try{
                  byte[] readBytes = new byte[4096];
                  while (true){
                      int readCount = dataInputStream.read(readBytes, 0, readBytes.length);
                      if (readCount != -1){
                          break;
                      }
                  }
            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }
}
