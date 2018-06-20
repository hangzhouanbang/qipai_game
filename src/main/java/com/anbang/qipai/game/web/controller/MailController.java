package com.anbang.qipai.game.web.controller;

import java.text.ParseException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.anbang.qipai.game.msg.service.MailMsgService;
import com.anbang.qipai.game.plan.bean.mail.SystemMail;
import com.anbang.qipai.game.plan.service.MailService;
import com.anbang.qipai.game.plan.service.MemberAuthService;
import com.anbang.qipai.game.web.vo.CommonVO;

import net.sf.json.JSONObject;


/**系统邮件controller
 * @author 程佳 2018.6.8
 * **/
@RestController
@RequestMapping("/mail")
public class MailController {
	
	@Autowired
	private MailService mailService;
	
	@Autowired
	private MemberAuthService memberAuthService;
	
	@Autowired
	private MailMsgService mailMsgService;
	
	
	private static Logger logger = LoggerFactory.getLogger(MailController.class);
	
	/**新发布的系统公告,并给所有用户发送邮件
	 * @param mail 管理系统传过来的json字符串
	 * @return 接收成功
	 * **/
	@RequestMapping("/addmail")
	@ResponseBody
	public CommonVO addmail(String mail) {
		JSONObject json = JSONObject.fromObject(mail);
		SystemMail mail1 = (SystemMail) JSONObject.toBean(json, SystemMail.class);
		logger.info("管理员名称"+mail1.getAdminname());
		SystemMail mails = new SystemMail();
		mails.setAdminname(mail1.getAdminname());
		mails.setTitle(mail1.getTitle());
		mails.setFile(mail1.getFile());
		mails.setIntegral(mail1.getIntegral());
		mails.setNumber(mail1.getNumber());
		mails.setVipcard(mail1.getVipcard());
		mails.setCreatetime(System.currentTimeMillis());
		mails.setStatus(mail1.getStatus());
		SystemMail mail2 = mailService.addmail(mails);
		//给会员发送邮件
		mailService.pagingfind(mail2.getId());
		mailMsgService.createmail(mail2);
		return new CommonVO();
	}
	
	/**用户查看本身的邮件
	 * @param 用户的id
	 * @throws ParseException 
	 * **/
	@RequestMapping("/querymail")
	@ResponseBody
	public CommonVO querymail(String token,String you) throws ParseException{
		CommonVO vo = new CommonVO();
		String memberId = memberAuthService.getMemberIdBySessionId(token);
		if (memberId == null) {
			vo.setSuccess(false);
			vo.setMsg("invalid token");
			return vo;
		}
		logger.info("id"+memberId+you);
		Map<String,Object> map =  mailService.findall(memberId); 
		vo.setData(map);
		return vo;
	}
	
	/**用户点击单个邮件，查看邮件详情
	 * @param memberid 会员id
	 * @param mailid 邮件id
	 * **/
	@RequestMapping("/findonemail")
	@ResponseBody
	public CommonVO findonemail(String token,String mailid){
		CommonVO vo = new CommonVO();
		String memberId = memberAuthService.getMemberIdBySessionId(token);
		if (memberId == null) {
			vo.setSuccess(false);
			vo.setMsg("invalid token");
			return vo;
		}
		if(mailid == null) {
			vo.setSuccess(false);
			vo.setMsg("invalid mailid");
			return vo;
		}
		Map<String,Object> map = mailService.findonemail(memberId, mailid);
		vo.setData(map);
		return vo;
	}
	
	/**领取奖利
	 * @param memberid 会员id
	 * @param mailid 邮件id
	 * **/
	@RequestMapping("/updatemailstate")
	@ResponseBody
	public CommonVO changestate(String token,String mailid) {
		CommonVO vo = new CommonVO();
		String memberId = memberAuthService.getMemberIdBySessionId(token);
		if (memberId == null) {
			vo.setSuccess(false);
			vo.setMsg("invalid token");
			return vo;
		}
		if(mailid == null) {
			vo.setSuccess(false);
			vo.setMsg("invalid mailid");
			return vo;
		}
		vo = mailService.changestate(memberId, mailid);
		return vo;
	}
	
	/**所有邮件设为已读
	 * @param memberid 会员id
	 * **/
	@RequestMapping("/updateallmail")
	@ResponseBody
	public CommonVO updateallmail(String token) {
		CommonVO vo = new CommonVO();
		String memberId = memberAuthService.getMemberIdBySessionId(token);
		if (memberId == null) {
			vo.setSuccess(false);
			vo.setMsg("invalid token");
			return vo;
		}
		 mailService.findallmembermail(memberId);
		 return new CommonVO();
	}
	
	/**删除所有已读
	 * @param memberid 会员id
	 * **/
	@RequestMapping("/deleteallmail")
	@ResponseBody
	public CommonVO deleteallmail(String token) {
		CommonVO vo = new CommonVO();
		String memberId = memberAuthService.getMemberIdBySessionId(token);
		if (memberId == null) {
			vo.setSuccess(false);
			vo.setMsg("invalid token");
			return vo;
		}
		mailService.deleteallmail(memberId);
		return new CommonVO();
	}
	
	
}

