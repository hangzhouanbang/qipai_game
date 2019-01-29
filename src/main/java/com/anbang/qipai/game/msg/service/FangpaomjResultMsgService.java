package com.anbang.qipai.game.msg.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.support.MessageBuilder;

import com.anbang.qipai.game.msg.channel.source.GameFangpaomjResultSource;
import com.anbang.qipai.game.msg.msjobj.CommonMO;
import com.anbang.qipai.game.plan.bean.historicalresult.GameHistoricalJuResult;
import com.anbang.qipai.game.plan.bean.historicalresult.GameHistoricalPanResult;

@EnableBinding(GameFangpaomjResultSource.class)
public class FangpaomjResultMsgService {
	@Autowired
	private GameFangpaomjResultSource gameFangpaomjResultSource;

	public void newJuResult(GameHistoricalJuResult majiangHistoricalResult) {
		CommonMO mo = new CommonMO();
		mo.setMsg("fangpaomajiang ju result");
		mo.setData(majiangHistoricalResult);
		gameFangpaomjResultSource.gameFangpaomjResult().send(MessageBuilder.withPayload(mo).build());
	}

	public void newPanResult(GameHistoricalPanResult majiangHistoricalResult) {
		CommonMO mo = new CommonMO();
		mo.setMsg("fangpaomajiang pan result");
		mo.setData(majiangHistoricalResult);
		gameFangpaomjResultSource.gameFangpaomjResult().send(MessageBuilder.withPayload(mo).build());
	}
}
