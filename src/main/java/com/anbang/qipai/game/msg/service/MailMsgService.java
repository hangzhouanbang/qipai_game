package com.anbang.qipai.game.msg.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.support.MessageBuilder;

import com.anbang.qipai.game.msg.channel.source.MailSource;
import com.anbang.qipai.game.msg.msjobj.CommonMO;
import com.anbang.qipai.game.plan.bean.mail.MailState;
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
	
	public void createMailState(List<MailState> list) {
		CommonMO mo = new CommonMO();
		mo.setMsg("newMailState");
		mo.setData(list);
		mailSource.mail().send(MessageBuilder.withPayload(mo).build());
	}
	
	public void updateMailState(MailState mailState) {
		CommonMO mo = new CommonMO();
		mo.setMsg("updateMailState");
		mo.setData(mailState);
		mailSource.mail().send(MessageBuilder.withPayload(mo).build());
	}
	
	public void updateMailStateAll(String id) {
		CommonMO mo = new CommonMO();
		mo.setMsg("updateMailStateAll");
		mo.setData(id);
		mailSource.mail().send(MessageBuilder.withPayload(mo).build());
	}
	
	public void deleteMailStateAll(String id) {
		CommonMO mo = new CommonMO();
		mo.setMsg("deleteMailStateAll");
		mo.setData(id);
		mailSource.mail().send(MessageBuilder.withPayload(mo).build());
	}

}
