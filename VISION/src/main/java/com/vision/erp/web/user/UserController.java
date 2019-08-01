package com.vision.erp.web.user;

import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.vision.erp.common.SendSMS;
import com.vision.erp.service.domain.Branch;
import com.vision.erp.service.domain.HumanResourceCard;
import com.vision.erp.service.domain.SMS;
import com.vision.erp.service.domain.User;
import com.vision.erp.service.user.UserService;

@RestController
public class UserController {

	@Autowired
	@Qualifier("userServiceImpl")
	private UserService userService;


	//�α����ϱ����� �� �����ҷ���
	@RequestMapping(value="/user/loginUser",method=RequestMethod.POST)
	public User loginUser(@RequestBody User user) throws Exception{
			

			User dbuser = userService.selectUser(user);
			
			if(dbuser != null) {
				if(user.getUserId().equals(dbuser.getUserId()) && user.getPassword().equals(dbuser.getPassword()) ) {
					dbuser.setPassword(null);
					dbuser.setLoginFlag(true);
					return  dbuser;
				}
			}else {
				user.setLoginFlag(false);
				user.setUserId(null);
				user.setPassword(null);
			}
			
			return user;
	}

	//������̵�ã��
//	@RequestMapping(value="/user/proofMySelfForId1",method=RequestMethod.POST)
//	public User proofMySelfForId1(@RequestBody HumanResourceCard hrc) throws Exception{
//		
//		User user = userService.proofMySelfForId1(hrc);
//		if( user != null) {
//			return user;
//		}else {
//			return new User();
//		}
//	}
	
	//Test ���̵�ã��
		@RequestMapping(value="/user/forgotId",method=RequestMethod.POST)
		public User test(@RequestBody Map<String, String> map) throws Exception{
			
			HumanResourceCard humanResourceCard = new HumanResourceCard();
			Branch branch = new Branch();
			
			SendSMS sendSMS = SendSMS.getSendSMSInstance();
			SMS sms = new SMS();
			sms.setSender("010-3739-1105");
			sms.setReciever(map.get("phone").replace("-", ""));
			
			String message = "";
			
			humanResourceCard.setEmployeeName(map.get("name"));
			humanResourceCard.setEmployeePhone(map.get("phone"));
			branch.setBranchManagerName(map.get("name"));
			branch.setBranchManagerPhone(map.get("phone"));
			
			User user = userService.proofMySelfForId1(humanResourceCard);
			if( user != null) {
				message = "������ ���̵�� "+user.getUserId()+"�Դϴ�.";
				sms.setContent(message);
				sendSMS.sendSMS(sms);
				return user;
			}else {
				user = userService.proofMySelfForId2(branch);
				if( user != null) {
					message = "������ ���̵�� "+user.getUserId()+"�Դϴ�.";
					sms.setContent(message);
					sendSMS.sendSMS(sms);
					return user;
				}else {
					return new User();
				}
			}
		}

	//������̵�ã��
//	@RequestMapping(value="/user/proofMySelfForId2",method=RequestMethod.POST)
//	public User proofMySelfForId2(@RequestBody Branch branch) throws Exception{
//
//		User user = userService.proofMySelfForId2(branch);
//		if( user != null) {
//			return user;
//		}else {
//			return new User();
//		}
//	}

//	//�����й�ȣã��
//	@RequestMapping(value="/user/proofMySelfForPassword1",method=RequestMethod.POST)
//	public User proofMySelfForPassword1(@RequestBody HumanResourceCard hrc) throws Exception{
//
//		User user = userService.proofMySelfForPassword1(hrc);
//		if( user != null) {
//			return user;
//		}else {
//			return new User();
//		}
//	}
//
//	//�����й�ȣã��
//	@RequestMapping(value="/user/proofMySelfForPassword2",method=RequestMethod.POST)
//	public User proofMySelfForPassword2(@RequestBody Branch branch) throws Exception{
//
//		User user = userService.proofMySelfForPassword2(branch);
//		if( user != null) {
//			return user;
//		}else {
//			return new User();
//		}
//	}
	
	//Test ��й�ȣ ã��
	@RequestMapping(value="/user/forgotPassword",method=RequestMethod.POST)
	public boolean proofMySelfForPassword1(@RequestBody Map<String, String> map) throws Exception{

		User user = userService.proofMySelfForPassword1(map);
		if(user != null) {
			return true;
		}else {
			user = userService.proofMySelfForPassword2(map);
			if(user != null) {
				return true;
			}else {
				return false;
			}
		}
	}
	
	@RequestMapping(value = "/user/getIdentifyCode", method=RequestMethod.POST)
	public StringBuffer getIdentifyCode(@RequestBody Map<String, String> map) throws Exception{
		
		Random random = new Random();
		StringBuffer buf = new StringBuffer();
		for(int i = 0; i<5; i++) {
			if(random.nextBoolean()) {
				buf.append((char)((int)(random.nextInt(26))+65));
			}else {
				buf.append((random.nextInt(10)));
			}
		}
		
		System.out.println(buf);
		
		SendSMS sendSMS = SendSMS.getSendSMSInstance();
		SMS sms = new SMS();
		sms.setSender("010-3739-1105");
		sms.setReciever(map.get("phone").replace("-", ""));
		
		String message = "";
		
		User user = userService.proofMySelfForPassword1(map);
		if(user != null) {
			message = "������ ������ȣ�� ["+buf+"]�Դϴ�. �ùٸ��� �Է��ϼ���.";
			sms.setContent(message);
			sendSMS.sendSMS(sms);
			return buf;
		}else {
			user = userService.proofMySelfForPassword2(map);
			if(user != null) {
				message = "������ ������ȣ�� ["+buf+"]�Դϴ�. �ùٸ��� �Է��ϼ���.";
				sms.setContent(message);
				sendSMS.sendSMS(sms);
				return buf;
			}else {
				return null;
			}
		}
	}

	//����������
	@RequestMapping(value="/user/selectInfo/{employeeNo}",method=RequestMethod.GET)
	public Map<String, Object> selectInfo(@PathVariable String employeeNo) throws Exception{
		return userService.selectInfo(employeeNo);
	}

	//��й�ȣ����
	@RequestMapping(value="/user/modifyPassword",method=RequestMethod.POST)
	public void modifyPassword(@RequestBody User user) throws Exception{
		userService.updatePassword(user);
	}


}
