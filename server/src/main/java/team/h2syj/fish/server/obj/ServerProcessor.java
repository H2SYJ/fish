package team.h2syj.fish.server.obj;

public abstract class ServerProcessor {
    public abstract void execute(MessageData data);

    public static class ChooseServerProcessor extends ServerProcessor {
        @Override
        public void execute(MessageData data) {
            String choose = data.convert(String.class);

        }
    }

}