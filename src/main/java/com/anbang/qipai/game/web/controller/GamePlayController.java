package com.anbang.qipai.game.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.client.api.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.anbang.qipai.game.cqrs.c.service.GameRoomCmdService;
import com.anbang.qipai.game.msg.service.ChayuanShuangkouGameRoomMsgService;
import com.anbang.qipai.game.msg.service.DaboluoGameRoomMsgService;
import com.anbang.qipai.game.msg.service.DianpaoGameRoomMsgService;
import com.anbang.qipai.game.msg.service.DoudizhuGameRoomMsgService;
import com.anbang.qipai.game.msg.service.FangpaoGameRoomMsgService;
import com.anbang.qipai.game.msg.service.GameServerMsgService;
import com.anbang.qipai.game.msg.service.PaodekuaiGameRoomMsgService;
import com.anbang.qipai.game.msg.service.RoomManageMsgService;
import com.anbang.qipai.game.msg.service.RuianGameRoomMsgService;
import com.anbang.qipai.game.msg.service.WenzhouGameRoomMsgService;
import com.anbang.qipai.game.msg.service.WenzhouShuangkouGameRoomMsgService;
import com.anbang.qipai.game.plan.bean.games.CanNotJoinMoreRoomsException;
import com.anbang.qipai.game.plan.bean.games.Game;
import com.anbang.qipai.game.plan.bean.games.GameLaw;
import com.anbang.qipai.game.plan.bean.games.GameRoom;
import com.anbang.qipai.game.plan.bean.games.GameServer;
import com.anbang.qipai.game.plan.bean.games.IllegalGameLawsException;
import com.anbang.qipai.game.plan.bean.games.LawsMutexGroup;
import com.anbang.qipai.game.plan.bean.games.MemberGameRoom;
import com.anbang.qipai.game.plan.bean.games.NoServerAvailableForGameException;
import com.anbang.qipai.game.plan.bean.games.PlayersRecord;
import com.anbang.qipai.game.plan.bean.members.Member;
import com.anbang.qipai.game.plan.bean.members.MemberGoldBalance;
import com.anbang.qipai.game.plan.bean.members.MemberLoginLimitRecord;
import com.anbang.qipai.game.plan.bean.members.MemberRights;
import com.anbang.qipai.game.plan.bean.members.NotVIPMemberException;
import com.anbang.qipai.game.plan.service.GameService;
import com.anbang.qipai.game.plan.service.MemberAuthService;
import com.anbang.qipai.game.plan.service.MemberGoldBalanceService;
import com.anbang.qipai.game.plan.service.MemberLoginLimitRecordService;
import com.anbang.qipai.game.plan.service.MemberService;
import com.anbang.qipai.game.remote.service.QipaiMembersRemoteService;
import com.anbang.qipai.game.remote.vo.CommonRemoteVO;
import com.anbang.qipai.game.util.CommonVoUtil;
import com.anbang.qipai.game.util.NumConvertChineseUtil;
import com.anbang.qipai.game.web.fb.CyskLawsFB;
import com.anbang.qipai.game.web.fb.DblLawsFB;
import com.anbang.qipai.game.web.fb.DdzLawsFB;
import com.anbang.qipai.game.web.fb.DpmjLawsFB;
import com.anbang.qipai.game.web.fb.FpmjLawsFB;
import com.anbang.qipai.game.web.fb.PdkLawsFB;
import com.anbang.qipai.game.web.fb.RamjLawsFB;
import com.anbang.qipai.game.web.fb.WzmjLawsFB;
import com.anbang.qipai.game.web.fb.WzskLawsFB;
import com.anbang.qipai.game.web.vo.CommonVO;
import com.anbang.qipai.game.web.vo.MemberGameRoomVO;
import com.anbang.qipai.game.web.vo.MemberPlayingRoomVO;
import com.google.gson.Gson;

/**
 * 去玩游戏相关的控制器
 * 
 * @author Neo
 *
 */
@RestController
@RequestMapping("/game")
public class GamePlayController {

	@Autowired
	private MemberAuthService memberAuthService;

	@Autowired
	private GameService gameService;

	@Autowired
	private MemberService memberService;

	@Autowired
	private MemberGoldBalanceService memberGoldBalanceService;

	@Autowired
	private GameRoomCmdService gameRoomCmdService;

	@Autowired
	private GameServerMsgService gameServerMsgService;

	@Autowired
	private QipaiMembersRemoteService qipaiMembersRomoteService;

	@Autowired
	private RuianGameRoomMsgService ruianGameRoomMsgService;

	@Autowired
	private FangpaoGameRoomMsgService fangpaoGameRoomMsgService;

	@Autowired
	private WenzhouGameRoomMsgService wenzhouGameRoomMsgService;

	@Autowired
	private DianpaoGameRoomMsgService dianpaoGameRoomMsgService;

	@Autowired
	private WenzhouShuangkouGameRoomMsgService wenzhouShuangkouGameRoomMsgService;

	@Autowired
	private ChayuanShuangkouGameRoomMsgService chayuanShuangkouGameRoomMsgService;

	@Autowired
	private DoudizhuGameRoomMsgService doudizhuGameRoomMsgService;

	@Autowired
	private PaodekuaiGameRoomMsgService paodekuaiGameRoomMsgService;

	@Autowired
	private DaboluoGameRoomMsgService daboluoGameRoomMsgService;

	@Autowired
	private MemberLoginLimitRecordService memberLoginLimitRecordService;

	@Autowired
	private RoomManageMsgService roomManageMsgService;

	@Autowired
	private HttpClient httpClient;

	private Gson gson = new Gson();

	/**
	 * 创建瑞安麻将房间
	 * 
	 * @param token
	 * @return
	 */
	@RequestMapping(value = "/create_ramj_room")
	@ResponseBody
	public CommonVO createRamjRoom(String token, @RequestBody List<String> lawNames) {
		CommonVO vo = new CommonVO();
		String memberId = memberAuthService.getMemberIdBySessionId(token);
		if (memberId == null) {
			vo.setSuccess(false);
			vo.setMsg("invalid token");
			return vo;
		}
		MemberLoginLimitRecord loginLimitRecord = memberLoginLimitRecordService.findByMemberId(memberId, true);
		if (loginLimitRecord != null) {
			vo.setSuccess(false);
			vo.setMsg("login limited");
			return vo;
		}
		Map data = new HashMap();
		Member member = memberService.findMember(memberId);
		MemberRights rights = member.getRights();

		GameRoom gameRoom;
		try {
			gameRoom = gameService.buildRamjGameRoom(memberId, lawNames);
		} catch (IllegalGameLawsException e) {
			vo.setSuccess(false);
			vo.setMsg("IllegalGameLawsException");
			return vo;
		} catch (NotVIPMemberException e) {
			int todayCreateVipRoomsCount = gameService.countTodayCreateVipRoomsCount(memberId);
			vo.setSuccess(false);
			vo.setMsg("NotVIPMemberException");
			data.put("todayCreateVipRoomsCount",
					NumConvertChineseUtil.toChinese(String.valueOf(todayCreateVipRoomsCount)));
			vo.setData(data);
			return vo;
		} catch (CanNotJoinMoreRoomsException e) {
			vo.setSuccess(false);
			vo.setMsg("CanNotJoinMoreRoomsException");
			return vo;
		} catch (NoServerAvailableForGameException e) {
			vo.setSuccess(false);
			vo.setMsg("NoServerAvailableForGameException");
			return vo;
		}
		RamjLawsFB fb = new RamjLawsFB(lawNames);
		// 普通会员每日开房（vip房）金币价格
		int gold = fb.payForCreateRoom();
		// 房主玩家记录
		List<PlayersRecord> playersRecord = new ArrayList<>();
		gameRoom.setPlayersRecord(playersRecord);
		PlayersRecord record = new PlayersRecord();
		record.setPlayerId(member.getId());
		record.setVip(member.isVip());
		record.setPayGold(gold);
		playersRecord.add(record);
		gameService.saveGameRoom(gameRoom);
		// 普通会员开vip房扣金币

		GameServer gameServer = gameRoom.getServerGame().getServer();
		// 游戏服务器rpc，需要手动httpclientrpc
		Request req = httpClient.newRequest(gameServer.getHttpUrl() + "/game/newgame");
		req.param("playerId", memberId);
		req.param("difen", fb.getDifen());
		req.param("taishu", fb.getTaishu());
		req.param("panshu", fb.getPanshu());
		req.param("renshu", fb.getRenshu());
		req.param("dapao", fb.getDapao());
		Map resData;
		try {
			ContentResponse res = req.send();
			String resJson = new String(res.getContent());
			CommonVO resVo = gson.fromJson(resJson, CommonVO.class);
			resData = (Map) resVo.getData();
			gameRoom.getServerGame().setGameId((String) resData.get("gameId"));
		} catch (Exception e) {
			vo.setSuccess(false);
			vo.setMsg("SysException");
			return vo;
		}

		// 普通会员开vip房扣金币
		if (!member.isVip() && gameRoom.isVip()) {
			CommonRemoteVO rvo = qipaiMembersRomoteService.gold_withdraw(memberId, gold, "pay for create room");
			if (!rvo.isSuccess()) {
				vo.setSuccess(false);
				vo.setMsg(rvo.getMsg());
				return vo;
			}
		}

		String roomNo = gameRoomCmdService.createRoom(memberId, System.currentTimeMillis());
		gameRoom.setNo(roomNo);

		gameService.createGameRoom(gameRoom);
		// 发送房间创建消息
		roomManageMsgService.creatRoom(gameRoom);

		data.put("httpUrl", gameRoom.getServerGame().getServer().getHttpUrl());
		data.put("wsUrl", gameRoom.getServerGame().getServer().getWsUrl());
		data.put("roomNo", gameRoom.getNo());
		data.put("gameId", gameRoom.getServerGame().getGameId());
		data.put("token", resData.get("token"));
		data.put("game", gameRoom.getGame());
		vo.setData(data);
		return vo;

	}

	/**
	 * 创建放炮麻将房间
	 * 
	 * @param token
	 * @return
	 */
	@RequestMapping(value = "/create_fpmj_room")
	@ResponseBody
	public CommonVO createFpmjRoom(String token, @RequestBody List<String> lawNames) {
		CommonVO vo = new CommonVO();
		String memberId = memberAuthService.getMemberIdBySessionId(token);
		if (memberId == null) {
			vo.setSuccess(false);
			vo.setMsg("invalid token");
			return vo;
		}
		MemberLoginLimitRecord loginLimitRecord = memberLoginLimitRecordService.findByMemberId(memberId, true);
		if (loginLimitRecord != null) {
			vo.setSuccess(false);
			vo.setMsg("login limited");
			return vo;
		}
		Member member = memberService.findMember(memberId);
		MemberRights rights = member.getRights();

		Map data = new HashMap();
		GameRoom gameRoom;
		try {
			gameRoom = gameService.buildFpmjGameRoom(memberId, lawNames);
		} catch (IllegalGameLawsException e) {
			vo.setSuccess(false);
			vo.setMsg("IllegalGameLawsException");
			return vo;
		} catch (NotVIPMemberException e) {
			int todayCreateVipRoomsCount = gameService.countTodayCreateVipRoomsCount(memberId);
			vo.setSuccess(false);
			vo.setMsg("NotVIPMemberException");
			data.put("todayCreateVipRoomsCount",
					NumConvertChineseUtil.toChinese(String.valueOf(todayCreateVipRoomsCount)));
			vo.setData(data);
			return vo;
		} catch (CanNotJoinMoreRoomsException e) {
			vo.setSuccess(false);
			vo.setMsg("CanNotJoinMoreRoomsException");
			return vo;
		} catch (NoServerAvailableForGameException e) {
			vo.setSuccess(false);
			vo.setMsg("NoServerAvailableForGameException");
			return vo;
		}

		int gold = rights.getPlanMemberCreateRoomDailyGoldPrice();
		// 房主玩家记录
		List<PlayersRecord> playersRecord = new ArrayList<>();
		gameRoom.setPlayersRecord(playersRecord);
		PlayersRecord record = new PlayersRecord();
		record.setPlayerId(member.getId());
		record.setVip(member.isVip());
		record.setPayGold(gold);
		playersRecord.add(record);
		gameService.saveGameRoom(gameRoom);

		GameServer gameServer = gameRoom.getServerGame().getServer();
		// 游戏服务器rpc，需要手动httpclientrpc
		FpmjLawsFB fb = new FpmjLawsFB(lawNames);
		Request req = httpClient.newRequest(gameServer.getHttpUrl() + "/game/newgame");
		req.param("playerId", memberId);
		req.param("panshu", fb.getPanshu());
		req.param("renshu", fb.getRenshu());
		req.param("hongzhongcaishen", fb.getHognzhongcaishen());
		req.param("dapao", fb.getDapao());
		req.param("sipaofanbei", fb.getSipaofanbei());
		req.param("zhuaniao", fb.getZhuaniao());
		req.param("niaoshu", fb.getNiaoshu());
		Map resData;
		try {
			ContentResponse res = req.send();
			String resJson = new String(res.getContent());
			CommonVO resVo = gson.fromJson(resJson, CommonVO.class);
			resData = (Map) resVo.getData();
			gameRoom.getServerGame().setGameId((String) resData.get("gameId"));
		} catch (Exception e) {
			vo.setSuccess(false);
			vo.setMsg("SysException");
			return vo;
		}

		// 普通会员开vip房扣金币
		if (!member.isVip() && gameRoom.isVip()) {
			CommonRemoteVO rvo = qipaiMembersRomoteService.gold_withdraw(memberId, gold, "pay for create room");
			if (!rvo.isSuccess()) {
				vo.setSuccess(false);
				vo.setMsg(rvo.getMsg());
				return vo;
			}
		}

		String roomNo = gameRoomCmdService.createRoom(memberId, System.currentTimeMillis());
		gameRoom.setNo(roomNo);

		gameService.createGameRoom(gameRoom);
		// 发送房间创建消息
		roomManageMsgService.creatRoom(gameRoom);

		data.put("httpUrl", gameRoom.getServerGame().getServer().getHttpUrl());
		data.put("wsUrl", gameRoom.getServerGame().getServer().getWsUrl());
		data.put("roomNo", gameRoom.getNo());
		data.put("gameId", gameRoom.getServerGame().getGameId());
		data.put("token", resData.get("token"));
		data.put("game", gameRoom.getGame());
		vo.setData(data);
		return vo;

	}

	/**
	 * 创建温州麻将房间
	 * 
	 * @param token
	 * @return
	 */
	@RequestMapping(value = "/create_wzmj_room")
	@ResponseBody
	public CommonVO createWzmjRoom(String token, @RequestBody List<String> lawNames) {
		CommonVO vo = new CommonVO();
		String memberId = memberAuthService.getMemberIdBySessionId(token);
		if (memberId == null) {
			vo.setSuccess(false);
			vo.setMsg("invalid token");
			return vo;
		}
		MemberLoginLimitRecord loginLimitRecord = memberLoginLimitRecordService.findByMemberId(memberId, true);
		if (loginLimitRecord != null) {
			vo.setSuccess(false);
			vo.setMsg("login limited");
			return vo;
		}
		Member member = memberService.findMember(memberId);
		MemberRights rights = member.getRights();
		Map data = new HashMap();
		GameRoom gameRoom;
		try {
			gameRoom = gameService.buildWzmjGameRoom(memberId, lawNames);
		} catch (IllegalGameLawsException e) {
			vo.setSuccess(false);
			vo.setMsg("IllegalGameLawsException");
			return vo;
		} catch (NotVIPMemberException e) {
			int todayCreateVipRoomsCount = gameService.countTodayCreateVipRoomsCount(memberId);
			vo.setSuccess(false);
			vo.setMsg("NotVIPMemberException");
			data.put("todayCreateVipRoomsCount",
					NumConvertChineseUtil.toChinese(String.valueOf(todayCreateVipRoomsCount)));
			vo.setData(data);
			return vo;
		} catch (CanNotJoinMoreRoomsException e) {
			vo.setSuccess(false);
			vo.setMsg("CanNotJoinMoreRoomsException");
			return vo;
		} catch (NoServerAvailableForGameException e) {
			vo.setSuccess(false);
			vo.setMsg("NoServerAvailableForGameException");
			return vo;
		}

		int gold = rights.getPlanMemberCreateRoomDailyGoldPrice();
		// 房主玩家记录
		List<PlayersRecord> playersRecord = new ArrayList<>();
		gameRoom.setPlayersRecord(playersRecord);
		PlayersRecord record = new PlayersRecord();
		record.setPlayerId(member.getId());
		record.setVip(member.isVip());
		record.setPayGold(gold);
		playersRecord.add(record);
		gameService.saveGameRoom(gameRoom);

		GameServer gameServer = gameRoom.getServerGame().getServer();
		// 游戏服务器rpc，需要手动httpclientrpc
		WzmjLawsFB fb = new WzmjLawsFB(lawNames);
		Request req = httpClient.newRequest(gameServer.getHttpUrl() + "/game/newgame");
		req.param("playerId", memberId);
		req.param("panshu", fb.getPanshu());
		req.param("renshu", fb.getRenshu());
		req.param("jinjie1", fb.getJinjie1());
		req.param("jinjie2", fb.getJinjie2());
		req.param("gangsuanfen", fb.getGangsuanfen());
		req.param("teshushuangfan", fb.getTeshushuangfan());
		req.param("caishenqian", fb.getCaishenqian());
		req.param("shaozhongfa", fb.getShaozhongfa());
		req.param("lazila", fb.getLazila());
		Map resData;
		try {
			ContentResponse res = req.send();
			String resJson = new String(res.getContent());
			CommonVO resVo = gson.fromJson(resJson, CommonVO.class);
			resData = (Map) resVo.getData();
			gameRoom.getServerGame().setGameId((String) resData.get("gameId"));
		} catch (Exception e) {
			vo.setSuccess(false);
			vo.setMsg("SysException");
			return vo;
		}

		// 普通会员开vip房扣金币
		if (!member.isVip() && gameRoom.isVip()) {
			CommonRemoteVO rvo = qipaiMembersRomoteService.gold_withdraw(memberId, gold, "pay for create room");
			if (!rvo.isSuccess()) {
				vo.setSuccess(false);
				vo.setMsg(rvo.getMsg());
				return vo;
			}
		}

		String roomNo = gameRoomCmdService.createRoom(memberId, System.currentTimeMillis());
		gameRoom.setNo(roomNo);

		gameService.createGameRoom(gameRoom);
		// 发送房间创建消息
		roomManageMsgService.creatRoom(gameRoom);

		data.put("httpUrl", gameRoom.getServerGame().getServer().getHttpUrl());
		data.put("wsUrl", gameRoom.getServerGame().getServer().getWsUrl());
		data.put("roomNo", gameRoom.getNo());
		data.put("gameId", gameRoom.getServerGame().getGameId());
		data.put("token", resData.get("token"));
		data.put("game", gameRoom.getGame());
		vo.setData(data);
		return vo;

	}

	/**
	 * 创建点炮麻将房间
	 */
	@RequestMapping(value = "/create_dpmj_room")
	@ResponseBody
	public CommonVO createDpmjRoom(String token, @RequestBody List<String> lawNames) {
		CommonVO vo = new CommonVO();
		// 根据token从UserSessionsManager的Map<String, UserSession>
		// idSessionMap拿到memberId(memberDbo表)
		String memberId = memberAuthService.getMemberIdBySessionId(token);
		if (memberId == null) {
			vo.setSuccess(false);
			vo.setMsg("invalid token");
			return vo;
		}
		// 根据memberId查询是否被限制
		MemberLoginLimitRecord loginLimitRecord = memberLoginLimitRecordService.findByMemberId(memberId, true);
		if (loginLimitRecord != null) {
			vo.setSuccess(false);
			vo.setMsg("login limited");
			return vo;
		}
		// 根据memberId查询到member
		Member member = memberService.findMember(memberId);
		// 得到member中的会员权益
		MemberRights rights = member.getRights();
		Map data = new HashMap();
		GameRoom gameRoom;
		try {
			gameRoom = gameService.buildDpmjGameRoom(memberId, lawNames);
		} catch (IllegalGameLawsException e) {
			vo.setSuccess(false);
			vo.setMsg("IllegalGameLawsException");
			return vo;
		} catch (NotVIPMemberException e) {
			int todayCreateVipRoomsCount = gameService.countTodayCreateVipRoomsCount(memberId);
			vo.setSuccess(false);
			vo.setMsg("NotVIPMemberException");
			data.put("todayCreateVipRoomsCount",
					NumConvertChineseUtil.toChinese(String.valueOf(todayCreateVipRoomsCount)));
			vo.setData(data);
			return vo;
		} catch (CanNotJoinMoreRoomsException e) {
			vo.setSuccess(false);
			vo.setMsg("CanNotJoinMoreRoomsException");
			return vo;
		} catch (NoServerAvailableForGameException e) {
			vo.setSuccess(false);
			vo.setMsg("NoServerAvailableForGameException");
			return vo;
		}

		// 普通会员每日开房（vip房）金币价格
		int gold = rights.getPlanMemberCreateRoomDailyGoldPrice();
		// 房主玩家记录
		List<PlayersRecord> playersRecord = new ArrayList<>();
		// 玩家记录存入gameRoom
		gameRoom.setPlayersRecord(playersRecord);
		PlayersRecord record = new PlayersRecord();
		record.setPlayerId(member.getId());
		record.setVip(member.isVip());
		record.setPayGold(gold);
		playersRecord.add(record);

		gameService.saveGameRoom(gameRoom);

		GameServer gameServer = gameRoom.getServerGame().getServer();
		// 游戏服务器rpc，需要手动httpclientrpc
		DpmjLawsFB fb = new DpmjLawsFB(lawNames);
		// 远程调用游戏服务器的newgame
		Request req = httpClient.newRequest(gameServer.getHttpUrl() + "/game/newgame");
		req.param("playerId", memberId);
		req.param("panshu", fb.getPanshu());
		req.param("renshu", fb.getRenshu());
		req.param("dianpao", fb.getDianpao());
		req.param("dapao", fb.getDapao());
		req.param("quzhongfabai", fb.getQuzhongfabai());
		req.param("zhuaniao", fb.getZhuaniao());
		req.param("niaoshu", fb.getNiaoshu());
		req.param("qingyise", fb.getQingyise());
		Map resData;
		try {
			ContentResponse res = req.send();
			String resJson = new String(res.getContent());
			CommonVO resVo = gson.fromJson(resJson, CommonVO.class);
			// 游戏服务器回传的参数带有gameId和token
			resData = (Map) resVo.getData();
			gameRoom.getServerGame().setGameId((String) resData.get("gameId"));
		} catch (Exception e) {
			vo.setSuccess(false);
			vo.setMsg("SysException");
			return vo;
		}

		// 普通会员开vip房扣金币,调用member系统中的方法
		if (!member.isVip() && gameRoom.isVip()) {
			CommonRemoteVO rvo = qipaiMembersRomoteService.gold_withdraw(memberId, gold, "pay for create room");
			if (!rvo.isSuccess()) {
				vo.setSuccess(false);
				vo.setMsg(rvo.getMsg());
				return vo;
			}
		}
		// 创建游戏房间的编号
		String roomNo = gameRoomCmdService.createRoom(memberId, System.currentTimeMillis());
		gameRoom.setNo(roomNo);

		// 将带roomNo的gameRoom写入数据库
		gameService.createGameRoom(gameRoom);
		// 发送房间创建消息
		roomManageMsgService.creatRoom(gameRoom);

		data.put("httpUrl", gameRoom.getServerGame().getServer().getHttpUrl());
		data.put("wsUrl", gameRoom.getServerGame().getServer().getWsUrl());
		data.put("roomNo", gameRoom.getNo());
		data.put("gameId", gameRoom.getServerGame().getGameId());
		// 存入了游戏服务器传回的token
		data.put("token", resData.get("token"));
		data.put("game", gameRoom.getGame());
		vo.setData(data);
		return vo;

	}

	/**
	 * 创建温州双扣房间
	 */
	@RequestMapping(value = "/create_wzsk_room")
	@ResponseBody
	public CommonVO createWzskRoom(String token, @RequestBody List<String> lawNames) {
		CommonVO vo = new CommonVO();
		// 根据token从UserSessionsManager的Map<String, UserSession>
		// idSessionMap拿到memberId(memberDbo表)
		String memberId = memberAuthService.getMemberIdBySessionId(token);
		if (memberId == null) {
			vo.setSuccess(false);
			vo.setMsg("invalid token");
			return vo;
		}
		// 根据memberId查询是否被限制
		MemberLoginLimitRecord loginLimitRecord = memberLoginLimitRecordService.findByMemberId(memberId, true);
		if (loginLimitRecord != null) {
			vo.setSuccess(false);
			vo.setMsg("login limited");
			return vo;
		}
		// 根据memberId查询到member
		Member member = memberService.findMember(memberId);
		// 得到member中的会员权益
		Map data = new HashMap();
		GameRoom gameRoom;
		try {
			gameRoom = gameService.buildWzskGameRoom(memberId, lawNames);
		} catch (IllegalGameLawsException e) {
			vo.setSuccess(false);
			vo.setMsg("IllegalGameLawsException");
			return vo;
		} catch (NotVIPMemberException e) {
			int todayCreateVipRoomsCount = gameService.countTodayCreateVipRoomsCount(memberId);
			vo.setSuccess(false);
			vo.setMsg("NotVIPMemberException");
			data.put("todayCreateVipRoomsCount",
					NumConvertChineseUtil.toChinese(String.valueOf(todayCreateVipRoomsCount)));
			vo.setData(data);
			return vo;
		} catch (CanNotJoinMoreRoomsException e) {
			vo.setSuccess(false);
			vo.setMsg("CanNotJoinMoreRoomsException");
			return vo;
		} catch (NoServerAvailableForGameException e) {
			vo.setSuccess(false);
			vo.setMsg("NoServerAvailableForGameException");
			return vo;
		}

		WzskLawsFB fb = new WzskLawsFB(lawNames);
		// 普通会员每日开房（vip房）金币价格
		int gold = fb.payForCreateRoom();
		// 房主玩家记录
		List<PlayersRecord> playersRecord = new ArrayList<>();
		// 玩家记录存入gameRoom
		gameRoom.setPlayersRecord(playersRecord);
		PlayersRecord record = new PlayersRecord();
		record.setPlayerId(member.getId());
		record.setVip(member.isVip());
		record.setPayGold(gold);
		playersRecord.add(record);

		gameService.saveGameRoom(gameRoom);

		GameServer gameServer = gameRoom.getServerGame().getServer();
		// 游戏服务器rpc，需要手动httpclientrpc
		// 远程调用游戏服务器的newgame
		Request req = httpClient.newRequest(gameServer.getHttpUrl() + "/game/newgame");
		req.param("playerId", memberId);
		req.param("panshu", fb.getPanshu());
		req.param("renshu", fb.getRenshu());
		req.param("bx", fb.getBx());
		req.param("chapai", fb.getChapai());
		req.param("fapai", fb.getFapai());
		req.param("chaodi", fb.getChaodi());
		req.param("shuangming", fb.getShuangming());
		req.param("bxfd", fb.getBxfd());
		req.param("jxfd", fb.getJxfd());
		req.param("sxfd", fb.getSxfd());
		req.param("gxjb", fb.getGxjb());
		Map resData;
		try {
			ContentResponse res = req.send();
			String resJson = new String(res.getContent());
			CommonVO resVo = gson.fromJson(resJson, CommonVO.class);
			// 游戏服务器回传的参数带有gameId和token
			resData = (Map) resVo.getData();
			gameRoom.getServerGame().setGameId((String) resData.get("gameId"));
		} catch (Exception e) {
			vo.setSuccess(false);
			vo.setMsg("SysException");
			return vo;
		}

		// 普通会员开vip房扣金币,调用member系统中的方法
		if (!member.isVip() && gameRoom.isVip()) {
			CommonRemoteVO rvo = qipaiMembersRomoteService.gold_withdraw(memberId, gold, "pay for create room");
			if (!rvo.isSuccess()) {
				vo.setSuccess(false);
				vo.setMsg(rvo.getMsg());
				return vo;
			}
		}
		// 创建游戏房间的编号
		String roomNo = gameRoomCmdService.createRoom(memberId, System.currentTimeMillis());
		gameRoom.setNo(roomNo);

		// 将带roomNo的gameRoom写入数据库
		gameService.createGameRoom(gameRoom);
		// 发送房间创建消息
		roomManageMsgService.creatRoom(gameRoom);

		data.put("httpUrl", gameRoom.getServerGame().getServer().getHttpUrl());
		data.put("wsUrl", gameRoom.getServerGame().getServer().getWsUrl());
		data.put("roomNo", gameRoom.getNo());
		data.put("gameId", gameRoom.getServerGame().getGameId());
		// 存入了游戏服务器传回的token
		data.put("token", resData.get("token"));
		data.put("game", gameRoom.getGame());
		vo.setData(data);
		return vo;
	}

	/**
	 * 创建斗地主房间
	 */
	@RequestMapping(value = "/create_ddz_room")
	@ResponseBody
	public CommonVO createDdzRoom(String token, @RequestBody List<String> lawNames) {
		CommonVO vo = new CommonVO();
		String memberId = memberAuthService.getMemberIdBySessionId(token);
		if (memberId == null) {
			vo.setSuccess(false);
			vo.setMsg("invalid token");
			return vo;
		}
		// 根据memberId查询是否被限制
		MemberLoginLimitRecord loginLimitRecord = memberLoginLimitRecordService.findByMemberId(memberId, true);
		if (loginLimitRecord != null) {
			vo.setSuccess(false);
			vo.setMsg("login limited");
			return vo;
		}
		// 根据memberId查询到member
		Member member = memberService.findMember(memberId);
		// 得到member中的会员权益
		MemberRights rights = member.getRights();
		Map data = new HashMap();
		GameRoom gameRoom;
		try {
			gameRoom = gameService.buildDdzGameRoom(memberId, lawNames);
		} catch (IllegalGameLawsException e) {
			vo.setSuccess(false);
			vo.setMsg("IllegalGameLawsException");
			return vo;
		} catch (NotVIPMemberException e) {
			int todayCreateVipRoomsCount = gameService.countTodayCreateVipRoomsCount(memberId);
			vo.setSuccess(false);
			vo.setMsg("NotVIPMemberException");
			data.put("todayCreateVipRoomsCount",
					NumConvertChineseUtil.toChinese(String.valueOf(todayCreateVipRoomsCount)));
			vo.setData(data);
			return vo;
		} catch (CanNotJoinMoreRoomsException e) {
			vo.setSuccess(false);
			vo.setMsg("CanNotJoinMoreRoomsException");
			return vo;
		} catch (NoServerAvailableForGameException e) {
			vo.setSuccess(false);
			vo.setMsg("NoServerAvailableForGameException");
			return vo;
		}

		// 普通会员每日开房（vip房）金币价格
		int gold = rights.getPlanMemberCreateRoomDailyGoldPrice();
		// 房主玩家记录
		List<PlayersRecord> playersRecord = new ArrayList<>();
		// 玩家记录存入gameRoom
		gameRoom.setPlayersRecord(playersRecord);
		PlayersRecord record = new PlayersRecord();
		record.setPlayerId(member.getId());
		record.setVip(member.isVip());
		record.setPayGold(gold);
		playersRecord.add(record);

		gameService.saveGameRoom(gameRoom);

		GameServer gameServer = gameRoom.getServerGame().getServer();
		// 游戏服务器rpc，需要手动httpclientrpc
		DdzLawsFB fb = new DdzLawsFB(lawNames);
		// 远程调用游戏服务器的newgame
		Request req = httpClient.newRequest(gameServer.getHttpUrl() + "/game/newgame");
		req.param("playerId", memberId);
		req.param("panshu", fb.getPanshu());
		req.param("renshu", fb.getRenshu());
		req.param("difen", fb.getDifen());
		req.param("qxp", fb.getQxp());
		req.param("szfbxp", fb.getSzfbxp());
		Map resData;
		try {
			ContentResponse res = req.send();
			String resJson = new String(res.getContent());
			CommonVO resVo = gson.fromJson(resJson, CommonVO.class);
			// 游戏服务器回传的参数带有gameId和token
			resData = (Map) resVo.getData();
			gameRoom.getServerGame().setGameId((String) resData.get("gameId"));
		} catch (Exception e) {
			vo.setSuccess(false);
			vo.setMsg("SysException");
			return vo;
		}

		// 普通会员开vip房扣金币,调用member系统中的方法
		if (!member.isVip() && gameRoom.isVip()) {
			CommonRemoteVO rvo = qipaiMembersRomoteService.gold_withdraw(memberId, gold, "pay for create room");
			if (!rvo.isSuccess()) {
				vo.setSuccess(false);
				vo.setMsg(rvo.getMsg());
				return vo;
			}
		}
		// 创建游戏房间的编号
		String roomNo = gameRoomCmdService.createRoom(memberId, System.currentTimeMillis());
		gameRoom.setNo(roomNo);

		// 将带roomNo的gameRoom写入数据库
		gameService.createGameRoom(gameRoom);
		// 发送房间创建消息
		roomManageMsgService.creatRoom(gameRoom);

		data.put("httpUrl", gameRoom.getServerGame().getServer().getHttpUrl());
		data.put("wsUrl", gameRoom.getServerGame().getServer().getWsUrl());
		data.put("roomNo", gameRoom.getNo());
		data.put("gameId", gameRoom.getServerGame().getGameId());
		// 存入了游戏服务器传回的token
		data.put("token", resData.get("token"));
		data.put("game", gameRoom.getGame());
		vo.setData(data);
		return vo;
	}

	/**
	 * 创建大菠萝房间
	 */
	@RequestMapping(value = "/create_dbl_room")
	@ResponseBody
	public CommonVO createDblRoom(String token, @RequestBody List<String> lawNames) {
		CommonVO vo = new CommonVO();
		String memberId = memberAuthService.getMemberIdBySessionId(token);
		if (memberId == null) {
			vo.setSuccess(false);
			vo.setMsg("invalid token");
			return vo;
		}
		// 根据memberId查询是否被限制
		MemberLoginLimitRecord loginLimitRecord = memberLoginLimitRecordService.findByMemberId(memberId, true);
		if (loginLimitRecord != null) {
			vo.setSuccess(false);
			vo.setMsg("login limited");
			return vo;
		}
		// 根据memberId查询到member
		Member member = memberService.findMember(memberId);
		// 得到member中的会员权益
		MemberRights rights = member.getRights();
		Map data = new HashMap();
		GameRoom gameRoom;
		try {
			gameRoom = gameService.buildDblGameRoom(memberId, lawNames);
		} catch (IllegalGameLawsException e) {
			vo.setSuccess(false);
			vo.setMsg("IllegalGameLawsException");
			return vo;
		} catch (NotVIPMemberException e) {
			int todayCreateVipRoomsCount = gameService.countTodayCreateVipRoomsCount(memberId);
			vo.setSuccess(false);
			vo.setMsg("NotVIPMemberException");
			data.put("todayCreateVipRoomsCount",
					NumConvertChineseUtil.toChinese(String.valueOf(todayCreateVipRoomsCount)));
			vo.setData(data);
			return vo;
		} catch (CanNotJoinMoreRoomsException e) {
			vo.setSuccess(false);
			vo.setMsg("CanNotJoinMoreRoomsException");
			return vo;
		} catch (NoServerAvailableForGameException e) {
			vo.setSuccess(false);
			vo.setMsg("NoServerAvailableForGameException");
			return vo;
		}

		// 普通会员每日开房（vip房）金币价格
		int gold = rights.getPlanMemberCreateRoomDailyGoldPrice();
		// 房主玩家记录
		List<PlayersRecord> playersRecord = new ArrayList<>();
		// 玩家记录存入gameRoom
		gameRoom.setPlayersRecord(playersRecord);
		PlayersRecord record = new PlayersRecord();
		record.setPlayerId(member.getId());
		record.setVip(member.isVip());
		record.setPayGold(gold);
		playersRecord.add(record);

		gameService.saveGameRoom(gameRoom);

		GameServer gameServer = gameRoom.getServerGame().getServer();
		// 游戏服务器rpc，需要手动httpclientrpc
		DblLawsFB fb = new DblLawsFB(lawNames);
		// 远程调用游戏服务器的newgame
		Request req = httpClient.newRequest(gameServer.getHttpUrl() + "/game/newgame");
		req.param("playerId", memberId);
		req.param("panshu", fb.getPanshu());
		req.param("renshu", fb.getRenshu());
		req.param("dqef", fb.getDqef());
		req.param("dqsf", fb.getDqsf());
		req.param("bx", fb.getBx());
		req.param("bihuase", fb.getBihuase());
		req.param("zidongzupai", fb.getZidongzupai());
		req.param("yitiaolong", fb.getYitiaolong());
		Map resData;
		try {
			ContentResponse res = req.send();
			String resJson = new String(res.getContent());
			CommonVO resVo = gson.fromJson(resJson, CommonVO.class);
			// 游戏服务器回传的参数带有gameId和token
			resData = (Map) resVo.getData();
			gameRoom.getServerGame().setGameId((String) resData.get("gameId"));
		} catch (Exception e) {
			vo.setSuccess(false);
			vo.setMsg("SysException");
			return vo;
		}

		// 普通会员开vip房扣金币,调用member系统中的方法
		if (!member.isVip() && gameRoom.isVip()) {
			CommonRemoteVO rvo = qipaiMembersRomoteService.gold_withdraw(memberId, gold, "pay for create room");
			if (!rvo.isSuccess()) {
				vo.setSuccess(false);
				vo.setMsg(rvo.getMsg());
				return vo;
			}
		}
		// 创建游戏房间的编号
		String roomNo = gameRoomCmdService.createRoom(memberId, System.currentTimeMillis());
		gameRoom.setNo(roomNo);

		// 将带roomNo的gameRoom写入数据库
		gameService.createGameRoom(gameRoom);
		// 发送房间创建消息
		roomManageMsgService.creatRoom(gameRoom);

		data.put("httpUrl", gameRoom.getServerGame().getServer().getHttpUrl());
		data.put("wsUrl", gameRoom.getServerGame().getServer().getWsUrl());
		data.put("roomNo", gameRoom.getNo());
		data.put("gameId", gameRoom.getServerGame().getGameId());
		// 存入了游戏服务器传回的token
		data.put("token", resData.get("token"));
		data.put("game", gameRoom.getGame());
		vo.setData(data);
		return vo;
	}

	/**
	 * 创建跑得快房间
	 */
	@RequestMapping(value = "/create_pdk_room")
	@ResponseBody
	public CommonVO createPdkRoom(String token, @RequestBody List<String> lawNames) {
		String memberId = memberAuthService.getMemberIdBySessionId(token);
		if (memberId == null) {
			return CommonVoUtil.error("invalid token");
		}
		// 根据memberId查询是否被限制
		MemberLoginLimitRecord loginLimitRecord = memberLoginLimitRecordService.findByMemberId(memberId, true);
		if (loginLimitRecord != null) {
			return CommonVoUtil.error("login limited");
		}

		// 根据memberId查询到member
		Member member = memberService.findMember(memberId);
		// 得到member中的会员权益
		MemberRights rights = member.getRights();
		Map data = new HashMap();
		GameRoom gameRoom;
		try {
			gameRoom = gameService.buildPdkGameRoom(memberId, lawNames);
		} catch (IllegalGameLawsException e) {
			return CommonVoUtil.error(e.getClass().getName());
		} catch (NotVIPMemberException e) {
			int todayCreateVipRoomsCount = gameService.countTodayCreateVipRoomsCount(memberId);
			return CommonVoUtil.error(e.getClass().getName() + "-todayCreateVipRoomsCount:"
					+ NumConvertChineseUtil.toChinese(String.valueOf(todayCreateVipRoomsCount)));
		} catch (CanNotJoinMoreRoomsException e) {
			return CommonVoUtil.error("CanNotJoinMoreRoomsException");
		} catch (NoServerAvailableForGameException e) {
			return CommonVoUtil.error(e.getClass().getName());
		}

		// 普通会员每日开房（vip房）金币价格
		int gold = rights.getPlanMemberCreateRoomDailyGoldPrice();
		// 房主玩家记录
		List<PlayersRecord> playersRecord = new ArrayList<>();
		// 玩家记录存入gameRoom
		gameRoom.setPlayersRecord(playersRecord);
		PlayersRecord record = new PlayersRecord();
		record.setPlayerId(member.getId());
		record.setVip(member.isVip());
		record.setPayGold(gold);
		playersRecord.add(record);

		gameService.saveGameRoom(gameRoom);

		GameServer gameServer = gameRoom.getServerGame().getServer();
		// 游戏服务器rpc，需要手动httpclientrpc
		PdkLawsFB fb = new PdkLawsFB(lawNames);
		// 远程调用游戏服务器的newgame
		Request req = httpClient.newRequest(gameServer.getHttpUrl() + "/game/newgame");
		req.param("playerId", memberId);
		req.param("panshu", fb.getPanshu());
		req.param("renshu", fb.getRenshu());

		req.param("bichu", fb.getBichu());
		req.param("biya", fb.getBiya());
		req.param("aBoom", fb.getaBoom());
		req.param("sandaique", fb.getSandaique());
		req.param("feijique", fb.getFeijique());
		req.param("showShoupaiNum", fb.getShowShoupaiNum());
		req.param("zhuaniao", fb.getZhuaniao());
		Map resData;
		try {
			ContentResponse res = req.send();
			String resJson = new String(res.getContent());
			CommonVO resVo = gson.fromJson(resJson, CommonVO.class);
			// 游戏服务器回传的参数带有gameId和token
			resData = (Map) resVo.getData();
			gameRoom.getServerGame().setGameId((String) resData.get("gameId"));
		} catch (Exception e) {
			return CommonVoUtil.error("SysException");
		}

		// 普通会员开vip房扣金币,调用member系统中的方法
		if (!member.isVip() && gameRoom.isVip()) {
			CommonRemoteVO rvo = qipaiMembersRomoteService.gold_withdraw(memberId, gold, "pay for create room");
			if (!rvo.isSuccess()) {
				return CommonVoUtil.error(rvo.getMsg());
			}
		}
		// 创建游戏房间的编号
		String roomNo = gameRoomCmdService.createRoom(memberId, System.currentTimeMillis());
		gameRoom.setNo(roomNo);

		// 将带roomNo的gameRoom写入数据库
		gameService.createGameRoom(gameRoom);
		// 发送房间创建消息
		roomManageMsgService.creatRoom(gameRoom);

		data.put("httpUrl", gameRoom.getServerGame().getServer().getHttpUrl());
		data.put("wsUrl", gameRoom.getServerGame().getServer().getWsUrl());
		data.put("roomNo", gameRoom.getNo());
		data.put("gameId", gameRoom.getServerGame().getGameId());
		// 存入了游戏服务器传回的token
		data.put("token", resData.get("token"));
		data.put("game", gameRoom.getGame());
		return CommonVoUtil.success(data, "creat paodekuai success");
	}

	/**
	 * 创建茶苑双扣房间
	 */
	@RequestMapping(value = "/create_cysk_room")
	@ResponseBody
	public CommonVO createCyskRoom(String token, @RequestBody List<String> lawNames) {
		CommonVO vo = new CommonVO();
		// 根据token从UserSessionsManager的Map<String, UserSession>
		// idSessionMap拿到memberId(memberDbo表)
		String memberId = memberAuthService.getMemberIdBySessionId(token);
		if (memberId == null) {
			vo.setSuccess(false);
			vo.setMsg("invalid token");
			return vo;
		}
		// 根据memberId查询是否被限制
		MemberLoginLimitRecord loginLimitRecord = memberLoginLimitRecordService.findByMemberId(memberId, true);
		if (loginLimitRecord != null) {
			vo.setSuccess(false);
			vo.setMsg("login limited");
			return vo;
		}
		// 根据memberId查询到member
		Member member = memberService.findMember(memberId);
		MemberRights rights = member.getRights();
		// 得到member中的会员权益
		Map data = new HashMap();
		GameRoom gameRoom;
		try {
			gameRoom = gameService.buildCyskGameRoom(memberId, lawNames);
		} catch (IllegalGameLawsException e) {
			vo.setSuccess(false);
			vo.setMsg("IllegalGameLawsException");
			return vo;
		} catch (NotVIPMemberException e) {
			int todayCreateVipRoomsCount = gameService.countTodayCreateVipRoomsCount(memberId);
			vo.setSuccess(false);
			vo.setMsg("NotVIPMemberException");
			data.put("todayCreateVipRoomsCount",
					NumConvertChineseUtil.toChinese(String.valueOf(todayCreateVipRoomsCount)));
			vo.setData(data);
			return vo;
		} catch (CanNotJoinMoreRoomsException e) {
			vo.setSuccess(false);
			vo.setMsg("CanNotJoinMoreRoomsException");
			return vo;
		} catch (NoServerAvailableForGameException e) {
			vo.setSuccess(false);
			vo.setMsg("NoServerAvailableForGameException");
			return vo;
		}

		CyskLawsFB fb = new CyskLawsFB(lawNames);
		// 普通会员每日开房（vip房）金币价格
		int gold = rights.getPlanMemberCreateRoomDailyGoldPrice();
		// 房主玩家记录
		List<PlayersRecord> playersRecord = new ArrayList<>();
		// 玩家记录存入gameRoom
		gameRoom.setPlayersRecord(playersRecord);
		PlayersRecord record = new PlayersRecord();
		record.setPlayerId(member.getId());
		record.setVip(member.isVip());
		record.setPayGold(gold);
		playersRecord.add(record);

		gameService.saveGameRoom(gameRoom);

		GameServer gameServer = gameRoom.getServerGame().getServer();
		// 游戏服务器rpc，需要手动httpclientrpc
		// 远程调用游戏服务器的newgame
		Request req = httpClient.newRequest(gameServer.getHttpUrl() + "/game/newgame");
		req.param("playerId", memberId);
		req.param("panshu", fb.getPanshu());
		req.param("renshu", fb.getRenshu());
		req.param("bx", fb.getBx());
		req.param("fapai", fb.getFapai());
		req.param("shuangming", fb.getShuangming());
		Map resData;
		try {
			ContentResponse res = req.send();
			String resJson = new String(res.getContent());
			CommonVO resVo = gson.fromJson(resJson, CommonVO.class);
			// 游戏服务器回传的参数带有gameId和token
			resData = (Map) resVo.getData();
			gameRoom.getServerGame().setGameId((String) resData.get("gameId"));
		} catch (Exception e) {
			vo.setSuccess(false);
			vo.setMsg("SysException");
			return vo;
		}

		// 普通会员开vip房扣金币,调用member系统中的方法
		if (!member.isVip() && gameRoom.isVip()) {
			CommonRemoteVO rvo = qipaiMembersRomoteService.gold_withdraw(memberId, gold, "pay for create room");
			if (!rvo.isSuccess()) {
				vo.setSuccess(false);
				vo.setMsg(rvo.getMsg());
				return vo;
			}
		}
		// 创建游戏房间的编号
		String roomNo = gameRoomCmdService.createRoom(memberId, System.currentTimeMillis());
		gameRoom.setNo(roomNo);

		// 将带roomNo的gameRoom写入数据库
		gameService.createGameRoom(gameRoom);
		// 发送房间创建消息
		roomManageMsgService.creatRoom(gameRoom);

		data.put("httpUrl", gameRoom.getServerGame().getServer().getHttpUrl());
		data.put("wsUrl", gameRoom.getServerGame().getServer().getWsUrl());
		data.put("roomNo", gameRoom.getNo());
		data.put("gameId", gameRoom.getServerGame().getGameId());
		// 存入了游戏服务器传回的token
		data.put("token", resData.get("token"));
		data.put("game", gameRoom.getGame());
		vo.setData(data);
		return vo;
	}

	/**
	 * 加入房间。如果加入的是自己暂时离开的房间，那么就变成返回房间
	 */
	@RequestMapping(value = "/join_room")
	@ResponseBody
	public CommonVO joinRoom(String token, String roomNo) {
		CommonVO vo = new CommonVO();
		String memberId = memberAuthService.getMemberIdBySessionId(token);
		if (memberId == null) {
			vo.setSuccess(false);
			vo.setMsg("invalid token");
			return vo;
		}
		MemberLoginLimitRecord loginLimitRecord = memberLoginLimitRecordService.findByMemberId(memberId, true);
		if (loginLimitRecord != null) {
			vo.setSuccess(false);
			vo.setMsg("login limited");
			return vo;
		}
		Member member = memberService.findMember(memberId);

		GameRoom gameRoom = gameService.findRoomOpen(roomNo);
		if (gameRoom == null) {
			vo.setSuccess(false);
			vo.setMsg("invalid room number");
			return vo;
		}
		String serverGameId = gameRoom.getServerGame().getGameId();

		// 处理如果是自己暂时离开的房间
		MemberGameRoom memberGameRoom = gameService.findMemberGameRoom(memberId, gameRoom.getId());
		if (memberGameRoom != null) {
			// 游戏服务器rpc返回房间
			GameServer gameServer = gameRoom.getServerGame().getServer();
			Request req = httpClient.newRequest(gameServer.getHttpUrl() + "/game/backtogame");
			req.param("playerId", memberId);
			req.param("gameId", serverGameId);
			Map resData;
			try {
				ContentResponse res = req.send();
				String resJson = new String(res.getContent());
				CommonVO resVo = gson.fromJson(resJson, CommonVO.class);
				if (resVo.isSuccess()) {
					resData = (Map) resVo.getData();
				} else {
					vo.setSuccess(false);
					vo.setMsg(resVo.getMsg());
					return vo;
				}
			} catch (Exception e) {
				vo.setSuccess(false);
				vo.setMsg("SysException");
				return vo;
			}

			Map data = new HashMap();
			data.put("httpUrl", gameRoom.getServerGame().getServer().getHttpUrl());
			data.put("wsUrl", gameRoom.getServerGame().getServer().getWsUrl());
			data.put("roomNo", gameRoom.getNo());
			data.put("token", resData.get("token"));
			data.put("gameId", serverGameId);
			data.put("game", gameRoom.getGame());
			vo.setData(data);
			return vo;
		}

		try {
			gameService.tryHasMoreRoom(memberId);
		} catch (CanNotJoinMoreRoomsException e) {
			vo.setSuccess(false);
			vo.setMsg("CanNotJoinMoreRoomsException");
			return vo;
		}

		// 判断普通会员个人账户的余额能否支付加入房间的费用
		MemberGoldBalance memberGoldBalance = memberGoldBalanceService.findByMemberId(memberId);
		if (memberGoldBalance == null) {
			vo.setSuccess(false);
			vo.setMsg("InsufficientBalanceException");
			return vo;
		}
		int gold = 100;
		List<String> lawNames = new ArrayList<>();
		List<GameLaw> laws = gameRoom.getLaws();
		// 构建list laws
		laws.forEach((law) -> lawNames.add(law.getName()));
		if (gameRoom.getGame().equals(Game.wenzhouShuangkou)) {
			gold = new WzskLawsFB(lawNames).payForJoinRoom();
		} else if (gameRoom.getGame().equals(Game.ruianMajiang)) {
			gold = new RamjLawsFB(lawNames).payForJoinRoom();
		}
		int balance = memberGoldBalance.getBalanceAfter();
		if (gameRoom.isVip() && !member.isVip() && balance < gold) {
			vo.setSuccess(false);
			vo.setMsg("InsufficientBalanceException");
			return vo;
		}

		// 游戏服务器rpc加入房间
		GameServer gameServer = gameRoom.getServerGame().getServer();
		Request req = httpClient.newRequest(gameServer.getHttpUrl() + "/game/joingame");
		req.param("playerId", memberId);
		req.param("gameId", serverGameId);
		Map resData;
		try {
			ContentResponse res = req.send();
			String resJson = new String(res.getContent());
			CommonVO resVo = gson.fromJson(resJson, CommonVO.class);
			if (resVo.isSuccess()) {
				resData = (Map) resVo.getData();
			} else {
				vo.setSuccess(false);
				vo.setMsg(resVo.getMsg());
				return vo;
			}
		} catch (Exception e) {
			vo.setSuccess(false);
			vo.setMsg("SysException");
			return vo;
		}

		// 加入房间玩家记录,列表从第一开始，第0个是房主
		List<PlayersRecord> playersRecord = gameRoom.getPlayersRecord();
		PlayersRecord record = new PlayersRecord();
		record.setPlayerId(member.getId());
		record.setVip(member.isVip());
		record.setPayGold(gold);
		playersRecord.add(record);
		gameService.saveGameRoom(gameRoom);
		// 普通会员加入vip房完成玉石扣除才能加入房间
		if (gameRoom.isVip() && !member.isVip()) {
			CommonRemoteVO rvo = qipaiMembersRomoteService.gold_withdraw(memberId, gold, "pay for join room");
			if (!rvo.isSuccess()) {
				vo.setSuccess(false);
				vo.setMsg(rvo.getMsg());
				return vo;
			}
		}

		gameService.joinGameRoom(gameRoom, memberId);
		// 发送房间更新消息
		roomManageMsgService.updatePlayer(gameRoom);

		Map data = new HashMap();
		data.put("httpUrl", gameRoom.getServerGame().getServer().getHttpUrl());
		data.put("wsUrl", gameRoom.getServerGame().getServer().getWsUrl());
		data.put("roomNo", gameRoom.getNo());
		data.put("token", resData.get("token"));
		data.put("gameId", serverGameId);
		data.put("game", gameRoom.getGame());
		vo.setData(data);
		return vo;

	}

	/**
	 * 游戏服务器上线
	 * 
	 * @param gameServer
	 * @return
	 */
	@RequestMapping(value = "/game_server_online")
	@ResponseBody
	public CommonVO gameserveronline(@RequestBody GameServer gameServer) {
		CommonVO vo = new CommonVO();
		gameService.onlineServer(gameServer);
		gameServerMsgService.gameServerOnline(gameServer);
		return vo;
	}

	/**
	 * 游戏服务器下线
	 *
	 * @return
	 */
	@RequestMapping(value = "/game_server_offline")
	@ResponseBody
	public CommonVO gameserveroffline(@RequestBody String[] gameServerIds) {
		CommonVO vo = new CommonVO();
		gameService.offlineServer(gameServerIds);
		gameServerMsgService.gameServerOffline(gameServerIds);
		return vo;
	}

	/**
	 * 添加玩法
	 *
	 * @return
	 */
	@RequestMapping(value = "/add_law")
	@ResponseBody
	public CommonVO addlaw(@RequestBody GameLaw law) {
		CommonVO vo = new CommonVO();
		gameService.createGameLaw(law);
		gameServerMsgService.createGameLaw(law);
		return vo;
	}

	/**
	 * 删除玩法
	 * 
	 * @param lawId
	 * @return
	 */
	@RequestMapping(value = "/remove_law")
	@ResponseBody
	public CommonVO removelaw(String lawId) {
		CommonVO vo = new CommonVO();
		gameService.removeGameLaw(lawId);
		gameServerMsgService.removeGameLaw(lawId);
		return vo;
	}

	/**
	 * 编辑玩法
	 *
	 * @return
	 */
	@RequestMapping(value = "/update_law")
	@ResponseBody
	public CommonVO updatelaw(@RequestBody GameLaw law) {
		CommonVO vo = new CommonVO();
		gameService.createGameLaw(law);
		gameServerMsgService.updateGameLaw(law);
		return vo;
	}

	/**
	 * 添加玩法互斥组
	 *
	 * @return
	 */
	@RequestMapping(value = "/add_mutexgroup")
	@ResponseBody
	public CommonVO addmutexgroup(@RequestBody LawsMutexGroup lawsMutexGroup) {
		CommonVO vo = new CommonVO();
		gameService.addLawsMutexGroup(lawsMutexGroup);
		gameServerMsgService.addLawsMutexGroup(lawsMutexGroup);
		return vo;
	}

	/**
	 * 删除玩法互斥组
	 *
	 * @return
	 */
	@RequestMapping(value = "/remove_mutexgroup")
	@ResponseBody
	public CommonVO removemutexgroup(String groupId) {
		CommonVO vo = new CommonVO();
		gameService.removeLawsMutexGroup(groupId);
		gameServerMsgService.removeLawsMutexGroup(groupId);
		return vo;
	}

	/**
	 * 查询玩家当前正在游戏的房间
	 * 
	 * @param token
	 * @return
	 */
	@RequestMapping(value = "/query_membergameroom")
	public CommonVO queryMemberGameRoom(String token) {
		CommonVO vo = new CommonVO();
		String memberId = memberAuthService.getMemberIdBySessionId(token);
		if (memberId == null) {
			vo.setSuccess(false);
			vo.setMsg("invalid token");
			return vo;
		}
		List<MemberGameRoomVO> roomList = new ArrayList<>();
		List<MemberGameRoom> rooms = gameService.queryMemberGameRoomForMember(memberId);
		// List<MemberGameRoom> roomList =
		// gameService.queryMemberGameRoomForMember(memberId);
		for (MemberGameRoom room : rooms) {
			GameRoom gameRoom = room.getGameRoom();
			MemberGameRoomVO roomVo = new MemberGameRoomVO(gameRoom.getNo(), gameRoom.getGame(),
					gameRoom.getPlayersCount(), gameRoom.getCurrentPanNum(), gameRoom.getPanCountPerJu(),
					gameRoom.getDeadlineTime());
			roomList.add(roomVo);
		}
		vo.setMsg("room list");
		vo.setData(roomList);
		return vo;
	}

	/**
	 * 后台rpc查询会员游戏房间
	 * 
	 * @return
	 */
	@RequestMapping(value = "/query_memberplayingroom")
	public CommonVO queryMemberPlayingRoom(String memberId) {
		CommonVO vo = new CommonVO();
		List<MemberGameRoom> roomList = gameService.queryMemberGameRoomForMember(memberId);
		List<MemberPlayingRoomVO> gameRoomList = new ArrayList<>();
		roomList.forEach((memberGameRoom) -> {
			gameRoomList.add(new MemberPlayingRoomVO(memberGameRoom.getGameRoom()));
		});
		vo.setMsg("room list");
		vo.setData(gameRoomList);
		return vo;
	}

	/**
	 * 房间到时定时器，每小时
	 */
	@Scheduled(cron = "0 0 0/1 * * ?")
	public void removeGameRoom() {
		long deadlineTime = System.currentTimeMillis();
		List<GameRoom> roomList = gameService.findExpireGameRoom(deadlineTime);
		Map<Game, List<String>> gameIdMap = new HashMap<>();
		for (Game game : Game.values()) {
			gameIdMap.put(game, new ArrayList<>());
		}
		for (GameRoom room : roomList) {
			Game game = room.getGame();
			String serverGameId = room.getServerGame().getGameId();
			gameIdMap.get(game).add(serverGameId);
		}
		ruianGameRoomMsgService.removeGameRoom(gameIdMap.get(Game.ruianMajiang));
		fangpaoGameRoomMsgService.removeGameRoom(gameIdMap.get(Game.fangpaoMajiang));
		wenzhouGameRoomMsgService.removeGameRoom(gameIdMap.get(Game.wenzhouMajiang));
		dianpaoGameRoomMsgService.removeGameRoom(gameIdMap.get(Game.dianpaoMajiang));
		wenzhouShuangkouGameRoomMsgService.removeGameRoom(gameIdMap.get(Game.wenzhouShuangkou));
		doudizhuGameRoomMsgService.removeGameRoom(gameIdMap.get(Game.doudizhu));
		paodekuaiGameRoomMsgService.removeGameRoom(gameIdMap.get(Game.paodekuai));
		daboluoGameRoomMsgService.removeGameRoom(gameIdMap.get(Game.daboluo));
		chayuanShuangkouGameRoomMsgService.removeGameRoom(gameIdMap.get(Game.chayuanShuangkou));
	}

	/**
	 * 加入观战
	 */
	@RequestMapping(value = "/joinWatch")
	@ResponseBody
	public CommonVO joinWatch(String token, String roomNo) {
		CommonVO vo = new CommonVO();
		String memberId = memberAuthService.getMemberIdBySessionId(token);
		if (memberId == null) {
			vo.setSuccess(false);
			vo.setMsg("invalid token");
			return vo;
		}
		MemberLoginLimitRecord loginLimitRecord = memberLoginLimitRecordService.findByMemberId(memberId, true);
		if (loginLimitRecord != null) {
			vo.setSuccess(false);
			vo.setMsg("login limited");
			return vo;
		}

		GameRoom gameRoom = gameService.findRoomOpen(roomNo);
		if (gameRoom == null) {
			vo.setSuccess(false);
			vo.setMsg("invalid room number");
			return vo;
		}
		String serverGameId = gameRoom.getServerGame().getGameId();
		GameServer gameServer = gameRoom.getServerGame().getServer();

		Request req = httpClient.newRequest(gameServer.getHttpUrl() + "/game/joinwatch");
		req.param("playerId", memberId);
		req.param("gameId", serverGameId);
		Map resData;
		try {
			ContentResponse res = req.send();
			String resJson = new String(res.getContent());
			CommonVO resVo = gson.fromJson(resJson, CommonVO.class);
			if (resVo.isSuccess()) {
				resData = (Map) resVo.getData();
			} else {
				vo.setSuccess(false);
				vo.setMsg(resVo.getMsg());
				return vo;
			}
		} catch (Exception e) {
			vo.setSuccess(false);
			vo.setMsg("SysException");
			return vo;
		}
		Map data = new HashMap();
		data.put("httpUrl", gameRoom.getServerGame().getServer().getHttpUrl());
		data.put("wsUrl", gameRoom.getServerGame().getServer().getWsUrl());
		data.put("roomNo", gameRoom.getNo());
		data.put("token", resData.get("token"));
		data.put("gameId", serverGameId);
		data.put("game", gameRoom.getGame());
		vo.setData(data);
		return vo;
	}
}
