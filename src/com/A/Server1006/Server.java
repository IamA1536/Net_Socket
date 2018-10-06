package com.A.Server1006;

import com.A.Gobal_Data.Data;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server implements Data {
    private int limit;
    private ServerSocket ServerConn;
    private ArrayList<Socket> clientList = new ArrayList<>();
    private ArrayList<String> clientName = new ArrayList<>();

    Server() {
        limit = 20;
        try {
            ServerConn = new ServerSocket(port);
            System.out.println("Server is available!");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("The port is unavailable£¡");
        }
    }

    Server(int limit) {
        this.limit = limit;
        try {
            ServerConn = new ServerSocket(port);
            System.out.println("Server is available!");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("The port is unavailable£¡");
        }
    }

    public String getName(Socket socket) {
        try {
            BufferedInputStream BIS = new BufferedInputStream(socket.getInputStream());
            byte[] b = new byte[serverbyte];
            String msg;
            int i = BIS.read(b);
            msg = new String(b, 0, i);
            return msg;
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Fail to get user's name!");
        }
        return null;
    }

    public void ServerStart() {
        for (int i = 0; i < limit; i++) {
            try {
                Socket ss = ServerConn.accept();
                clientList.add(ss);
                clientName.add(getName(ss));
                new Thread(new ClientMsg(ss, clientList, clientName)).start();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Fail to creat a Server!");
            }
        }
    }
}
