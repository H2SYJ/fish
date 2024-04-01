package team.h2syj.fish.server;

import team.h2syj.fish.server.obj.MessageData;

public abstract class ServerProcessor {
    public abstract void execute(MessageData data);

    public static class ChooseServerProcessor extends ServerProcessor {
        @Override
        public void execute(MessageData data) {
            String choose = data.convert(String.class);

        }
    }

}
