package com.A.Server1006;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class ClientMsg implements Runnable {

    Socket socket;
    ArrayList<Socket> clientlist;

    ClientMsg(Socket socket, ArrayList<Socket> clientlist) {
        this.socket = socket;
        this.clientlist = clientlist;
    }

    @Override
    public void run() {
        try {
            BufferedInputStream BIS = new BufferedInputStream(socket.getInputStream());
            byte[] b = new byte[1024];
            String msg;
            while (true){
                int i = BIS.read();
                msg = new String(b, 0 ,i);
                for (Socket s:clientlist){

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Fail to get IOStream!");
        }

    }

    private void sendMsg(Socket fromSocket, String msg){
        try {
            BufferedOutputStream BOS = new BufferedOutputStream(fromSocket.getOutputStream());
            byte[] b = msg.getBytes();
            BOS.write(b, 0, b.length);
            BOS.flush();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Fail to send message!");
        }
    }
}
