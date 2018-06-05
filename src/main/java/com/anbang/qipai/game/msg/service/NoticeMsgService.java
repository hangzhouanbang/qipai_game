package com.anbang.qipai.game.msg.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.support.MessageBuilder;

import com.anbang.qipai.game.msg.channel.NoticeSource;
import com.anbang.qipai.game.msg.msjobj.CommonMO;
import com.anbang.qipai.game.plan.bean.Notices;

@EnableBinding(NoticeSource.class)
public class NoticeMsgService {
	
	@Autowired
	private NoticeSource gameSource;
	
	public void createNotice(Notices notices) {
		CommonMO mo = new CommonMO();
		mo.setMsg("newNotice");
		mo.setData(notices);
		gameSource.game().send(MessageBuilder.withPayload(mo).build());
	}
	

}
