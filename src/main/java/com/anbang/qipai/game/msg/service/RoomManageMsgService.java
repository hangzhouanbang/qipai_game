package com.anbang.qipai.game.msg.service;

import com.anbang.qipai.game.msg.channel.source.RoomManageSource;
import com.anbang.qipai.game.msg.msjobj.CommonMO;
import com.anbang.qipai.game.plan.bean.games.GameRoom;
import com.anbang.qipai.game.plan.bean.games.PlayersRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.support.MessageBuilder;

import java.util.List;

@EnableBinding(RoomManageSource.class)
public class RoomManageMsgService {

    @Autowired
    private RoomManageSource roomManageSource;

    public void creatRoom(GameRoom gameRoom) {
        CommonMO mo = new CommonMO();
        mo.setMsg(RoomManageSource.CREATROOM);
        mo.setData(gameRoom);
        roomManageSource.roomManage().send(MessageBuilder.withPayload(mo).build());
    }

    public void updatePlayer(GameRoom gameRoom) {
        CommonMO mo = new CommonMO();
        mo.setMsg(RoomManageSource.UPDATEPLAYER);
        mo.setData(gameRoom);
        roomManageSource.roomManage().send(MessageBuilder.withPayload(mo).build());
    }
}
