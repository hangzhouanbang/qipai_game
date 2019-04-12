package com.anbang.qipai.game.msg.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.support.MessageBuilder;

import com.anbang.qipai.game.msg.channel.source.GameDaboluoResultSource;
import com.anbang.qipai.game.msg.msjobj.CommonMO;
import com.anbang.qipai.game.plan.bean.historicalresult.GameHistoricalJuResult;
import com.anbang.qipai.game.plan.bean.historicalresult.GameHistoricalPanResult;

@EnableBinding(GameDaboluoResultSource.class)
public class DaboluoResultMsgService {
	@Autowired
	private GameDaboluoResultSource gameDaboluoResultSource;

	public void newJuResult(GameHistoricalJuResult majiangHistoricalResult) {
		CommonMO mo = new CommonMO();
		mo.setMsg("daboluo ju result");
		mo.setData(majiangHistoricalResult);
		gameDaboluoResultSource.gameDaboluoResult().send(MessageBuilder.withPayload(mo).build());
	}

	public void newPanResult(GameHistoricalPanResult majiangHistoricalResult) {
		CommonMO mo = new CommonMO();
		mo.setMsg("daboluo pan result");
		mo.setData(majiangHistoricalResult);
		gameDaboluoResultSource.gameDaboluoResult().send(MessageBuilder.withPayload(mo).build());
	}
}
