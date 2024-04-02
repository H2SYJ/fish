package team.h2syj.fish.server;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class WebSocketHandler extends TextWebSocketHandler {

    private static final Map<String, WebSocketSession> map = new ConcurrentHashMap<>();
    private static final int POLLING_TIME = 60 * 1000; // 心跳轮询时间60秒

    static {
        new Thread(() -> {
            log.info("心跳轮询已启动");
            TextMessage message = new TextMessage("pong");
            while (true) {
                try {
                    Thread.sleep(POLLING_TIME);
                    if (!map.isEmpty()) {
                        for (WebSocketSession session : map.values()) {
                            session.sendMessage(message);
                        }
                    }
                } catch (Exception ignored) {
                }
            }
        }).start();
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        log.info("websocket连接 {}", session.getId());
        map.put(session.getId(), session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
        String json = message.getPayload();
        log.info("websocket接受消息 {}，message：{}", session.getId(), json);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) {
        map.remove(session.getId());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) {
        map.remove(session.getId());
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

}