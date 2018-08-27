package com.anbang.qipai.game.msg.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.support.MessageBuilder;

import com.anbang.qipai.game.msg.channel.source.NoticeSource;
import com.anbang.qipai.game.msg.msjobj.CommonMO;
import com.anbang.qipai.game.plan.bean.notice.Notices;

@EnableBinding(NoticeSource.class)
public class NoticeMsgService {

	@Autowired
	private NoticeSource noticeSource;

	public void createNotice(Notices notices) {
		CommonMO mo = new CommonMO();
		mo.setMsg("newNotice");
		mo.setData(notices);
		noticeSource.notice().send(MessageBuilder.withPayload(mo).build());
	}

	public void memberLogoutNotice(String memberId) {
		CommonMO mo = new CommonMO();
		mo.setMsg("member logout");
		mo.setData(memberId);
		noticeSource.notice().send(MessageBuilder.withPayload(mo).build());
	}

	public void updateMemberOnlineNotice(String memberId) {
		CommonMO mo = new CommonMO();
		mo.setMsg("update member online");
		mo.setData(memberId);
		noticeSource.notice().send(MessageBuilder.withPayload(mo).build());
	}

}
