package team.h2syj.fish.core;

import team.h2syj.fish.core.Controller.Input;

public class ServerInput implements Input {
    private final ServerPlayer p1;
    private final ServerPlayer p2;

    public ServerInput(ServerPlayer p1, ServerPlayer p2) {
        this.p1 = p1;
        this.p2 = p2;
    }

    @Override
    public String nextLine() {
        // 发送指令输入
        // 接收到输入的值进行返回
        return null;
    }
}
