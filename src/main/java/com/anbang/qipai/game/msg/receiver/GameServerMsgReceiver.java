package com.anbang.qipai.game.msg.receiver;

import com.anbang.qipai.game.msg.GameServerMsgConstant;
import com.anbang.qipai.game.msg.channel.sink.GameServerManagerSink;
import com.anbang.qipai.game.plan.service.GameService;
import com.anbang.qipai.game.websocket.CommonMO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

import java.util.List;

@EnableBinding(GameServerManagerSink.class)
public class GameServerMsgReceiver {

    @Autowired
    private GameService gameService;

    @StreamListener(GameServerManagerSink.GAME_SERVER_MANAGER)
    public void gameServerMsgHandle(CommonMO commonMO){
        System.out.println(">>> 监听到消息:"+commonMO.getMsg()+","+commonMO.getData());
        final String msg=commonMO.getMsg();
        if (GameServerMsgConstant.STOP_GAME_SERVERS.equals(msg)){
            List<String> ids = (List<String>) commonMO.getData();
            this.gameService.stopGameServer(ids);
        }
        else if (GameServerMsgConstant.RECOVER_GAME_SERVERS.equals(msg)){
            List<String>ids= (List<String>) commonMO.getData();
            this.gameService.recoverGameServer(ids);
        }
    }



}
