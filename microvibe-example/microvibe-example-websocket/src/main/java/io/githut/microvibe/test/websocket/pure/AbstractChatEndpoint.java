package io.githut.microvibe.test.websocket.pure;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.Session;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public abstract class AbstractChatEndpoint {

    static final ObjectMapper mapper = new ObjectMapper();
    static {
        mapper.setSerializationInclusion(Include.NON_NULL);
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        mapper.configure(JsonParser.Feature.ALLOW_MISSING_VALUES, true);
        mapper.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
    }
    static Map<String, Session> sessions = new HashMap<>();
    private String currentUser;

    public AbstractChatEndpoint() {
        super();
    }

    protected void register(String user, Session session) {
        this.currentUser = user;
        if (sessions.containsKey(user)) {
            System.out.printf("user [%s] has been online, kickout!%n", user);
            Session older = sessions.get(user);
            if (older.isOpen()) {
                try {
                    older.close();
                } catch (IOException e) {
                }
            }
        }
        sessions.put(user, session);
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        try {
            System.out.println(currentUser + "：" + message);
            @SuppressWarnings("unchecked")
            Map<String, Object> req = mapper.readValue(message, Map.class);
            String tousername = StringUtils.trimToNull((String) req.get("tousername"));
            String msgcontent = (String) req.get("msgcontent");

            Map<String, Object> resp = new HashMap<>();
            resp.put("time", req.get("time"));
            resp.put("msgcontent", msgcontent);
            resp.put("hash", this.hashCode());
            resp.put("currentUser", currentUser);
            Session tosession;
            if (tousername != null && (tosession = sessions.get(tousername)) != null) {
                resp.put("tousername", tousername);
                message = mapper.writeValueAsString(resp);
                tosession.getBasicRemote().sendText(message);
                session.getBasicRemote().sendText(message);
            } else {
                message = mapper.writeValueAsString(resp);
                String text = message;
                sessions.values().forEach(s -> {
                    try {
                        s.getBasicRemote().sendText(text);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }
//            return message;
        } catch (Exception e) {
            e.printStackTrace();
            message = "{error:\""
                    + e.getMessage().replaceAll("\"", "\\\"") + "\","
                    + "hash:\"" + this.hashCode() + "\""
                    + "currentUser:\"" + currentUser.replaceAll("\"", "\\\"") + "\""
                    + "}";
            try {
                session.getBasicRemote().sendText(message);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
//            return message;
        }

    }

    @OnError
    public void onError(Throwable t, Session session) {
        t.printStackTrace();
    }

    @OnClose
    public void onClose(Session session, CloseReason reason) {
        System.out.println(String.format("Session %s closed because of %s", session.getId(), reason));
        sessions.remove(currentUser);
    }

}
