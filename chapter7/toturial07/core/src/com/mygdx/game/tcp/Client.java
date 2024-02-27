package com.mygdx.game.tcp;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.net.Socket;
import com.badlogic.gdx.net.SocketHints;

import java.io.OutputStream;

public class Client extends Thread{
    @Override
    public void run() {
        receivedTCP();
    }

    public  void receivedTCP() {
        // 创建客户端套接字
        Socket socket = Gdx.net.newClientSocket(Net.Protocol.TCP, "localhost", 8080, null);

        try {
            // 获取输出流
            OutputStream outputStream = socket.getOutputStream();

            // 发送数据到服务器
            outputStream.write("Hello, server!".getBytes());
            outputStream.flush();
            // 读取服务器响应
            byte[] data = new byte[1024];
            int length = socket.getInputStream().read(data);
            String message = new String(data, 0, length);
            System.out.println("Received from server: " + message);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭客户端套接字
            socket.dispose();
        }
    }
}
