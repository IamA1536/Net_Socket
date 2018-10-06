package com.A.Client1006;

import com.A.Gobal_Data.Data;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client implements Data {
    private Socket socket;
    private String UserName;

    Client(String UserName) {
        this.UserName = UserName;
        try {
            socket = new Socket("localhost", port);
            System.out.println("Client start!");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Fail to connect to server!");
        }
    }

    public void ClientStart() {
        try {
            BufferedOutputStream BOS = new BufferedOutputStream(socket.getOutputStream());
            byte[] b = UserName.getBytes();
            BOS.write(b, 0, b.length);
            BOS.flush();
            new Thread(new ClientMsg(socket)).start();
            Scanner input = new Scanner(System.in);
            while (true) {
                String msg = input.nextLine();
                if (msg.equals("EXIT")) break;
                b = msg.getBytes();
                BOS.write(b, 0, b.length);
                BOS.flush();
            }
            BOS.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Fail to get information from server!");

        }
    }
}
