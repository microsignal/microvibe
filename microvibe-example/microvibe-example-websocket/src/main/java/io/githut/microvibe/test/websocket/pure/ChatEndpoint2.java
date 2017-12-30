package io.githut.microvibe.test.websocket.pure;

import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.springframework.stereotype.Component;

@Component
@ServerEndpoint("/ws/echo/{user}")
public class ChatEndpoint2 extends AbstractChatEndpoint {
    // 连接打开时执行
    @OnOpen
    public void onOpen(@PathParam("user") String user, Session session) {
        register(user, session);
        System.out.println("Connected ... " + session.getId());
    }

}
