package team.h2syj.fish.core;

import org.springframework.web.socket.WebSocketSession;

import lombok.Getter;
import team.h2syj.fish.player.Player;

@Getter
public class ServerPlayer extends Player {

    private final WebSocketSession session;

    public ServerPlayer(WebSocketSession session) {
        this.session = session;
    }

}
