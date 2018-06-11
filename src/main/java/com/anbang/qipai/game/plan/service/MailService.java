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
import com.anbang.qipai.game.plan.bean.mail.TrackPoint;
import com.anbang.qipai.game.plan.bean.members.Member;
import com.anbang.qipai.game.plan.dao.MailDao;
import com.anbang.qipai.game.plan.date.ConversionDate;
import com.anbang.qipai.game.web.vo.CommonVO;

@Component
public class MailService {
	
	private static Logger logger = LoggerFactory.getLogger(MailService.class);
	
	@Autowired
	private ConversionDate conversionDate;
	
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
				logger.info("id:"+member.getId()+i+page);
				MailState mailstate = new MailState();
				mailstate.setMailid(mailid);
				mailstate.setMemberid(member.getId());
				if(systemmail.getNumber() == 0 && systemmail.getIntegral() == 0 && systemmail.getVipcard() == 0) {
					mailstate.setReceive("2");
				}else {
					mailstate.setReceive("1");
				}
				logger.info("奖励是否为空："+systemmail.getNumber()+systemmail.getVipcard()+systemmail.getIntegral());
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
		 long newtime = conversionDate.cdate(member.getCreateTime(), 20);
		 List<SystemMail> lists = new ArrayList<>();
		 List<SystemMail> wdwl = new ArrayList<>();
		 List<SystemMail> wd = new ArrayList<>();
		 List<SystemMail> ydwl = new ArrayList<>();
		 List<SystemMail> ydyl = new ArrayList<>();
		 List<SystemMail> yl = new ArrayList<>();
		 List<MailState> list = maildao.findall(memberid);
		 for (MailState mailState1 : list) {
			 SystemMail sys = maildao.findByIdtime(mailState1.getMailid(), newtime);
			 if(sys != null) {
				 if(mailState1.getStatemail().equals("1") && mailState1.getReceive().equals("1")) {
					 wdwl.add(sys);
				 }else if(mailState1.getStatemail().equals("1") && mailState1.getReceive().equals("2")) {
					 wd.add(sys);
				 }else if(mailState1.getStatemail().equals("0") && mailState1.getReceive().equals("1")) {
					 ydwl.add(sys);
				 }else if(mailState1.getStatemail().equals("0") && mailState1.getReceive().equals("0")) {
					 ydyl.add(sys);
				 }else if(mailState1.getStatemail().equals("0") && mailState1.getReceive().equals("2")) {
					 yl.add(sys);
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
	
	/**邮件小红点
	 * @param memberid 会员id
	 * **/
	public Map<String,Object> redmail(String memberid){
		TrackPoint tr = new TrackPoint();
		Map<String,Object> map = new HashMap<>();
		 List<MailState> list = maildao.findall(memberid);
		 for (MailState mailState : list) {
			 if(mailState.getStatemail().equals("1")) {
				 tr.setMemberid(memberid);
				 tr.setState(true);
				 break;
			 }else {
				 tr.setMemberid(memberid);
				 tr.setState(false);
			 }
		}
		 map.put("track",tr);
		 return map;
		
	}
	
	/**用户点开邮件，改变邮件状态
	 *@param memberid 会员id
	 *@param mailid 邮件id
	 *@param receive 是否领取
	 * **/
	public CommonVO changestate(String memberid,String mailid,String receive) {
		CommonVO vo = new CommonVO();
		MailState mailstate = maildao.findmembermail(memberid, mailid);
		mailstate.setStatemail("0");
		if(receive!=null && !receive.equals("")) {
			mailstate.setReceive("0");
		}
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
