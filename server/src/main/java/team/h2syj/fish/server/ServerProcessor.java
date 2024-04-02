package team.h2syj.fish.server;

import org.springframework.web.socket.WebSocketSession;

import team.h2syj.fish.core.Controller.Input;
import team.h2syj.fish.core.ServerInput;
import team.h2syj.fish.core.SystemSetting;
import team.h2syj.fish.net.obj.MessageData;

public abstract class ServerProcessor {
    public abstract void execute(WebSocketSession session, MessageData data);

    public static class ChooseServerProcessor extends ServerProcessor {
        @Override
        public void execute(WebSocketSession session, MessageData data) {
            String choose = data.convert(String.class);
            Input input = SystemSetting.input;
            if (input instanceof ServerInput serverInput)
                serverInput.end(choose);
        }
    }

}