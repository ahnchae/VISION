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
@RequestMapping("/user/*")
public class UserController {

	@Autowired
	@Qualifier("userServiceImpl")
	private UserService userService;

	
	//�α����ϱ����� �� �����ҷ���
	@RequestMapping(value="json/getUser",method=RequestMethod.POST)
	public User gerUser(@RequestBody User user) throws Exception{

		return userService.selectUser(user.getUserId());
	}

	//������̵�ã��
	@RequestMapping(value="json/proofMySelfForId1",method=RequestMethod.POST)
	public String proofMySelfForId1(@RequestBody HumanResourceCard hrc) throws Exception{

		return userService.proofMySelfForId1(hrc);
	}

	//������̵�ã��
	@RequestMapping(value="json/proofMySelfForId2",method=RequestMethod.POST)
	public String proofMySelfForId2(@RequestBody Branch branch) throws Exception{

		return userService.proofMySelfForId2(branch);
	}

	//�����й�ȣã��
	@RequestMapping(value="json/proofMySelfForPassword1",method=RequestMethod.POST)
	public String proofMySelfForPassword1(@RequestBody HumanResourceCard hrc) throws Exception{

		return userService.proofMySelfForPassword1(hrc);
	}

	//�����й�ȣã��
	@RequestMapping(value="json/proofMySelfForPassword2",method=RequestMethod.POST)
	public String proofMySelfForPassword2(@RequestBody Branch branch) throws Exception{

		return userService.proofMySelfForPassword2(branch);
	}

	//����������
	@RequestMapping(value="json/selectInfo/{employeeNo}",method=RequestMethod.GET)
	public Map<String, Object> selectInfo(@PathVariable String employeeNo) throws Exception{
		return userService.selectInfo(employeeNo);
	}

	//��й�ȣ����
	@RequestMapping(value="json/updatePassword",method=RequestMethod.POST)
	public void updatePassword(@RequestBody User user) throws Exception{
		 userService.updatePassword(user);
	}


}
