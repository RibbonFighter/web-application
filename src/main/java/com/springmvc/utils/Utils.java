package com.springmvc.utils;

import javax.servlet.http.HttpServletRequest;

import com.springmvc.models.CartInfo;

public class Utils {
	
	//thong tin cac mat hang da mua luu tru trong session
	public static CartInfo getCartInfoInSession(HttpServletRequest request) {
		
		CartInfo cartInfo = (CartInfo) request .getSession().getAttribute("myCartInfo");
		//neu chua tao gio hang, tao no
		if (cartInfo == null) {
			cartInfo = new CartInfo();
			//luu no vo session
			request.getSession().setAttribute("myCartInfo", cartInfo);
		}
		
		return cartInfo;
	}
	
	public static void removeCartInfoSession(HttpServletRequest request) {
		request.getSession().removeAttribute("myCartInfo");
	}
	
	public static void storeLastOrderedCartInfoInSession(HttpServletRequest request, CartInfo cartInfo) {
		request.getSession().setAttribute("lastOrderedCartInfo", cartInfo);
	}
	
	public static CartInfo getLastOrderedCartInfoInSession(HttpServletRequest request) {
		return (CartInfo) request.getSession().getAttribute("lastOrderedCartInfo");
	}
}
