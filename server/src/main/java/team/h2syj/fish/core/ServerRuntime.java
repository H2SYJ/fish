package team.h2syj.fish.core;

import java.io.IOException;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import cn.hutool.json.JSONUtil;
import team.h2syj.fish.net.obj.ConnectionData;
import team.h2syj.fish.net.obj.MessageData;
import team.h2syj.fish.net.obj.MessageData.Type;

public class ServerRuntime {

    private static ServerPlayer p1;
    private static ServerPlayer p2;

    public static void connection(WebSocketSession session) throws IOException {
        boolean isP1 = p1 == null;
        if (isP1) {
            p1 = new ServerPlayer(session);
            p1.setName("P1");
        } else {
            p2 = new ServerPlayer(session);
            p1.setName("P2");
        }
        ConnectionData data = new ConnectionData();
        data.setSessionId(session.getId());
        data.setP1(isP1);

        MessageData messageData = Type.连接成功.initMessage();
        messageData.setData(data);

        session.sendMessage(new TextMessage(JSONUtil.toJsonStr(messageData)));
        if (p1 != null && p2 != null) {
            startGame();
        }
    }

    private static void startGame() {
        SystemSetting.output = new ServerOutput(p1, p2);
        SystemSetting.input = new ServerInput(p1, p2);
        new ServerWorld(p1, p2).start();
    }

}
