package cn.argento.askia.websocket;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

@ServerEndpoint("/echo")
public class EchoServerEndPoint {

    private static final CopyOnWriteArraySet<Session> sessions = new CopyOnWriteArraySet<Session>();


    @OnOpen
    public void onOpen(Session session){
        sessions.add(session);
        System.out.println("新连接：" + session.getId() + ", 当前在线：" + sessions.size());
        // 可以发送欢迎消息
        sendMessage(session, "连接成功！");
    }

    @OnMessage
    public void onMessage(String message, Session session){
        System.out.println("收到消息：" + message + ", 来自：" + session.getId());
//        sendMessage(session, "服务器回声：" + message);
        // 也可以广播给所有人
         broadcast("用户 " + session.getId() + " 说：" + message, session.getId());
    }

    @OnClose
    public void onClose(Session session) {
        sessions.remove(session);
        System.out.println("连接关闭：" + session.getId() + "，当前在线：" + sessions.size());
    }

    @OnError
    public void onError(Session session, Throwable error) {
        System.err.println("连接错误：" + session.getId() + "，错误：" + error.getMessage());
    }

    // 辅助方法：发送消息
    private void sendMessage(Session session, String message) {
        try {
            session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 广播消息给所有在线客户端
    private void broadcast(String message, String selfId) {
        for (Session s : sessions) {
            if (selfId.equals(s.getId())){
                // 排除掉自己
                continue;
            }
            try {
                s.getBasicRemote().sendText(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
