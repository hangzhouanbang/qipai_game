package com.anbang.qipai.game.cqrs.c.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dml.users.UserSessionsManager;

@Component
public class MemberAuthService {

	@Autowired
	private UserSessionsManager userSessionsManager;

	public String getMemberIdBySessionId(String sessionId) {
		return userSessionsManager.getUserIdBySessionId(sessionId);
	}

	public void createSessionForMember(String sessionId, String memberId) {
		userSessionsManager.createEngrossSessionForUser(memberId, sessionId, System.currentTimeMillis(), 0);
	}

}
