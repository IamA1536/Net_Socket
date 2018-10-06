package com.A.Server1006;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    private int limit;
    private ServerSocket ServerConn;
    private ArrayList<Socket> clinetList = new ArrayList<Socket>();

    Server() {
        limit = 20;
        try {
            ServerConn = new ServerSocket(8888);
            System.out.println("Server is available!");
        } catch (IOException e) {
//            e.printStackTrace();
            System.out.println("The port is unavailable£¡");
        }
    }

    Server(int limit) {
        this.limit = limit;
        try {
            ServerConn = new ServerSocket(8888);
            System.out.println("Server is available!");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("The port is unavailable£¡");
        }
    }

    public void ServerStart() {
        for (int i = 0; i < limit; i++) {
            try {
                Socket ss = ServerConn.accept();

            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Fail to creat a Server!");
            }
        }
    }
}
