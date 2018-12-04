package com.anbang.qipai.game.msg.service;


import com.anbang.qipai.game.plan.bean.historicalresult.GameHistoricalJuResult;
import com.anbang.qipai.game.plan.bean.historicalresult.GameJuPlayerResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.support.MessageBuilder;

import com.anbang.qipai.game.msg.channel.source.GameDataReportSource;
import com.anbang.qipai.game.msg.msjobj.CommonMO;
import com.anbang.qipai.game.web.vo.GameDataReportVO;


/**
 * @author YaphetS
 * @date 2018/11/30
 */
@EnableBinding(GameDataReportSource.class)
public class GameDataReportMsgService {
	@Autowired
	private GameDataReportSource gameDataReportSource;

	public void recordGameDataReport(GameDataReportVO report) {
		CommonMO mo = new CommonMO();
		mo.setMsg("record gameDataReport");
		mo.setData(report);
		gameDataReportSource.gameDataReport().send(MessageBuilder.withPayload(mo).build());
	}
}
