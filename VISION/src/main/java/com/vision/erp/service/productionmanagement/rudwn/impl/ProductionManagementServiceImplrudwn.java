package com.vision.erp.service.productionmanagement.rudwn.impl;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.vision.erp.service.accounting.AccountingDAO;
import com.vision.erp.service.domain.InteProduction;
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

	@Override
	public List<OrderToVendor> selectOrderToVendorList() throws Exception {
		// TODO Auto-generated method stub
		return productionDAO.selectOrderToVendorList();
	}

	//���ִ�⿡�� ��������ϴ� �޼ҵ�+��ǥ�����ڵ庯��
	@Override
	public void modifyOrderToVenCode(Map<String, Object> map) throws Exception {

		OrderToVendor orderToVendor = (OrderToVendor) map.get("orderToVendor");
		Statement statement = (Statement) map.get("statement");

		productionDAO.modifyOrderToVenCode(orderToVendor);
		accountingDAO.updateStatementUsageStatus(statement);

	}

	//���ּ���ǰ ���º���(�԰���/�԰�Ϸ�)
	@Override
	public void modifyOrderToVenItemCode(Map<String, Object> map) throws Exception {

		OrderToVendorProduct orderToVendorProduct = (OrderToVendorProduct) map.get("orderToVendorProduct");
		Product product = (Product) map.get("product");
		OrderToVendor orderToVendor =new OrderToVendor();
		orderToVendor.setOrderToVendorNo(orderToVendorProduct.getOrderToVendorNo());
		int count = 0;
		//�԰�Ϸᰡ �� �Ǿ����� ���������� ���ֿϷ�� �ٲٱ� ���ؼ� ���������°�
		List<OrderToVendorProduct> listOrderSize = productionDAO.orderToVendorDetailList(orderToVendorProduct);
		int sizeCount = listOrderSize.size();
		
		productionDAO.modifyOrderToVenItemCode(orderToVendorProduct);
		productionDAO.updateProductCount(product);
		
	
		for(int i=0; i<listOrderSize.size(); i++) {
			
			if(listOrderSize.get(i).getOrderToVendorProductStatusCodeNo().equals("02")) {
				count += 1;				
				if(count == sizeCount) {
					productionDAO.modifyOrderToVenCode1(orderToVendor);
				} 
			}
		}
	}

	@Override
	public List<OrderToVendorProduct> orderToVendorDetailList(OrderToVendorProduct orderToVendorProduct)
			throws Exception {
		// TODO Auto-generated method stub
		return productionDAO.orderToVendorDetailList(orderToVendorProduct);
	}

	@Override
	public String addOrderToVendor(Map<String, Object> map) throws Exception {

		Statement statement = (Statement) map.get("statement");
		OrderToVendor orderToVendor = (OrderToVendor) map.get("orderToVendor");
		List<OrderToVendorProduct> orderToVendorProducts = (List<OrderToVendorProduct>) map.get("productList");


		//��ǥ�� ���
		accountingDAO.insertStatement(statement);

		String statementNo = statement.getStatementNo();

		orderToVendor.setStatementNo(statementNo);

		//���ּ� ���

		productionDAO.addOrderToVendor(orderToVendor);

		String orderToVendorNo =  orderToVendor.getOrderToVendorNo();

		for(int i = 0; i < orderToVendorProducts.size();i++) {
			OrderToVendorProduct orderToVendorProduct = orderToVendorProducts.get(i);
			orderToVendorProduct.setOrderToVendorNo(orderToVendorNo);
			orderToVendorProduct.setOrderToVendorProductStatusCodeNo("01");
			productionDAO.addorderToVendorProduct(orderToVendorProduct);
		}

		return null;
	}

}
