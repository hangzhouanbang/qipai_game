package com.anbang.qipai.game.websocket;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.anbang.qipai.game.msg.service.MemberLoginRecordMsgService;
import com.anbang.qipai.game.plan.bean.members.MemberLoginRecord;
import com.anbang.qipai.game.plan.bean.notice.SystemNotice;
import com.anbang.qipai.game.plan.service.MemberAuthService;
import com.anbang.qipai.game.plan.service.MemberLoginRecordService;
import com.anbang.qipai.game.plan.service.SystemNoticeService;
import com.google.gson.Gson;

@Component
public class HallWsController extends TextWebSocketHandler {

	@Autowired
	private HallWsNotifier wsNotifier;

	@Autowired
	private MemberAuthService memberAuthService;

	@Autowired
	private SystemNoticeService systemNoticeService;

	@Autowired
	private MemberLoginRecordService memberLoginRecordService;

	@Autowired
	private MemberLoginRecordMsgService memberLoginRecordMsgService;

	private ExecutorService executorService = Executors.newCachedThreadPool();

	private Gson gson = new Gson();

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

		executorService.submit(() -> {
			CommonMO mo = gson.fromJson(message.getPayload(), CommonMO.class);
			String msg = mo.getMsg();
			if ("heartbeat".equals(msg)) {// 心跳
				processHeartbeat(session, mo.getData());
			} else {
			}
		});

	}

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		wsNotifier.addSession(session);
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		wsNotifier.removeSession(session.getId());
	}

	@Override
	public void handleTransportError(WebSocketSession session, Throwable error) throws Exception {
		executorService.submit(() -> {
			try {
				session.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		wsNotifier.removeSession(session.getId());
		error.printStackTrace();
	}

	/**
	 * 心跳
	 *
	 * @param session
	 * @param data
	 */
	private void processHeartbeat(WebSocketSession session, Object data) {
		Map map = (Map) data;
		String token = (String) map.get("token");
		if (token == null) {// 非法访问
			try {
				session.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return;
		}
		String memberId = memberAuthService.getMemberIdBySessionId(token);
		if (memberId == null) {// 非法的token
			try {
				session.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return;
		}
		if (wsNotifier.isRawSession(session.getId())) {// 第一条心跳
			wsNotifier.updateSession(session.getId(), memberId);
			// 发送系统公告
			List<SystemNotice> notices = systemNoticeService.findValidNotice();
			notices.forEach((notice) -> {
				CommonMO mo = new CommonMO();
				mo.setMsg("sysNotice");
				Map moData = new HashMap();
				moData.put("content", notice.getContent());
				mo.setData(moData);
				sendMessage(session, gson.toJson(mo));
			});

			// 登录记录
			MemberLoginRecord newRecord = new MemberLoginRecord();
			newRecord.setMemberId(memberId);
			long currentTime = System.currentTimeMillis();
			newRecord.setLoginTime(currentTime);
			newRecord.setOnlineTime(0);
			newRecord.setLoginIp(session.getRemoteAddress().getHostString());
			MemberLoginRecord lastRecord = memberLoginRecordService.findRecentRecordByMemberId(memberId);
			if (lastRecord != null) {
				newRecord.setLastLoginTime(lastRecord.getLoginTime());
			} else {
				newRecord.setLastLoginTime(currentTime);
			}
			memberLoginRecordService.save(newRecord);
			memberLoginRecordMsgService.memberLoginRecord(newRecord);
		} else {
			wsNotifier.updateSession(session.getId());
			// 更新在线时间
			MemberLoginRecord lastRecord = memberLoginRecordService.findRecentRecordByMemberId(memberId);
			lastRecord.setOnlineTime(System.currentTimeMillis() - lastRecord.getLoginTime());
			memberLoginRecordService.updateOnlineTimeById(lastRecord.getId(), lastRecord.getOnlineTime());
			memberLoginRecordMsgService.updateMemberOnlineRecord(lastRecord);
		}
	}

	private void sendMessage(WebSocketSession session, String message) {
		synchronized (session) {
			try {
				session.sendMessage(new TextMessage(message));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
