package com.anbang.qipai.game.msg.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.support.MessageBuilder;

import com.anbang.qipai.game.msg.channel.source.GameDoudizhuResultSource;
import com.anbang.qipai.game.msg.msjobj.CommonMO;
import com.anbang.qipai.game.plan.bean.historicalresult.GameHistoricalJuResult;
import com.anbang.qipai.game.plan.bean.historicalresult.GameHistoricalPanResult;

@EnableBinding(GameDoudizhuResultSource.class)
public class DoudizhuResultMsgService {
	@Autowired
	private GameDoudizhuResultSource gameDoudizhuResultSource;

	public void newJuResult(GameHistoricalJuResult pukeHistoricalResult) {
		CommonMO mo = new CommonMO();
		mo.setMsg("doudizhu ju result");
		mo.setData(pukeHistoricalResult);
		gameDoudizhuResultSource.gameDoudizhuResult().send(MessageBuilder.withPayload(mo).build());
	}

	public void newPanResult(GameHistoricalPanResult pukeHistoricalResult) {
		CommonMO mo = new CommonMO();
		mo.setMsg("douizhu pan result");
		mo.setData(pukeHistoricalResult);
		gameDoudizhuResultSource.gameDoudizhuResult().send(MessageBuilder.withPayload(mo).build());
	}
}
