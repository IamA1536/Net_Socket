package com.A.Server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server1006 {
    private ServerSocket ss;

    public void talk() {
        try {
            ss = new ServerSocket(8888);
            Socket serverconn = ss.accept();
            InputStream in = serverconn.getInputStream();
            OutputStream out = serverconn.getOutputStream();
            out.write("welcome\r\n".getBytes());

            byte[] buffer = new byte[5];
            for (int i = 0; i < buffer.length; i++) buffer[i] = (byte) in.read();

            out.write(("you said:" + new String(buffer)).getBytes());

            in.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
