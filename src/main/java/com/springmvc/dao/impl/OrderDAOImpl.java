package com.springmvc.dao.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.springmvc.dao.OrderDAO;
import com.springmvc.dao.ProductDao;
import com.springmvc.entity.OrderDetail;
import com.springmvc.entity.Orders;
import com.springmvc.entity.Products;
import com.springmvc.models.CartInfo;
import com.springmvc.models.CartLineInfo;
import com.springmvc.models.CustomerInfo;
import com.springmvc.models.OrderDetailInfo;
import com.springmvc.models.OrderInfo;
import com.springmvc.models.PaginationResult;

@Repository
@Transactional
public class OrderDAOImpl implements OrderDAO{
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private ProductDao productDAO;

	private int getMaxOrderNum() {
		Session session = sessionFactory.getCurrentSession();
		String hql = "SELECT MAX(ORD.orderNum) FROM Orders ORD";
		Query<Integer> query = session.createQuery(hql);
		Integer value = (Integer) query.uniqueResult();
		if (value == null) { //lan dau mua thi tra gia tri 0 = null
			return 0;
		}
		return value; //de lay gia tri + 1 ra don hang ke tiep , nghia la bn khach hang da mua dc bay nhieu hang
	}

	@Override
	public void saveOrder(CartInfo cartInfo) {
		Session session = sessionFactory.getCurrentSession();
		int orderNum = getMaxOrderNum() + 1; //nhu tren
		
		Orders order = new Orders();
		order.setId(UUID.randomUUID().toString()); //java.util.UUID
		order.setOrderNum(orderNum);
		order.setOrderDate(new Date()); // lay ngay hien tai 
		order.setAmount(cartInfo.getAmountTotal()); // lay trong cartinfo amount bn, cho tinh tien co roi thi lay gia tri thoi
		
		//set gia tri cua customer 
		CustomerInfo customerInfo = cartInfo.getCustomerInfo();
		order.setCustomerName(customerInfo.getName());
		order.setCustomerEmail(customerInfo.getEmail());
		order.setCustomerPhone(customerInfo.getPhone());
		order.setCustomerAddress(customerInfo.getAddress());
		session.persist(order); //phai luu trc khi orderDetail.setOrder(order);
		
		List<CartLineInfo> cartLineInfos = cartInfo.getCartLineInfos();
		for (CartLineInfo cartLineInfo : cartLineInfos) {
			OrderDetail orderDetail = new OrderDetail();
			orderDetail.setId(UUID.randomUUID().toString());
			orderDetail.setOrder(order);;
			orderDetail.setAmount(cartLineInfo.getAmount());
			orderDetail.setPrice(cartLineInfo.getProductInfo().getPrice());
			orderDetail.setQuantity(cartLineInfo.getQuantity());
			
			String code = cartLineInfo.getProductInfo().getCode();
			Products products = productDAO.getProductByCode(code);
			orderDetail.setProduct(products);
			
			session.persist(orderDetail);
		}
		cartInfo.setOrderNum(orderNum);
	}

	//@Page = 1 , 2...
	@Override
	public PaginationResult<OrderInfo> getAllOrderInfos(int page, int maxResult, int maxNavigationPage) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "SELECT NEW " + OrderInfo.class.getName() + " (ORD.id, ORD.orderDate, ORD.orderNum,  ORD.amount,  ORD.customerName, ORD.customerAddress, "
				+  " ORD.customerEmail, ORD.customerPhone) FROM Orders ORD ORDER BY ORD.orderNum DESC"; //theo giong thu tu ben constructor cua orderinfo
		Query<OrderInfo> query = session.createQuery(hql);
		List<OrderInfo> orderInfos = query.list();
		return new PaginationResult<OrderInfo>(query, page, maxResult, maxNavigationPage);
	}
	
	public Orders getOrderById(String orderId) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "SELECT ORD FROM Orders ORD WHERE ORD.id = :ORDERID";
		Query<Orders> query = session.createQuery(hql);
		query.setParameter("ORDERID", orderId);
		Orders orders = (Orders) query.uniqueResult();
		return orders;
	}

	@Override
	public OrderInfo getOrderInfoById(String orderId) { //tim id
		Orders orders = getOrderById(orderId);
		if (orders == null) {
		return null;
		}
		
		OrderInfo orderInfo = new OrderInfo(orders.getId(), orders.getOrderDate(), orders.getOrderNum(), orders.getAmount(),
				orders.getCustomerName(), orders.getCustomerAddress(), orders.getCustomerEmail(), orders.getCustomerPhone());
		// tao duoi db len nem vo orderinfo
		return orderInfo;
	}

	@Override
	public List<OrderDetailInfo> getAllOrderDetailInfos(String orderId) { //orderId tu tao 
		Session session = sessionFactory.getCurrentSession();
		String hql = "SELECT NEW " + OrderDetailInfo.class.getName() + " (ORD.id, ORD.product.code, ORD.product.name, "
				+ "ORD.quantity, ORD.price, ORD.amount) FROM OrderDetail ORD WHERE ORD.order.id = :ORDERID"; 
		//odi di qua theo thong so thu tu constructor, OrderDetail lay tu Order entity
		Query<OrderDetailInfo> query = session.createQuery(hql);
		query.setParameter("ORDERID", orderId);
		List<OrderDetailInfo> orderDetailInfos = query.list();
		return orderDetailInfos;
		
	}
}
