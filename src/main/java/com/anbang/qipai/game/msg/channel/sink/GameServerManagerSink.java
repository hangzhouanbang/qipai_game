package com.anbang.qipai.game.msg.channel.sink;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface GameServerManagerSink {

    String GAME_SERVER_MANAGER="gameServerManager";

    @Input
    SubscribableChannel gameServerManager();

}
