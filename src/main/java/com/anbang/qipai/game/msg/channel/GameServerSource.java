package com.anbang.qipai.game.msg.channel;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface GameServerSource {
	@Output
	MessageChannel gameServer();
}
