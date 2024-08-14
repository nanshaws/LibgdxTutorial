## 7.1 HTTP请求

在libGDX中，可以使用HttpRequest类来发送HTTP请求。以下是一个简单的示例：

```
package com.mygdx.game.http;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.net.HttpRequestBuilder;
import com.badlogic.gdx.net.HttpStatus;

public class HttpExample {
    public static void send() {
        HttpRequestBuilder requestBuilder = new HttpRequestBuilder();
        Net.HttpRequest httpRequest = requestBuilder.newRequest().method(Net.HttpMethods.GET).url("https://www.google.de").content("q=libgdx&example=example").build();
        Gdx.net.sendHttpRequest(httpRequest, httpResponseListener);
        //有参数的
//        HttpRequestBuilder requestBuilder = new HttpRequestBuilder();
//        HttpRequest httpRequest = requestBuilder.newRequest().method(HttpMethods.GET).url("https://www.google.de").content("q=libgdx&example=example").build();
//        Gdx.net.sendHttpRequest(httpRequest, httpResponseListener);

    }

    private static final Net.HttpResponseListener httpResponseListener = new Net.HttpResponseListener() {
        @Override
        public void handleHttpResponse(Net.HttpResponse httpResponse) {
            if (httpResponse.getStatus().getStatusCode() == HttpStatus.SC_OK) {
                System.out.println("请求成功，响应内容：" + httpResponse.getResultAsString());
            } else {
                System.out.println("请求失败，状态码：" + httpResponse.getStatus().getStatusCode());
            }
        }

        @Override
        public void failed(Throwable throwable) {
            System.out.println("失败");
        }

        @Override
        public void cancelled() {
            System.out.println("取消");
        }
    };

}
```

## TCP通信，客户端

```
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

```

## TCP通信，服务端

```
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

```

