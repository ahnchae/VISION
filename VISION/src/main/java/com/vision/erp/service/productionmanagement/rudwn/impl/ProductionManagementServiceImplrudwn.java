package com.vision.erp.service.productionmanagement.rudwn.impl;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.vision.erp.service.accounting.AccountingDAO;
import com.vision.erp.service.domain.OrderToVendor;
import com.vision.erp.service.domain.OrderToVendorProduct;
import com.vision.erp.service.domain.Product;
import com.vision.erp.service.domain.Statement;
import com.vision.erp.service.productionmanagement.rudwn.ProductionManagementDAOrudwn;
import com.vision.erp.service.productionmanagement.rudwn.ProductionManagementServicerudwn;

@Service("productionManagementServiceImplrudwn")
public class ProductionManagementServiceImplrudwn implements ProductionManagementServicerudwn {

	@Autowired
	@Qualifier("productionManagementDAOImplrudwn")
	private ProductionManagementDAOrudwn productionDAO;


	@Autowired
	@Qualifier("accountingDAOImpl")
	private AccountingDAO accountingDAO;



	@Override
	public void addProduct(Product product) throws Exception {
		productionDAO.addProduct(product);

	}

	@Override
	public void updateProduct(Product product) throws Exception {
		productionDAO.updateProduct(product);

	}

	@Override
	public void updateUsageStatus(Product product) throws Exception {
		productionDAO.updateUsageStatus(product);

	}

	@Override
	public List<Product> selectProductList() throws Exception {

		return productionDAO.selectProductList();
	}

	@Override//����°žƴ�
	public Product selectDetailProduct(String productNo) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override///////////////////////////////////////////////////���������� �Ǻ��̴�
	public String addOrderToVendor(Map<String, Object> map) throws Exception {

		SimpleDateFormat format = new SimpleDateFormat ( "yyyy/MM/dd");
		String date = format.format (System.currentTimeMillis());

		//��ǥ���
		Statement statement = new Statement();
		//
		OrderToVendorProduct orderToVendorProduct = new OrderToVendorProduct();
		//�ŷ�ó������ ��ǰ��ã�����ؼ�
		Product product = new Product();
		
		
		String productNo = (String) map.get("productNo");
		OrderToVendor orderToVendor = (OrderToVendor) map.get("orderToVendor");
		

		//��ǥ���̺� �ŷ�ó���� ����ϱ� ���� ��ǰ�� �̸��� ������.
		product = productionDAO.selectDetailProduct(productNo);

		//����
		statement.setStatementCategoryCodeNo("02");
		//����
		statement.setTradeDate(date);
		//����
		statement.setTradeTargetName(product.getProductName());
		//����
		statement.setStatementDetail("����");
		//����
		statement.setTradeAmount(orderToVendor.getTotalAmount());
		//����
		statement.setAccountNo("1002384718373");


		accountingDAO.insertStatement(statement);

		System.out.println(statement.getStatementNo()); //������� ��ǥ�� �����

		String statementNo = statement.getStatementNo();

		orderToVendor.setStatementNo(statementNo);
		orderToVendor.setOrderToVenStatusCodeNo("01");

		productionDAO.addOrderToVendor(orderToVendor);

		String orderToVendorNo =  orderToVendor.getOrderToVendorNo();//������� ���ֿ� �����
		
		//for(int i = 0; i <= List) {
		
		//}//���ֹ�ǰ��� ����ϳ�  �̤�
		
		orderToVendorProduct.setOrderToVendorNo(orderToVendorNo);
	
		return productionDAO.addOrderToVendor(orderToVendor);
	}
	
	
	
	@Override
	public List<OrderToVendor> selectOrderToVendorList() throws Exception {
		// TODO Auto-generated method stub
		return productionDAO.selectOrderToVendorList();
	}

	@Override
	public void modifyOrderToVenCode(OrderToVendor orderToVendor) throws Exception {
		// TODO Auto-generated method stub
		productionDAO.modifyOrderToVenCode(orderToVendor);

	}
////////////////////////////////////////////////����
	//���ּ���ǰ ���º���(�԰���/�԰�Ϸ�)
	@Override
	public void modifyOrderToVenItemCode(OrderToVendorProduct orderToVendorProduct) throws Exception {
		// TODO Auto-generated method stub
		productionDAO.modifyOrderToVenItemCode(orderToVendorProduct);

	}

	@Override
	public void updateProductCount(Product product) throws Exception {
		// TODO Auto-generated method stub
		productionDAO.updateProduct(product);

	}

	@Override
	public void addorderToVendorProduct(OrderToVendorProduct orderToVendorProduct) throws Exception {
		// TODO Auto-generated method stub
		productionDAO.addorderToVendorProduct(orderToVendorProduct);
	}
////////////////////////////////////////////////
	
	
	@Override
	public List<OrderToVendorProduct> orderToVendorDetailList(OrderToVendorProduct orderToVendorProduct)
			throws Exception {
		// TODO Auto-generated method stub
		return productionDAO.orderToVendorDetailList(orderToVendorProduct);
	}



}
