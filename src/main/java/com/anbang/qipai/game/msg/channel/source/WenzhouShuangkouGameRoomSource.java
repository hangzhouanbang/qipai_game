package com.anbang.qipai.game.msg.channel.source;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface WenzhouShuangkouGameRoomSource {
	@Output
	MessageChannel wenzhouShuangkouGameRoom();
}
