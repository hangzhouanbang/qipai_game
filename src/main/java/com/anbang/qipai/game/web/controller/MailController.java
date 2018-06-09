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
import com.anbang.qipai.game.plan.bean.mail.MailState;
import com.anbang.qipai.game.plan.bean.mail.SystemMail;
import com.anbang.qipai.game.plan.service.MailService;
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
		
		SystemMail mails = new SystemMail();
		mails.setTitle(mail1.getTitle());
		mails.setFile(mail1.getFile());
		mails.setIntegral(mail1.getIntegral());
		mails.setNumber(mail1.getNumber());
		mails.setVipcard(mail1.getVipcard());
		logger.info("66666666666"+mails.getIntegral()+mails.getNumber()+mails.getVipcard());
		mails.setCreatetime(System.currentTimeMillis());
		
		SystemMail mail2 = mailService.addmail(mails);
		mailMsgService.createmail(mail2);
		//给会员发送邮件
		mailService.pagingfind(mail2.getId());
		return new CommonVO();
	}
	
	/**用户查看本身的邮件
	 * @param 用户的id
	 * @throws ParseException 
	 * **/
	@RequestMapping("/querymail")
	@ResponseBody
	public Map<String,Object> querymail(String memberid) throws ParseException{
		return mailService.findall(memberid); 
	}
	
	/**用户进入游戏大厅，是否有未读邮件，有未读显示小红点
	 * @param memberid 会员id
	 * **/
	@RequestMapping("/redmail")
	@ResponseBody
	public Map<String,Object> redmail(String memberid){
		return mailService.redmail(memberid);
	}
	
	/**用户点开邮件，改变邮件状态
	 * @param memberid 会员id
	 * @param mailid 邮件id
	 * @param receive 是否领取
	 * **/
	@RequestMapping("/updatemailstate")
	@ResponseBody
	public String changestate(String memberid,String mailid,String receive) {
		if(memberid != null && mailid !=null) 
		{
			MailState mailstate = mailService.changestate(memberid, mailid, receive);
			if(mailstate!=null) {
				return "success";
			}
			return "file";
		}
	return "file";
		
	}
	
	/**所有邮件设为已读
	 * @param memberid 会员id
	 * **/
	@RequestMapping("/updateallmail")
	@ResponseBody
	public String updateallmail(String memberid) {
		long count = mailService.findallmembermail(memberid);
		if(count != 0) {
			return "success";
		}
		return "file";
	}
	
	/**删除所有已读
	 * @param memberid 会员id
	 * **/
	@RequestMapping("/deleteallmail")
	@ResponseBody
	public String deleteallmail(String memberid) {
		long count = mailService.deleteallmail(memberid);
		if(count != 0) {
			return "success";
		}
		return "file";
	}
	
	
}

