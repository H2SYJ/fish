package team.h2syj.fish.core;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import cn.hutool.json.JSONUtil;
import team.h2syj.fish.core.Controller.Input;
import team.h2syj.fish.net.obj.MessageData;
import team.h2syj.fish.net.obj.MessageData.Type;
import team.h2syj.fish.player.Player;

public class ServerInput implements Input {
    private final ServerPlayer p1;
    private final ServerPlayer p2;
    private CountDownLatch countDown;
    private String input;

    public ServerInput(ServerPlayer p1, ServerPlayer p2) {
        this.p1 = p1;
        this.p2 = p2;
    }

    @Override
    public String nextLine(Player player) {
        // 发送指令输入
        WebSocketSession session = p1 == player ? p1.getSession() : p2.getSession();
        MessageData messageData = Type.等待输入.initMessage();
        try {
            session.sendMessage(new TextMessage(JSONUtil.toJsonStr(messageData)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.input = "";
        // 接收到输入的值进行返回
        this.countDown = new CountDownLatch(1);
        try {
            this.countDown.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return input;
    }

    public void end(String input) {
        this.input = input;
        countDown.countDown();
    }

}
