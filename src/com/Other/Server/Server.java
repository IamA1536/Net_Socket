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
        ServerSocket s = new ServerSocket(81);// �����������׽��֣�ָ���˿ں�
        List<Socket> sockets = new ArrayList<Socket>();
        System.out.println("������������");
        int i = 0;
        for (; i <= 100; i++) {// ������100���ͻ���
            // accept()��һ����������������ͻ���û�����������÷����Ͳ���ִ�С����󵽴����������һ��Socket����
            // ���������յ����׽��ֵ�����
            Socket socket = s.accept();
            // ������׽��ֵ��ַ�����ʽ
            System.out.println(socket);
            sockets.add(socket);
            // �����߳�����������Ϣ����Ⱥ��
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
            // ��ȡ��ǰsocket��������
            BufferedInputStream bis = new BufferedInputStream(
                    socket.getInputStream());
            byte[] b = new byte[1024];
            String msg = "";
            while (true) {
                int i = bis.read(b);
                if (i == -1) {
                    break;
                }
                //��ȡ�ͻ��˵���Ϣ
                msg = new String(b, 0, i);
                //Ⱥ��
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