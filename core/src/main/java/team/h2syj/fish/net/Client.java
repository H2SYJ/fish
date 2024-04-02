package team.h2syj.fish.net;

import org.jetbrains.annotations.NotNull;

import cn.hutool.json.JSONUtil;
import okhttp3.OkHttpClient;
import okhttp3.OkHttpClient.Builder;
import okhttp3.Request;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import team.h2syj.fish.core.Runtime;
import team.h2syj.fish.core.SystemSetting;
import team.h2syj.fish.net.obj.MessageData;
import team.h2syj.fish.net.obj.MessageData.Type;

public class Client extends WebSocketListener {

    private final WebSocket socket;
    private boolean run = true;

    public Client(String host, int port) {
        OkHttpClient client = new Builder().build();
        Request request = new Request.Builder().url(String.format("ws://%s:%s", host, port)).build();
        this.socket = client.newWebSocket(request, this);
    }

    public void run() {
        while (run) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {
            }
            socket.send(JSONUtil.toJsonStr(Type.PING.initMessage()));
        }
    }

    @Override
    public void onMessage(@NotNull WebSocket webSocket, @NotNull String text) {
        MessageData data = JSONUtil.toBean(text, MessageData.class);
        Type type = data.getType();
        switch (type) {
        case 渲染内容 -> SystemSetting.output.print(data.convert(String.class));
        case 等待输入 -> {
            String input = SystemSetting.input.nextLine();
            MessageData messageData = Type.输入完成.initMessage();
            messageData.setData(input);
            socket.send(JSONUtil.toJsonStr(messageData));
        }
        case 游戏结束 -> {
            run = false;
            Runtime.endGame();
        }
        }
    }

    @Override
    public void onClosed(@NotNull WebSocket webSocket, int code, @NotNull String reason) {
        this.run = false;
    }
}
