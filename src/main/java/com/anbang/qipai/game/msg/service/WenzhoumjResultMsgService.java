package com.anbang.qipai.game.msg.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.support.MessageBuilder;

import com.anbang.qipai.game.msg.channel.source.GameWenzhoumjResultSource;
import com.anbang.qipai.game.msg.msjobj.CommonMO;
import com.anbang.qipai.game.plan.bean.historicalresult.GameHistoricalJuResult;
import com.anbang.qipai.game.plan.bean.historicalresult.GameHistoricalPanResult;

@EnableBinding(GameWenzhoumjResultSource.class)
public class WenzhoumjResultMsgService {
	@Autowired
	private GameWenzhoumjResultSource gameWenzhoumjResultSource;

	public void newJuResult(GameHistoricalJuResult majiangHistoricalResult) {
		CommonMO mo = new CommonMO();
		mo.setMsg("wenzhoumajiang ju result");
		mo.setData(majiangHistoricalResult);
		gameWenzhoumjResultSource.gameWenzhoumjResult().send(MessageBuilder.withPayload(mo).build());
	}

	public void newPanResult(GameHistoricalPanResult majiangHistoricalResult) {
		CommonMO mo = new CommonMO();
		mo.setMsg("wenzhoumajiang pan result");
		mo.setData(majiangHistoricalResult);
		gameWenzhoumjResultSource.gameWenzhoumjResult().send(MessageBuilder.withPayload(mo).build());
	}
}
