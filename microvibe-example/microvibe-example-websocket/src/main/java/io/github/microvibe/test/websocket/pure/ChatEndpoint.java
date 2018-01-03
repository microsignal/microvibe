package io.github.microvibe.test.websocket.pure;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.springframework.stereotype.Component;

@Component
@ServerEndpoint("/ws/chat")
public class ChatEndpoint extends AbstractChatEndpoint {
    // 连接打开时执行
    @OnOpen
    public void onOpen(Session session) {
        Map<String, List<String>> map = session.getRequestParameterMap();
        List<String> list = map.get("user");
        String user;
        if (list != null && list.size() > 0) {
            user = list.get(0);
        } else {
            user = UUID.randomUUID().toString();
        }
        register(user, session);
        System.out.println("Connected ... " + session.getId());
    }

}
