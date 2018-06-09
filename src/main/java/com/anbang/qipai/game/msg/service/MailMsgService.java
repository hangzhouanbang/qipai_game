package com.anbang.qipai.game.msg.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.support.MessageBuilder;

import com.anbang.qipai.game.msg.channel.MailSource;
import com.anbang.qipai.game.msg.msjobj.CommonMO;
import com.anbang.qipai.game.plan.bean.mail.SystemMail;

@EnableBinding(MailSource.class)
public class MailMsgService {
	
	@Autowired
	private MailSource mailSource;
	public void createmail(SystemMail mail) {
		CommonMO mo = new CommonMO();
		mo.setMsg("newMail");
		mo.setData(mail);
		mailSource.mail().send(MessageBuilder.withPayload(mo).build());
	}

}
