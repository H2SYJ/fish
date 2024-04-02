package team.h2syj.fish.core;

public class ServerWorld extends Thread {

    private final ServerPlayer p1;
    private final ServerPlayer p2;

    public ServerWorld(ServerPlayer p1, ServerPlayer p2) {
        this.p1 = p1;
        this.p2 = p2;
    }

    @Override
    public void run() {
        new World(p1, p2).start();
    }
}
