package com.A.Client1006;

import com.A.Gobal_Data.Data;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientMsg implements Runnable, Data {

    private Socket socket;

    ClientMsg(Socket socket) {
        super();
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            BufferedInputStream BIS = new BufferedInputStream(socket.getInputStream());
            byte[] b = new byte[clientbyte];
            while (true) {
                int i = BIS.read(b);
                if (i == -1) break;
                String msg = new String(b, 0, i);
                System.out.println(msg);
            }
            BIS.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Fail to get IOStream!");
        }
    }
}
