package com.anbang.qipai.game.msg.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.support.MessageBuilder;

import com.anbang.qipai.game.msg.channel.source.NoticeSource;
import com.anbang.qipai.game.msg.msjobj.CommonMO;
import com.anbang.qipai.game.plan.bean.notice.SystemNotice;

@EnableBinding(NoticeSource.class)
public class NoticeMsgService {

	@Autowired
	private NoticeSource noticeSource;

	public void createNotice(List<SystemNotice> notices) {
		CommonMO mo = new CommonMO();
		mo.setMsg("newNotice");
		mo.setData(notices);
		noticeSource.notice().send(MessageBuilder.withPayload(mo).build());
	}

	public void startNotice(SystemNotice notice) {
		CommonMO mo = new CommonMO();
		mo.setMsg("start notice");
		mo.setData(notice);
		noticeSource.notice().send(MessageBuilder.withPayload(mo).build());
	}

	public void stopNotice(SystemNotice notice) {
		CommonMO mo = new CommonMO();
		mo.setMsg("stop notice");
		mo.setData(notice);
		noticeSource.notice().send(MessageBuilder.withPayload(mo).build());
	}

	public void removeNotice(SystemNotice notice) {
		CommonMO mo = new CommonMO();
		mo.setMsg("remove notice");
		mo.setData(notice);
		noticeSource.notice().send(MessageBuilder.withPayload(mo).build());
	}
}
