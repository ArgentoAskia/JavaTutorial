package cn.argento.askia.websocket;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;

@ClientEndpoint
public class EchoClientEndPoint {

    @OnOpen
    public void onOpen(Session session) {
        System.out.println("连接已打开");
        try {
            session.getBasicRemote().sendText("Hello Server!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @OnMessage
    public void onMessage(String message) {
        System.out.println("收到消息：" + message);
    }

    @OnClose
    public void onClose(Session session, CloseReason reason) {
        System.out.println("连接关闭，原因：" + reason.getReasonPhrase());
    }

    public static void main(String[] args) throws Exception {
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        String uri = "ws://localhost:8080/websocket/echo";
        container.connectToServer(EchoClientEndPoint.class, URI.create(uri));
        // 保持主线程不退出
        Thread.sleep(10000);
    }
}

