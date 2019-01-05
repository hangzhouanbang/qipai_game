package com.anbang.qipai.game.msg.channel.source;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface RoomManageSource {

    String CREATROOM = "creatRoom";
    String UPDATEPLAYER = "updatePlayer";

    @Output
    MessageChannel roomManage();
}
