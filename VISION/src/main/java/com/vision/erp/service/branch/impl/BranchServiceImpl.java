package com.vision.erp.service.branch.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.vision.erp.service.branch.BranchDAO;
import com.vision.erp.service.branch.BranchService;
import com.vision.erp.service.domain.BranchDailySales;
import com.vision.erp.service.domain.SalesMenu;
import com.vision.erp.service.domain.SalesProduct;

@Service("branchServiceImpl")
public class BranchServiceImpl implements BranchService{
	
	@Resource(name = "branchDAOImpl")
	private BranchDAO branchDAO;

	@Override
	public void addDailySales(SalesProduct salesProduct) throws Exception {		
		branchDAO.insertDailySales(salesProduct);
	}

	@Override
	public List<SalesProduct> getBranchDailySalesDetail(String branchNo, String salesDate) throws Exception {
		return branchDAO.selectDailySalesDetail(branchNo, salesDate);
	}

	@Override
	public List<BranchDailySales> getBranchDailySalesList(String branchNo) throws Exception {
		return branchDAO.selectDailySalesList(branchNo);
	}

	@Override
	public void modifySalesProduct(SalesProduct salesProduct) throws Exception {
		branchDAO.updateSalesProduct(salesProduct);
	}

	@Override
	public List<SalesMenu> getSalesMenuList() throws Exception {
		return branchDAO.selectSalesMenuList();
	}

	
	
	
}
