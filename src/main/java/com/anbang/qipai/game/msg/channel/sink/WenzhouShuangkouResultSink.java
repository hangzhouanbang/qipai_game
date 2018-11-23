package com.anbang.qipai.game.msg.channel.sink;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface WenzhouShuangkouResultSink {
	String WENZHOUSHUANGKOURESULT = "wenzhouShuangkouResult";

	@Input
	SubscribableChannel wenzhouShuangkouResult();
}
