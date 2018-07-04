package com.anbang.qipai.game.plan.dao.mongodb;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import com.anbang.qipai.game.plan.bean.mail.MailState;
import com.anbang.qipai.game.plan.bean.mail.SystemMail;
import com.anbang.qipai.game.plan.bean.members.Member;
import com.anbang.qipai.game.plan.dao.MailDao;

@Component
public class MongdbMailDao implements MailDao{
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	/**根据会员id查询这个会员下的所有邮件
	 * **/
	@Override
	public List<MailState> findall(String memberid) {
		Criteria criteria = new Criteria();
		criteria.andOperator(Criteria.where("memberid").is(memberid));//多条件查询
		List<MailState> list = mongoTemplate.find(new Query(criteria),MailState.class);
		return list;
	}

	/**根据邮件id查询邮件时间查询多少天前的邮件**/
	@Override
	public SystemMail findByIdtime(String mailid, long newtime) {
		Criteria criteria = new Criteria();
		criteria.andOperator(Criteria.where("id").is(mailid),Criteria.where("createtime").gte(newtime));//多条件查询
		return mongoTemplate.findOne(new Query(criteria),SystemMail.class);
	}

	
	/**添加并返回邮件信息
	 * **/
	@Override
	public SystemMail addmail(SystemMail mail){
		mongoTemplate.insert(mail);
		return mail;
	}

	/**返回第几个一万条数据
	 * **/
	@Override
	public List<Member> pagingfind(Query query,Pageable pageable) {
		List<Member> list = mongoTemplate.find(query.with(pageable),Member.class);
		return list;
	}

	/**查询多少页
	 * **/
	@Override
	public Long count(Query query,Integer size) {
		Long total = mongoTemplate.count(query, Member.class);//计算总数
		Long count = total%size==0?total/size:total/size+1;
		return count;
	}
	/**添加邮件状态
	 * **/
	@Override
	public MailState addmailstate(MailState mailstate) {
		mongoTemplate.insert(mailstate);
		return mailstate;
	}
	/**修改邮件状态
	 * **/
	@Override
	public void updateMailState(MailState mailstate) {
		Query query =new Query(Criteria.where("id").is(mailstate.getId()));
		Update update = new Update();
		update.set("receive",mailstate.getReceive());
		update.set("statemail",mailstate.getStatemail());
		update.set("deletestate",mailstate.getDeletestate());
		mongoTemplate.updateFirst(query, update, MailState.class);
	}

	/**根据会员id查询会员注册时间
	 * @param memberid 会员id
	 * **/
	@Override
	public Member findMemberById(String memberid) {
		return mongoTemplate.findById(memberid, Member.class);
	}
	
	/**查询所有系统邮件**/
	@Override
	public List<SystemMail> findallsystemmail() {
		return mongoTemplate.findAll(SystemMail.class);
	}
	
	/**查询哪个会员的哪个邮件**/
	@Override
	public MailState findmembermail(String memberid, String mailid) {
		Criteria criteria = new Criteria();
		criteria.andOperator(Criteria.where("memberid").is(memberid),Criteria.where("mailid").is(mailid));//多条件查询
		return mongoTemplate.findOne(new Query(criteria), MailState.class);
	}

	/**修改哪个会员哪个邮件的状态**/
	@Override
	public void updatemembermail(MailState mailstate) {
		mongoTemplate.save(mailstate);
	}

	/**查询一个会员的所有邮件
	 * **/
	@Override
	public List<MailState> findallmembermail(String memberid) {
		Query query = new Query(Criteria.where("memberid").is(memberid));
		return mongoTemplate.find(query,MailState.class);
	}
	
	/**根据邮件id查询邮件信息
	 * **/
	@Override
	public SystemMail findmailById(String mailid) {
		return mongoTemplate.findById(mailid,SystemMail.class);
	}



}
