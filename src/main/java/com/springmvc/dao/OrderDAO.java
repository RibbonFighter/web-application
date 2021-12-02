package com.springmvc.dao;

import java.util.List;

import com.springmvc.models.CartInfo;
import com.springmvc.models.OrderDetailInfo;
import com.springmvc.models.OrderInfo;
import com.springmvc.models.PaginationResult;

public interface OrderDAO {

	public void saveOrder(CartInfo cartInfo);
	
	public PaginationResult<OrderInfo> getAllOrderInfos(int page, int maxResult, int maxNavigationPage);

	public OrderInfo getOrderInfoById(String orderId);

	public List<OrderDetailInfo> getAllOrderDetailInfos(String orderId);
}
