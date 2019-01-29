package com.anbang.qipai.game.msg.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.support.MessageBuilder;

import com.anbang.qipai.game.msg.channel.source.GameRuianmjResultSource;
import com.anbang.qipai.game.msg.msjobj.CommonMO;
import com.anbang.qipai.game.plan.bean.historicalresult.GameHistoricalJuResult;
import com.anbang.qipai.game.plan.bean.historicalresult.GameHistoricalPanResult;

@EnableBinding(GameRuianmjResultSource.class)
public class RuianmjResultMsgService {
	@Autowired
	private GameRuianmjResultSource gameRuianmjResultSource;

	public void newJuResult(GameHistoricalJuResult majiangHistoricalResult) {
		CommonMO mo = new CommonMO();
		mo.setMsg("ruianmajiang ju result");
		mo.setData(majiangHistoricalResult);
		gameRuianmjResultSource.gameRuianmjResult().send(MessageBuilder.withPayload(mo).build());
	}

	public void newPanResult(GameHistoricalPanResult majiangHistoricalResult) {
		CommonMO mo = new CommonMO();
		mo.setMsg("ruianmajiang pan result");
		mo.setData(majiangHistoricalResult);
		gameRuianmjResultSource.gameRuianmjResult().send(MessageBuilder.withPayload(mo).build());
	}
}
