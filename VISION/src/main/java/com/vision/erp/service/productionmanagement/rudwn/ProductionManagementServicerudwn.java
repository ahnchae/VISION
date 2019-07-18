package com.vision.erp.service.productionmanagement.rudwn;

import java.util.List;
import java.util.Map;

import com.vision.erp.service.domain.InteProduction;
import com.vision.erp.service.domain.OrderToVendor;
import com.vision.erp.service.domain.OrderToVendorProduct;
import com.vision.erp.service.domain.Product;

public interface ProductionManagementServicerudwn {

	//��ǰ���
	public void addProduct(Product product)throws Exception;

	//��ǰ����
	public void updateProduct(Product product)throws Exception;

	//��ǰ���º��� (���/���x)
	public void updateUsageStatus(Product product)throws Exception;

	//��ǰ��Ϻ�����
	public List<Product> selectProductList() throws Exception;

	//��ǰ�󼼺���(��������ϴ°žƴ�)
	public Product selectDetailProduct(String productNo) throws Exception;

	//���ָ��
	public List<OrderToVendor> selectOrderToVendorList() throws Exception;

	//���ֻ��º���(���ִ��/���ֿϷ�/��������/�������)
	public void modifyOrderToVenCode(Map<String, Object> map) throws Exception;

	//���ּ���ǰ ���º���(�԰���/�԰�Ϸ�)
	public void modifyOrderToVenItemCode(Map<String, Object> map) throws Exception;

	//���ּ���ǰ(�󼼺���)
	public List<OrderToVendorProduct> orderToVendorDetailList(OrderToVendorProduct orderToVendorProduct) throws Exception;

	//���ֿ�û 
	public String addOrderToVendor(Map<String, Object> map) throws Exception;

}
