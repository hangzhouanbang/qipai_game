package com.anbang.qipai.game.msg.receiver;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

import com.anbang.qipai.game.msg.channel.sink.WenzhouShuangkouResultSink;
import com.anbang.qipai.game.msg.msjobj.CommonMO;
import com.anbang.qipai.game.plan.service.GameHistoricalJuResultService;
import com.anbang.qipai.game.plan.service.GameHistoricalPanResultService;
import com.anbang.qipai.game.plan.service.GameService;
import com.google.gson.Gson;

@EnableBinding(WenzhouShuangkouResultSink.class)
public class WenzhouShuangkouResultMsgReceiver {
	@Autowired
	private GameHistoricalJuResultService gameHistoricalResultService;

	@Autowired
	private GameHistoricalPanResultService gameHistoricalPanResultService;

	@Autowired
	private GameService gameService;

	private Gson gson = new Gson();

	@StreamListener(WenzhouShuangkouResultSink.WENZHOUSHUANGKOURESULT)
	public void recordMajiangHistoricalResult(CommonMO mo) {
		String msg = mo.getMsg();
		String json = gson.toJson(mo.getData());
		Map map = gson.fromJson(json, Map.class);
		if ("wenzhoumajiang ju result".equals(msg)) {

		}
		if ("wenzhoumajiang pan result".equals(msg)) {

		}
	}
}
