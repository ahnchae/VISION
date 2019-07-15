package com.vision.erp.service.productionmanagement.codms.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.vision.erp.common.Search;
import com.vision.erp.service.accounting.AccountingDAO;
import com.vision.erp.service.domain.OrderFromBranch;
import com.vision.erp.service.domain.OrderFromBranchProduct;
import com.vision.erp.service.domain.Product;
import com.vision.erp.service.domain.Statement;
import com.vision.erp.service.productionmanagement.codms.ProductionManagementDAOcodms;
import com.vision.erp.service.productionmanagement.codms.ProductionManagementServicecodms;
import com.vision.erp.service.productionmanagement.rudwn.ProductionManagementDAOrudwn;

@Service("productionManagementServiceImplcodms")
public class ProductionManagementServiceImplcodms implements ProductionManagementServicecodms {

	//field
	@Resource(name="productionManagementDAOImplcodms")
	private ProductionManagementDAOcodms productionManagementDAOcodms;
	
	@Resource(name="productionManagementDAOImplrudwn")
	private ProductionManagementDAOrudwn productionManagementDAOrudwn;
	
	@Resource(name="accountingDAOImpl")
	private AccountingDAO accountingDAO;
	
	//method
	//[����] �ֹ������
	@Override
	public void addOrderFromBranch(OrderFromBranch orderFromBranch) throws Exception {
		
		//���� ���� ����ϰ� �ֹ����� �����ϱ�
		List<OrderFromBranchProduct> finalList = new ArrayList<OrderFromBranchProduct>(); 
		int orderFromBranchTotalAmount = 0;
		for(OrderFromBranchProduct op : orderFromBranch.getOrderFromBranchProductList()) {
			OrderFromBranchProduct resultOp = calculateOrderFromBranchProduct(op);
			finalList.add(resultOp);
			orderFromBranchTotalAmount += Integer.parseInt(resultOp.getOrderFromBranchProductAmount());
		}
		orderFromBranch.setOrderFromBranchProductList(finalList);
		orderFromBranch.setOrderFromBranchTotalAmount(""+orderFromBranchTotalAmount);
		
		//���� ��¥�� �ֹ����� �Է��ϱ�
		SimpleDateFormat format = new SimpleDateFormat ( "yyyy/mm/dd");
		String date = format.format (System.currentTimeMillis());
		orderFromBranch.setOrderDate(date);
		
		//��ǥ����ϰ� �ֹ����� ��ǥ��ȣ �����ϱ�
		orderFromBranch.setStatementNo(addStatement(orderFromBranch).getStatementNo());
		
		//�ֹ��� ���
		//������ȣ, ��ǥ��ȣ, �Ѱ����� �־����
		productionManagementDAOcodms.insertOrderFromBranch(orderFromBranch);
		
		//�ֹ���ǰ ���
		//�ֹ�����ȣ, ��ǰ��ȣ, ����, ����, �ݾ��� �־����
		for(OrderFromBranchProduct op : orderFromBranch.getOrderFromBranchProductList()) {
			productionManagementDAOcodms.insertOrderFromBranchProduct(op.setOrderFromBranchNo(orderFromBranch.getOrderFromBranchNo()));
		}
		
	}
	
	//[����, ����]�ֹ������º���(�ֹ����01, �ֹ��Ϸ�02, �ֹ�����03, ��ҿ�û04, ���Ȯ��05)
	@Override
	public void modifyOrderFromBranchStatus(OrderFromBranch orderFromBranch) throws Exception {
		//�ֹ������� �����ϱ�
		productionManagementDAOcodms.updateOrderFromBranchStatus(orderFromBranch);
		
		//��ҿ�û�� �ֹ���ǰ ����öȸ�ϱ�
		if(orderFromBranch.getOrderFromBranchStatusCodeNo().equals("04")) {
			for(OrderFromBranchProduct op : orderFromBranch.getOrderFromBranchProductList()) {
				//�ֹ���ǰ����(���ϴ��01, ���ϿϷ�02, ����öȸ03)
				op.setOrderFromBranchProductStatusCodeNo("03");
				productionManagementDAOcodms.updateOrderFromBranchProductStatus(op);
			}
		}
		
		//���Ȯ���� ��ǥ ����ϱ�
		if(orderFromBranch.getOrderFromBranchStatusCodeNo().equals("05")) {
			Statement stmt = accountingDAO.selectStatementDetail(orderFromBranch.getStatementNo());
			stmt.setStatementUsageStatusCodeNo("02");
			accountingDAO.updateStatementUsageStatus(stmt);
		}
	}

	//[����, ����]�ֹ�������Ʈ ��������(�ֹ���ǰ ä����) ������ȣ searchKeyword�� ä���
	@Override
	public List<OrderFromBranch> getOrderFromBranchList(Search search) throws Exception {
		return productionManagementDAOcodms.selectOrderFromBranchList(search);
	}

	//[����, ����]�ֹ��� �󼼺��� �ֹ�����ȣ searchKeyword�� ä���
	@Override
	public OrderFromBranch getOrderFromBranchDetail(Search search) throws Exception {
		// TODO Auto-generated method stub
		return productionManagementDAOcodms.selectOrderFromBranchDetail(search);
	}

	//[����] �ֹ���ǰ ���º���(���ϴ��01, ���ϿϷ�02, ����öȸ03)
	//�ֹ�����ȣ, ��ǰ��ȣ �־����
	@Override
	public void modifyOrderFromBranchProductStatus(OrderFromBranchProduct orderFromBranchProduct) throws Exception {
		//��ǰ���º����ϱ�
		productionManagementDAOcodms.updateOrderFromBranchProductStatus(orderFromBranchProduct);
		
		if(orderFromBranchProduct.getOrderFromBranchProductStatusCodeNo().equals("02")) {
			//�ֹ����� ��ǰ�� ù ���϶�� �ֹ��� ���¸� �ֹ��������� ����
			changeOrderFromBranchStatus(orderFromBranchProduct, "03");
			//��� ��ǰ ���ϿϷ�� �ֹ������� �ֹ��Ϸ�� ����
			changeOrderFromBranchStatus(orderFromBranchProduct, "02");
		}
		
	}
	
	//���Ͻ� �ֹ������� ����
	private void changeOrderFromBranchStatus(OrderFromBranchProduct orderFromBranchProduct, String orderFromBranchStatusCodeNo) throws Exception{
		//�ֹ���ǰ�� �ش�Ǵ� �ֹ��� ��������
		Search search = new Search();
		search.setSearchKeyword(orderFromBranchProduct.getOrderFromBranchNo());
		OrderFromBranch ob = productionManagementDAOcodms.selectOrderFromBranchDetail(search);
		
		//�ֹ����� �ش�Ǵ� �̹�۹�ǰ ���� Ȯ���ϱ�
		int notDeliveredYet = productionManagementDAOcodms.selectOrderFromBranchProduct(ob.getOrderFromBranchNo());
		
		//if(��� ��ǰ ���ϿϷ�� �ֹ������� �ֹ��Ϸ�� ���� || ù ��ǰ ���Ͻ� �ֹ��� ���� �ֹ��������� ����)
		if(notDeliveredYet==0 || (ob.getOrderFromBranchStatusCodeNo().equals("01")&&orderFromBranchProduct.getOrderFromBranchProductStatusCodeNo().equals("02"))) {
			ob.setOrderFromBranchStatusCodeNo(orderFromBranchStatusCodeNo);
			productionManagementDAOcodms.updateOrderFromBranchStatus(ob);
		}
	}

	//�ֹ���ǰ �ݾ� ����ϱ�
	private OrderFromBranchProduct calculateOrderFromBranchProduct(OrderFromBranchProduct op) throws Exception{
		Product product = productionManagementDAOrudwn.selectDetailProduct(op.getProductNo());
		op.setPrice(product.getSalesPrice());
		op.setOrderFromBranchProductAmount(""+(Integer.parseInt(product.getSalesPrice())*Integer.parseInt(op.getOrderFromBranchProductQuantity())));
		System.out.println("CalculateOrderFromBranchProduct() : "+op);
		return op;
	}
	
	//��ǥ ����ϱ�
	private Statement addStatement(OrderFromBranch orderFromBranch) throws Exception {
		Statement statement = new Statement();
		statement.setTradeDate(orderFromBranch.getOrderDate());
		statement.setTradeTargetName(orderFromBranch.getBranchName());
		statement.setStatementCategoryCodeNo("01");
		statement.setStatementDetail("�ֹ�");
		statement.setAccountNo(orderFromBranch.getAccountNo());
		statement.setTradeAmount(orderFromBranch.getOrderFromBranchTotalAmount());
		accountingDAO.insertStatement(statement);
		
		return statement;
	}
}
