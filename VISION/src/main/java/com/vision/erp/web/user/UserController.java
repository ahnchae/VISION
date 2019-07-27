package com.vision.erp.web.user;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.vision.erp.service.domain.Branch;
import com.vision.erp.service.domain.HumanResourceCard;
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
	@RequestMapping(value="/user/proofMySelfForId1",method=RequestMethod.POST)
	public User proofMySelfForId1(@RequestBody HumanResourceCard hrc) throws Exception{

		return userService.proofMySelfForId1(hrc);
	}

	//������̵�ã��
	@RequestMapping(value="/user/proofMySelfForId2",method=RequestMethod.POST)
	public User proofMySelfForId2(@RequestBody Branch branch) throws Exception{

		return userService.proofMySelfForId2(branch);
	}

	//�����й�ȣã��
	@RequestMapping(value="/user/proofMySelfForPassword1",method=RequestMethod.POST)
	public User proofMySelfForPassword1(@RequestBody HumanResourceCard hrc) throws Exception{

		return userService.proofMySelfForPassword1(hrc);
	}

	//�����й�ȣã��
	@RequestMapping(value="/user/proofMySelfForPassword2",method=RequestMethod.POST)
	public User proofMySelfForPassword2(@RequestBody Branch branch) throws Exception{

		return userService.proofMySelfForPassword2(branch);
	}

	//����������
	@RequestMapping(value="/user/selectInfo/{employeeNo}",method=RequestMethod.GET)
	public Map<String, Object> selectInfo(@PathVariable String employeeNo) throws Exception{
		return userService.selectInfo(employeeNo);
	}

	//��й�ȣ����
	@RequestMapping(value="/user/updatePassword",method=RequestMethod.POST)
	public void updatePassword(@RequestBody User user) throws Exception{
		userService.updatePassword(user);
	}


}
