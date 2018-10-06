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
            String msg;
            while (true) {
                int c = 0;
                String user = "User";
                int i = BIS.read(b);
                if (i == -1) break;
                msg = new String(b, 0, i);
                for (Socket s : clientlist) {
                    c++;
                    if (s.getPort() == socket.getPort()) {
                        user = userName.get(c - 1);
                        break;
                    }
                }
                for (Socket s : clientlist) sendMsg(s, user + ":" + msg);

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
