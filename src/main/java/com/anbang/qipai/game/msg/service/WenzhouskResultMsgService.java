package com.anbang.qipai.game.msg.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.support.MessageBuilder;

import com.anbang.qipai.game.msg.channel.source.GameWenzhouskResultSource;
import com.anbang.qipai.game.msg.msjobj.CommonMO;
import com.anbang.qipai.game.plan.bean.historicalresult.GameHistoricalJuResult;
import com.anbang.qipai.game.plan.bean.historicalresult.GameHistoricalPanResult;

@EnableBinding(GameWenzhouskResultSource.class)
public class WenzhouskResultMsgService {
	@Autowired
	private GameWenzhouskResultSource gameWenzhouskResultSource;

	public void newJuResult(GameHistoricalJuResult pukeHistoricalResult) {
		CommonMO mo = new CommonMO();
		mo.setMsg("wenzhoushuangkou ju result");
		mo.setData(pukeHistoricalResult);
		gameWenzhouskResultSource.gameWenzhouskResult().send(MessageBuilder.withPayload(mo).build());
	}

	public void newPanResult(GameHistoricalPanResult pukeHistoricalResult) {
		CommonMO mo = new CommonMO();
		mo.setMsg("wenzhoushuangkou pan result");
		mo.setData(pukeHistoricalResult);
		gameWenzhouskResultSource.gameWenzhouskResult().send(MessageBuilder.withPayload(mo).build());
	}
}
