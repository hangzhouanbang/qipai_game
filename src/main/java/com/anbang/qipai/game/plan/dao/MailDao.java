package com.anbang.qipai.game.plan.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Query;

import com.anbang.qipai.game.plan.bean.mail.MailState;
import com.anbang.qipai.game.plan.bean.mail.SystemMail;
import com.anbang.qipai.game.plan.bean.members.Member;

/**系统邮件dao
 * @author 程佳 2018.6.8
 * **/
public interface MailDao {
	/**查询用户自身的所有邮件
	 * @param memberid 会员id
	 * @return 查询结果
	 * **/
	
	List<MailState> findall(String memberid);

	/**根据邮件id查询邮件时间查询多少天前的邮件
	 * @param mailid 邮件编号
	 * @return 返回的对象
	 * **/
	SystemMail findByIdtime(String mailid,long newtime);
	
	/**添加系统邮件
	 * @param mail 邮件内容
	 * @return 查询结果
	 * **/
	SystemMail addmail(SystemMail mail);
	
	/**分页查询所有会员
	 * @param query 查询条件
	 * @param pageble 分页条件
	 * @return 查询结果
	 * **/
	List<Member> pagingfind(Query query,Pageable pageable);
	
	/**查询总会员页数
	 * @param query 查询条件
	 * @param size 每页显示条数
	 * @param return 总页数
	 * **/
	Long count(Query query,Integer size);
	
	/**添加会员状态表
	 * @param mailstate 会员邮件信息
	 * **/
	void addmailstate(MailState mailstate);
	
	/**根据会员id查询会员注册时间
	 * @param memberid 会员id
	 * @return 返回结果
	 * **/
	Member findMemberById(String memberid);
	
	/**遍历所有系统邮件
	 * @return 所有邮件集合
	 * **/
	List<SystemMail> findallsystemmail();
	
	/**查询哪个会员的的哪个邮件
	 * @param memberid 会员id
	 * @param mailid 邮件id
	 * **/
	MailState findmembermail(String memberid,String mailid);
	
	/**修改单个会员单个邮件的状态
	 * @param mailstate 邮件状态对象
	 * **/
	void updatemembermail(MailState mailstate);
	
	/**查询一个会员的所有邮件
	 * @param memberid 会员编号
	 * **/
	List<MailState> findallmembermail(String memberid);
	
	/**根据邮件id查询邮件信息
	 * @param mailid 邮件编号
	 * **/
	SystemMail findmailById(String mailid);


}
