package com.mygdx.game.tcp;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.net.ServerSocket;
import com.badlogic.gdx.net.Socket;
import com.badlogic.gdx.net.SocketHints;

import java.io.OutputStream;

public class Server extends Thread {
    @Override
    public void run() {
        openTcp();
    }

    public  void openTcp() {
        // 创建服务端套接字
        ServerSocket serverSocket = Gdx.net.newServerSocket(Net.Protocol.TCP, 8080, null);

        while (true) {
            // 等待客户端连接
            Socket socket = serverSocket.accept(null);

            try {
                // 获取输出流
                OutputStream outputStream = socket.getOutputStream();

                // 读取客户端发送的数据
                byte[] data = new byte[1024];
                int length = socket.getInputStream().read(data);
                String message = new String(data, 0, length);
                System.out.println("Received from client: " + message);

                // 向客户端发送响应
                outputStream.write("Hello, client!".getBytes());
                outputStream.flush();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                // 关闭客户端套接字
                socket.dispose();
            }
        }
    }
}
