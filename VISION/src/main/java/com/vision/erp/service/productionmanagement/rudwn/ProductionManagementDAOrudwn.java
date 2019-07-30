package com.vision.erp.service.productionmanagement.rudwn;

import java.util.List;
import java.util.Map;

import com.vision.erp.service.domain.InteProduction;
import com.vision.erp.service.domain.OrderToVendor;
import com.vision.erp.service.domain.OrderToVendorProduct;
import com.vision.erp.service.domain.Product;

public interface ProductionManagementDAOrudwn {
	//��ǰ���
	public void addProduct(Product product)throws Exception;

	//��ǰ����
	public void updateProduct(Product product)throws Exception;

	//��ǰ���º��� (���/���x)
	public void updateUsageStatus(Product product)throws Exception;

	//��ǰ��Ϻ�����
	public List<Product> selectProductList() throws Exception;

	//��ǰ�󼼺���
	public Product selectDetailProduct(String productNo) throws Exception;

	//���ָ��
	public List<OrderToVendor> selectOrderToVendorList() throws Exception;

	//���ֻ��º���(���ִ��/���ֿϷ�/��������/�������)
	public void modifyOrderToVenCode(OrderToVendor orderToVendor) throws Exception;
	
	public void modifyOrderToVenCode1(OrderToVendor orderToVendor) throws Exception;
	
	public void modifyOrderToVenCode2(OrderToVendor orderToVendor) throws Exception;

	//���ּ���ǰ ���º���(�԰���/�԰�Ϸ�)
	public void modifyOrderToVenItemCode(OrderToVendorProduct orderToVendorProduct) throws Exception;
	
	public void modifyOrderToVenItemCode2(OrderToVendorProduct orderToVendorProduct) throws Exception;
	
	//���ּ���ǰ ���º���� ��ǰ�����߰���.���񽺿��� ó���Ұ���
	public void updateProductCount(Product product) throws Exception;

	//���ּ���ǰ(�󼼺���)
	public List<OrderToVendorProduct> orderToVendorDetailList(OrderToVendorProduct orderToVendorProduct) throws Exception;
	
	//���ֿ�û 
	public String addOrderToVendor(OrderToVendor orderToVendor) throws Exception;

	//���ֹ�ǰ���(���ֿ�û�� insert all���ؼ� ���)
	public void addorderToVendorProduct(OrderToVendorProduct orderToVendorProduct) throws Exception;

}
