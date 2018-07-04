package com.ccl.perfectisshit.practicethree.socketpractice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientSocket {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("127.0.0.1", 10086);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String s = bufferedReader.readLine();
            System.out.println(s);
            bufferedReader.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
