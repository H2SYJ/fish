package team.h2syj.fish.core;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import cn.hutool.json.JSONUtil;
import team.h2syj.fish.core.Renderer.Output;
import team.h2syj.fish.net.obj.MessageData;
import team.h2syj.fish.net.obj.MessageData.Type;

public class ServerOutput implements Output {
    private final ServerPlayer p1;
    private final ServerPlayer p2;

    public ServerOutput(ServerPlayer p1, ServerPlayer p2) {
        this.p1 = p1;
        this.p2 = p2;
    }

    @Override
    public void print(Object content) {
        String temp = "";
        if (content != null)
            temp = content.toString();

        MessageData messageData = Type.渲染内容.initMessage().setData(temp);
        TextMessage textMessage = new TextMessage(JSONUtil.toJsonStr(messageData));
        try {
            WebSocketSession session = p1.getSession();
            session.sendMessage(textMessage);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        try {
            WebSocketSession session = p2.getSession();
            session.sendMessage(textMessage);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
