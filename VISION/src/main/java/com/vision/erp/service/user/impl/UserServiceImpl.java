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
	//����������(�޼ҵ��̿�)�λ�ī��/����/�޿��̷�/��������
	@Override
	public Map selectUser(String find) throws Exception {
		
		Search search = new Search();
		Map<String, Object> map = new HashMap<String, Object>();
		search.setSearchKeyword(find);
		
		HumanResourceCard humanResourceCard = humanResourceDAO.selectHumanResourceCardDetailByEmployeeNo(find);
		List<Salary> salary = accountingDAO.selectSalaryList(search);
		List<WorkAttitude> workAttitude = humanResourceDAO.selectWorkAttitudeList(search);
		Branch branch = businessSupportDAO.selectBranchDetail(find);
		
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
		// TODO Auto-generated method stub
		return null;
	}
	//�����й�ȣȮ��
	@Override
	public String proofMySelfForPassword1(HumanResourceCard hrcInfo) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	//������̵����
	@Override
	public String proofMySelfForId2(Branch branch) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	//�����й�ȣȮ��
	@Override
	public String proofMySelfForPassword2(Branch branch) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
