package com.anbang.qipai.game.msg.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.support.MessageBuilder;

import com.anbang.qipai.game.msg.channel.source.GameDianpaomjResultSource;
import com.anbang.qipai.game.msg.msjobj.CommonMO;
import com.anbang.qipai.game.plan.bean.historicalresult.GameHistoricalJuResult;
import com.anbang.qipai.game.plan.bean.historicalresult.GameHistoricalPanResult;

@EnableBinding(GameDianpaomjResultSource.class)
public class DianpaomjResultMsgService {
	@Autowired
	private GameDianpaomjResultSource gameDianpaomjResultSource;

	public void newJuResult(GameHistoricalJuResult majiangHistoricalResult) {
		CommonMO mo = new CommonMO();
		mo.setMsg("dianpaomajiang ju result");
		mo.setData(majiangHistoricalResult);
		gameDianpaomjResultSource.gameDianpaomjResult().send(MessageBuilder.withPayload(mo).build());
	}

	public void newPanResult(GameHistoricalPanResult majiangHistoricalResult) {
		CommonMO mo = new CommonMO();
		mo.setMsg("dianpaomajiang pan result");
		mo.setData(majiangHistoricalResult);
		gameDianpaomjResultSource.gameDianpaomjResult().send(MessageBuilder.withPayload(mo).build());
	}
}
