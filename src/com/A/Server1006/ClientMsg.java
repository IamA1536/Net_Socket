package com.A.Server1006;

import com.A.Gobal_Data.Data;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class ClientMsg implements Runnable, Data {

    private Socket socket;
    private ArrayList<Socket> clientlist;
    private ArrayList<String> userName;

    ClientMsg(Socket socket, ArrayList<Socket> clientlist, ArrayList<String> userName) {
        this.socket = socket;
        this.clientlist = clientlist;
        this.userName = userName;
    }

    @Override
    public void run() {
        try {
            BufferedInputStream BIS = new BufferedInputStream(socket.getInputStream());
            byte[] b = new byte[serverbyte];
            Socket fromuser = null;
            String msg;
            String[] msgbody = new String[2];
            while (true) {
                int c = 0;
                String user = "User";
                int i = BIS.read(b);
                if (i == -1) break;
                msg = new String(b, 0, i);
                if (msg.indexOf("@") == -1) {
                    msgbody[0] = msg;
                    msgbody[1] = "All";
                } else msgbody = msg.split("@", 2);
                for (Socket s : clientlist) {
                    c++;
                    if (s.getPort() == socket.getPort()) {
                        user = userName.get(c - 1);
                        fromuser = s;
                        break;
                    }
                }
                if (msgbody[1].equals("All"))
                    for (Socket s : clientlist) sendMsg(s, user + ":" + msgbody[0]);
                else {
                    sendMsg(fromuser, user + ":" + msgbody[0]);
                    c = 0;
                    for (Socket s : clientlist) {
                        c++;
                        if (msgbody[1].equals(userName.get(c - 1))) {
                            sendMsg(s, user + ":" + msgbody[0]);
                            break;
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Fail to get IOStream!");
        }

    }


    private void sendMsg(Socket fromSocket, String msg) {
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
