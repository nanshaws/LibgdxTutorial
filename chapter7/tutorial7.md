## 7.1 HTTP请求

在libGDX中，可以使用HttpRequest类来发送HTTP请求。以下是一个简单的示例：

```
import com.badlogic.gdx.Net;
import com.badlogic.gdx.net.HttpRequest;
import com.badlogic.gdx.net.HttpResponse;
import com.badlogic.gdx.net.HttpStatus;

public class HttpExample {
    public static void main(String[] args) {
        HttpRequest httpRequest = new HttpRequest("http://www.baidu.com");
        HttpResponse httpResponse = httpRequest.send();

        if (httpResponse.getStatus().getStatusCode() == HttpStatus.SC_OK) {
            System.out.println("请求成功，响应内容：" + httpResponse.getResultAsString());
        } else {
            System.out.println("请求失败，状态码：" + httpResponse.getStatus().getStatusCode());
        }
    }
}
```

## 7.2 WebSocket通信

在libGDX中，可以使用WebSocket类进行WebSocket通信。以下是一个简单的示例：

```
import com.badlogic.gdx.Net;
import com.badlogic.gdx.net.WebSocket;
import com.badlogic.gdx.net.WebSocketListener;

public class WebSocketExample {
    public static void main(String[] args) {
        WebSocket webSocket = new WebSocket("ws://example.com", new MyWebSocketListener());
        webSocket.connect();
    }

    static class MyWebSocketListener implements WebSocketListener {
        @Override
        public void onConnected() {
            System.out.println("连接成功");
        }

        @Override
        public void onMessageReceived(String message) {
            System.out.println("收到消息：" + message);
        }

        @Override
        public void onDisconnected() {
            System.out.println("连接断开");
        }
    }
}
    
```

## 7.3 UDP通信，完整代码

在libGDX中，可以使用GdxUdpConnection类进行UDP通信。以下是一个简单的示例：

```
import com.badlogic.gdx.Net;
import com.badlogic.gdx.net.GdxUdpConnection;
import com.badlogic.gdx.net.GdxUdpPacket;
import com.badlogic.gdx.utils.TimeUtils;

public class UdpExample {
    public static void main(String[] args) {
        GdxUdpConnection udpConnection = new GdxUdpConnection();
        udpConnection.start(5000); // 监听端口5000

        // 发送数据
        byte[] data = "Hello, UDP!".getBytes();
        GdxUdpPacket packet = new GdxUdpPacket(data, "localhost", 5001); // 目标地址和端口
        udpConnection.send(packet);

        // 接收数据
        GdxUdpPacket receivedPacket = udpConnection.nextPacket();
        if (receivedPacket != null) {
            byte[] receivedData = receivedPacket.data;
            String receivedMessage = new String(receivedData);
            System.out.println("收到消息：" + receivedMessage);
        }

        udpConnection.stop(); // 停止UDP连接
    }
}

```

