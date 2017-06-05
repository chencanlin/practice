package com.org.ccl.practice.eventdelivery;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by ccl on 2017/6/5.
 */

public class Test {
    public static void main(String [] args){
        try {
            ServerSocket socket = new ServerSocket(20000);
            Socket accept = socket.accept();
            PrintWriter printWriter = new PrintWriter(accept.getOutputStream(), true);
            System.out.println(accept.getInetAddress().getHostName()+accept.getInetAddress().getHostAddress());
            printWriter.print("Hello World Client");
            printWriter.close();
            accept.close();
            socket.close();
            String s = "http://www.baidu.com";
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
