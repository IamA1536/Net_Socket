package com.Other.Client;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws IOException {
        //创建套接字
        Socket socket = new Socket("localhost", 81);
        //获取套接字的输出流
        BufferedOutputStream bos = new BufferedOutputStream(socket.getOutputStream());
        //启动线程监听接收服务器端的消息
        new Thread(new ClientMessage(socket)).start();
        Scanner sc = new Scanner(System.in);
        //客户端发送消息
        while (true) {
            String msg = sc.nextLine();
            if ("exit".equals(msg)) {
                break;
            }
            byte[] b = msg.getBytes();
            bos.write(b, 0, b.length);
            bos.flush();
        }
        bos.close();
        socket.close();
    }
}

class ClientMessage implements Runnable {
    private Socket socket;

    public ClientMessage(Socket socket) {
        super();
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            BufferedInputStream bis = new BufferedInputStream(socket.getInputStream());
            byte[] b = new byte[1024 * 8];
            while (true) {
                int i = bis.read(b);
                if (i == -1) {
                    break;
                }
                String msgFromServer = new String(b, 0, i);
                System.out.println(msgFromServer);
            }
            bis.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}