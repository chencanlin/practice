package com.ccl.perfectisshit.practicethree.socketpractice;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

public class ServerSocket {

    public static void main(String[] args) {
        try {
            java.net.ServerSocket serverSocket = new java.net.ServerSocket(10086);
            while (true){
                Socket accept = serverSocket.accept();
                PrintStream printStream = new PrintStream(accept.getOutputStream());
                printStream.print("这是来自服务器的问候！");
                printStream.flush();
                printStream.close();
                accept.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
