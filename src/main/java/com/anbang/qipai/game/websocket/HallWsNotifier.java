package com.anbang.qipai.game.websocket;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

@Component
public class HallWsNotifier {

	private Map<String, WebSocketSession> idSessionMap = new ConcurrentHashMap<>();

	private Map<String, String> sessionIdMemberIdMap = new ConcurrentHashMap<>();

	private Map<String, String> memberIdSessionIdMap = new ConcurrentHashMap<>();

	public void removeSession(String id) {
		WebSocketSession removedSession = idSessionMap.remove(id);
		if (removedSession != null) {
			String removedMemberId = sessionIdMemberIdMap.remove(id);
			if (removedMemberId != null) {
				memberIdSessionIdMap.remove(removedMemberId);
			}
		}
	}

	public boolean hasSession(String id) {
		return idSessionMap.containsKey(id);
	}

	public void addSession(WebSocketSession session, String memberId) {
		idSessionMap.put(session.getId(), session);
		sessionIdMemberIdMap.put(session.getId(), memberId);
		memberIdSessionIdMap.put(memberId, session.getId());
	}

}
