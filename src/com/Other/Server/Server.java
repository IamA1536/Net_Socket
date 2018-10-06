package com.Other.Server;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket s = new ServerSocket(81);// 创建服务器套接字，指定端口号
        List<Socket> sockets = new ArrayList<Socket>();
        System.out.println("服务器启动了");
        int i = 0;
        for (; i <= 100; i++) {// 可以有100个客户端
            // accept()是一个阻塞方法，如果客户端没有请求到来，该方法就不会执行。请求到达后立即产生一个Socket对象
            // 侦听并接收到此套接字的连接
            Socket socket = s.accept();
            // 输出该套接字的字符串格式
            System.out.println(socket);
            sockets.add(socket);
            // 启动线程用来接收消息并且群发
            new Thread(new ChatMessage(socket, sockets)).start();
        }
    }
}

class ChatMessage implements Runnable {
    private Socket socket;
    private List<Socket> sockets;

    public ChatMessage(Socket socket, List<Socket> sockets) {
        super();
        this.socket = socket;
        this.sockets = sockets;
    }

    @Override
    public void run() {
        try {
            // 获取当前socket的输入流
            BufferedInputStream bis = new BufferedInputStream(
                    socket.getInputStream());
            byte[] b = new byte[1024];
            String msg = "";
            while (true) {
                int i = bis.read(b);
                if (i == -1) {
                    break;
                }
                //获取客户端的消息
                msg = new String(b, 0, i);
                //群发
                for (Socket s : sockets) {
                    out(s, socket.getInetAddress().getHostName() + ":" + msg);
                }
            }
        } catch (IOException e) {
        }
    }

    private void out(Socket s, String msg) {
        try {
            BufferedOutputStream bos = new BufferedOutputStream(
                    s.getOutputStream());
            byte[] b = msg.getBytes();
            bos.write(b, 0, b.length);
            bos.flush();
        } catch (IOException e) {

        }
    }
}