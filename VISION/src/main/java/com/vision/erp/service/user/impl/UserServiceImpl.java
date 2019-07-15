package com.vision.erp.service.user.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.vision.erp.common.Search;
import com.vision.erp.service.accounting.AccountingDAO;
import com.vision.erp.service.businesssupport.BusinessSupportDAO;
import com.vision.erp.service.domain.Account;
import com.vision.erp.service.domain.Branch;
import com.vision.erp.service.domain.HumanResourceCard;
import com.vision.erp.service.domain.Salary;
import com.vision.erp.service.domain.User;
import com.vision.erp.service.domain.WorkAttitude;
import com.vision.erp.service.humanresource.HumanResourceDAO;
import com.vision.erp.service.user.UserDAO;
import com.vision.erp.service.user.UserService;

@Service("userServiceImpl")
public class UserServiceImpl implements UserService {
	@Autowired
	@Qualifier("userDAOImpl")
	private UserDAO userDAO;
	
	@Autowired
	@Qualifier("humanResourceDAOImpl")
	private HumanResourceDAO humanResourceDAO;
	
	@Autowired
	@Qualifier("accountingDAOImpl")
	private AccountingDAO accountingDAO;
	
	@Autowired
	@Qualifier("businessSupportDAOImpl")
	private BusinessSupportDAO businessSupportDAO;
	
	//ȸ������
	@Override
	public void addUser(User user) throws Exception {
		
		userDAO.addUser(user);
		
	}
	//�Ⱥ����ٰ���
	@Override
	public List<User> selectUserList(Search search) throws Exception {
		// TODO Auto-generated method stub
		return userDAO.selectUserList(search);
	}
	//����������(�޼ҵ��̿�)�λ�ī��/����/�޿��̷�/��������(�� �޼ҵ� ���°žƴ�)
	@Override
	public Map<String,Object> selectInfo(String employeeNo) throws Exception {
		
		Search search = new Search();
		Map<String, Object> map = new HashMap<String, Object>();
		search.setSearchKeyword(employeeNo);
		
		HumanResourceCard humanResourceCard = humanResourceDAO.selectHumanResourceCardDetailByEmployeeNo(employeeNo);
		List<Salary> salary = accountingDAO.selectSalaryList(search);
		List<WorkAttitude> workAttitude = humanResourceDAO.selectWorkAttitudeList(search);
		Branch branch = businessSupportDAO.selectBranchDetail(employeeNo);
		
		map.put("humanResourceCard", humanResourceCard);
		map.put("salary", salary);
		map.put("workAttitude", workAttitude);
		map.put("branch", branch);
		
		return map;
	}
	//��й�ȣ����
	@Override
	public void updatePassword(User user) throws Exception {
		
		userDAO.updatePassword(user);
		
	}
	//������̵�Ȯ��
	@Override
	public String proofMySelfForId1(HumanResourceCard hrcInfo) throws Exception {
		
		return userDAO.proofMySelfForId1(hrcInfo);
	}
	//�����й�ȣȮ��
	@Override
	public String proofMySelfForPassword1(HumanResourceCard hrcInfo) throws Exception {
		
		return userDAO.proofMySelfForPassword1(hrcInfo);
	}
	//������̵����
	@Override
	public String proofMySelfForId2(Branch branch) throws Exception {
		
		return userDAO.proofMySelfForId2(branch);
	}
	//�����й�ȣȮ��
	@Override
	public String proofMySelfForPassword2(Branch branch) throws Exception {
		// TODO Auto-generated method stub
		return userDAO.proofMySelfForPassword2(branch);
	}
	@Override
	public User selectUser(String userId) throws Exception {
		
		return userDAO.selectUser(userId);
	}
	
	

}
