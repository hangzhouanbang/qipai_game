package com.anbang.qipai.game.msg.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.support.MessageBuilder;

import com.anbang.qipai.game.msg.channel.source.GameChayuanskResultSource;
import com.anbang.qipai.game.msg.msjobj.CommonMO;
import com.anbang.qipai.game.plan.bean.historicalresult.GameHistoricalJuResult;
import com.anbang.qipai.game.plan.bean.historicalresult.GameHistoricalPanResult;

@EnableBinding(GameChayuanskResultSource.class)
public class ChayuanskResultMsgService {

	@Autowired
	private GameChayuanskResultSource gameChayuanskResultSource;

	public void newJuResult(GameHistoricalJuResult pukeHistoricalResult) {
		CommonMO mo = new CommonMO();
		mo.setMsg("chayuanshuangkou ju result");
		mo.setData(pukeHistoricalResult);
		gameChayuanskResultSource.gameChayuanskResult().send(MessageBuilder.withPayload(mo).build());
	}

	public void newPanResult(GameHistoricalPanResult pukeHistoricalResult) {
		CommonMO mo = new CommonMO();
		mo.setMsg("chayuanshuangkou pan result");
		mo.setData(pukeHistoricalResult);
		gameChayuanskResultSource.gameChayuanskResult().send(MessageBuilder.withPayload(mo).build());
	}
}
