package com.anbang.qipai.game.plan.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import com.anbang.qipai.game.plan.bean.mail.MailState;
import com.anbang.qipai.game.plan.bean.mail.SystemMail;
import com.anbang.qipai.game.plan.bean.mail.SystemMailState;
import com.anbang.qipai.game.plan.bean.members.Member;
import com.anbang.qipai.game.plan.dao.MailDao;
import com.anbang.qipai.game.util.TimeUtil;
import com.anbang.qipai.game.web.vo.CommonVO;

@Component
public class MailService {
	
	private static Logger logger = LoggerFactory.getLogger(MailService.class);
	
	@Autowired
	private MailDao maildao;
	/**添加系统邮件
	 * @param mail 邮件信息
	 * **/
	public SystemMail addmail(SystemMail mail) {
		return maildao.addmail(mail);
		
	}
	/**分页查询并给所有会员添加记录
	 * @param mailid 哪封邮件的id
	 * **/
	public void pagingfind(String mailid){
		SystemMail systemmail = maildao.findmailById(mailid);
		Integer page = 1;
		Integer size = 10000;
		Query query = new Query();
		Long count = maildao.count(query, size);
		for(Integer i = 1;i<=count;i++) {//数据量太大，分页实现集合查询
			Pageable pageable= new PageRequest(page-1, size);
			List<Member> list = maildao.pagingfind(query,pageable);
			for (Member member : list) {//分页查询后的会员添加邮件记录
				MailState mailstate = new MailState();
				mailstate.setMailid(systemmail.getId());
				mailstate.setMemberid(member.getId());
				if(systemmail.getNumber() == 0 && systemmail.getIntegral() == 0 && systemmail.getVipcard() == 0) {
					mailstate.setReceive("2");
				}else {
					mailstate.setReceive("1");
				}
				mailstate.setStatemail("1");
				mailstate.setDeletestate("1");
				maildao.addmailstate(mailstate);
			}
			page++;
		}
	}
	
	/**用户点击单个邮件，查看详情
	 * @param memberid 会员id
	 * @param mailid 邮件id
	 * **/
	public Map<String,Object> findonemail(String memberid,String mailid){
		Map<String,Object> map = new HashMap<String,Object>();
		MailState mailstates = maildao.findmembermail(memberid, mailid);
		mailstates.setStatemail("0");
		maildao.updatemembermail(mailstates);
		MailState mailstate = maildao.findmembermail(memberid, mailid);
		SystemMail systemmail = maildao.findmailById(mailid);
		map.put("mailstate", mailstate);
		map.put("systemmail", systemmail);
		return map;
	}
	
	/**用户查看本身邮件
	 * @param memberid 会员id
	 * @throws ParseException 
	 * **/
	public Map<String,Object> findall(String memberid) throws ParseException{
		Map<String,Object> map = new HashMap<String,Object>();
		 Member member = maildao.findMemberById(memberid);
		 long newtime = TimeUtil.creducedate(member.getCreateTime(), 20);
		 List<SystemMailState> lists = new ArrayList<>();
		 List<SystemMailState> wdwl = new ArrayList<>();
		 List<SystemMailState> wd = new ArrayList<>();
		 List<SystemMailState> ydwl = new ArrayList<>();
		 List<SystemMailState> ydyl = new ArrayList<>();
		 List<SystemMailState> yl = new ArrayList<>();
		 List<MailState> list = maildao.findall(memberid);
		 for (MailState mailState1 : list) {
			 if(mailState1.getDeletestate().equals("1")) {
				 SystemMail sys = maildao.findByIdtime(mailState1.getMailid(), newtime);
				 SystemMailState systemMailState = new SystemMailState();
				 if(sys != null) {
					 systemMailState.setStatemail(mailState1.getStatemail());
					 systemMailState.setReceive(mailState1.getReceive());
					 if(mailState1.getStatemail().equals("1") && mailState1.getReceive().equals("1")) {
						 systemMailState.setSystemMail(sys);
						 wdwl.add(systemMailState);
					 }else if(mailState1.getStatemail().equals("1") && mailState1.getReceive().equals("2") || mailState1.getReceive().equals("0")) {
						 systemMailState.setSystemMail(sys);
						 wd.add(systemMailState);
					 }else if(mailState1.getStatemail().equals("0") && mailState1.getReceive().equals("1")) {
						 systemMailState.setSystemMail(sys);
						 ydwl.add(systemMailState);
					 }else if(mailState1.getStatemail().equals("0") && mailState1.getReceive().equals("0")) {
						 systemMailState.setSystemMail(sys);
						 ydyl.add(systemMailState);
					 }else if(mailState1.getStatemail().equals("0") && mailState1.getReceive().equals("2")) {
						 systemMailState.setSystemMail(sys);
						 yl.add(systemMailState);
					 }
				 }
				 }
			 	}
		 lists.addAll(wdwl);
		 lists.addAll(wd);
		 lists.addAll(ydwl);
		 lists.addAll(ydyl);
		 lists.addAll(yl);
		 
		 map.put("lists",lists);
	    return map;
	}
	
	
	/**查询有多少未读未领的，小红点个数
	 * 
	public Integer redmailcount(String memberid) {
		Integer count = 0;
		List<MailState> list = maildao.findall(memberid);
		for (MailState mailState : list) {
			if(mailState.getStatemail().equals("1") || mailState.getReceive().equals("1") ) {
				count++;
			}
		}
		return count;
	}
	 **/
	/**用户点开邮件，改变邮件状态
	 *@param memberid 会员id
	 *@param mailid 邮件id
	 *@param receive 是否领取
	 * **/
	public CommonVO changestate(String memberid,String mailid) {
		CommonVO vo = new CommonVO();
		MailState mailstate = maildao.findmembermail(memberid, mailid);
		mailstate.setStatemail("0");
		mailstate.setReceive("0");
		maildao.updatemembermail(mailstate);
		return vo;
	}
	
	/**把一个用户的所有邮件设为已读
	 * **/
	public void findallmembermail(String memberid) {
		List<MailState> list = maildao.findallmembermail(memberid);
		for (MailState mailState : list) {//循环把这个会员的所有邮件设为已读
			mailState.setStatemail("0");
			maildao.addmailstate(mailState);
		}
	}
	
	/**删除所有已读
	 * @param memberid 会员id
	 * **/
	public void deleteallmail(String memberid) {
		List<MailState> list = maildao.findallmembermail(memberid);
		for (MailState mailState : list) {
			if(mailState.getStatemail().equals("0") && mailState.getReceive().equals("0") || mailState.getReceive().equals("2")) {
				mailState.setDeletestate("0");
			}
			maildao.addmailstate(mailState);
		}
	}
	
}
