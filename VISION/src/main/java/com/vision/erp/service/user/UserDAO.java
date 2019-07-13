package com.vision.erp.service.user;

import java.util.List;
import java.util.Map;

import com.vision.erp.common.Search;
import com.vision.erp.service.domain.Branch;
import com.vision.erp.service.domain.HumanResourceCard;
import com.vision.erp.service.domain.User;

public interface UserDAO {
	//ȸ������
	public void addUser(User user) throws Exception;

	//�Ⱥ����ٰ���
	public List<User> selectUserList(Search search) throws Exception;

	//�� ��������
	public User selectUser(String userId) throws Exception;

	//��й�ȣ�����ϱ�
	public void updatePassword(User user) throws Exception; 

	//����
	public String proofMySelfForId1(HumanResourceCard hrcInfo) throws Exception;

	//����
	public String proofMySelfForPassword1(HumanResourceCard hrcInfo) throws Exception;

	//����
	public String proofMySelfForId2(Branch branch) throws Exception;

	//����
	public String proofMySelfForPassword2(Branch branch) throws Exception;

}
