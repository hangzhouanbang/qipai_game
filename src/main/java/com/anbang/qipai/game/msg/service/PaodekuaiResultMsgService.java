package com.anbang.qipai.game.msg.service;

import com.anbang.qipai.game.msg.channel.source.GamePaodekuaiResultSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.support.MessageBuilder;

import com.anbang.qipai.game.msg.msjobj.CommonMO;
import com.anbang.qipai.game.plan.bean.historicalresult.GameHistoricalJuResult;
import com.anbang.qipai.game.plan.bean.historicalresult.GameHistoricalPanResult;

@EnableBinding(GamePaodekuaiResultSource.class)
public class PaodekuaiResultMsgService {
	@Autowired
	private GamePaodekuaiResultSource gamepaodekuaiResultSource;

	public void newJuResult(GameHistoricalJuResult pukeHistoricalResult) {
		CommonMO mo = new CommonMO();
		mo.setMsg("paodekuai ju result");
		mo.setData(pukeHistoricalResult);
		gamepaodekuaiResultSource.gamePaodekuaiResult().send(MessageBuilder.withPayload(mo).build());
	}

	public void newPanResult(GameHistoricalPanResult pukeHistoricalResult) {
		CommonMO mo = new CommonMO();
		mo.setMsg("douizhu pan result");
		mo.setData(pukeHistoricalResult);
		gamepaodekuaiResultSource.gamePaodekuaiResult().send(MessageBuilder.withPayload(mo).build());
	}
}
