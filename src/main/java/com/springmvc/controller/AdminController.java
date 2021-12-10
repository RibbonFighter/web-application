package com.springmvc.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.springmvc.dao.AccountDAO;
import com.springmvc.dao.OrderDAO;
import com.springmvc.dao.ProductDao;
import com.springmvc.entity.Accounts;
import com.springmvc.entity.Products;
import com.springmvc.models.AccountInfo;
import com.springmvc.models.OrderDetailInfo;
import com.springmvc.models.OrderInfo;
import com.springmvc.models.PaginationResult;
import com.springmvc.models.ProductInfo;
import com.springmvc.validator.AccountsInfoValidator;
import com.springmvc.validator.ProductInfoValidator;

@Controller
public class AdminController {
	
	@Autowired
	private OrderDAO orderoDAO;
	
	@Autowired
	private ProductDao productDAO;
	
	@Autowired
	private AccountDAO accountDAO;
	
	
	@Autowired
	private ProductInfoValidator productInfoValidator;
	
	@Autowired
	private AccountsInfoValidator accountInfoValidator;
	
	
	@RequestMapping("/403")
	public String accessDenied() {
		return "/403";
	}
	
	@RequestMapping("/")
	public String home() {
		return "index";
	}
	
	//GET : hien thi trang login
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(Model model) {
		return "login";
	}
	
	@RequestMapping(value = {"/accountInfo"}, method = RequestMethod.GET)
	public String accountInfo(Model model) {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		System.out.println("username: " + userDetails.getUsername());
		System.out.println("password: " + userDetails.getPassword());
		System.out.println("enable: " + userDetails.isEnabled());
		
		model.addAttribute("userDetails",userDetails);
		return "accountInfo";
	}
	
	@RequestMapping(value = {"/orderList"}, method = RequestMethod.GET)
	public String orderList(Model model, @RequestParam(value = "page", defaultValue = "1") int page) {		
		final int MAX_INSULT = 5;
		final int MAX_NAVIGATION_PAGE = 10;
		PaginationResult<OrderInfo> paginationOrderInfos = orderoDAO.getAllOrderInfos(page, MAX_INSULT, MAX_NAVIGATION_PAGE);
		model.addAttribute("paginationOrderInfos",paginationOrderInfos);
		return "orderList";
	}
	
	@RequestMapping(value = {"/orders"}, method = RequestMethod.GET)
	public String orderView(Model model, @RequestParam("orderId") String orderId) {
		OrderInfo orderInfo = null;
		if (orderId != null) {
			orderInfo = orderoDAO.getOrderInfoById(orderId);
		}
		if (orderInfo == null) { 
			return "redirect:/orderList";
		}
		
		List<OrderDetailInfo> orderDetailInfos = orderoDAO.getAllOrderDetailInfos(orderId);
		//. toi phan odi ben model
		//C:foreach lam vong lap xuat ra nhung thang detail tuong ung
		orderInfo.setOrderDetailInfos(orderDetailInfos);// query nhung dua ma da lay tu db len
		model.addAttribute("orderInfo",orderInfo);//orderInfo ben orderInfo.customerName
		//.model tu OrderInfo
		return "orders"; //hien thi nhung order len tren jsp
	}
	
	//get: hien thi product
	@RequestMapping(value = {"/product"}, method = RequestMethod.GET)
	public String product(Model model, @RequestParam(value = "code", defaultValue = "") String code) {
		ProductInfo productInfo = null;
		if(code != null && code.length() > 0) {
			productInfo = productDAO.getProductInfoByCode(code);
		}
		if (productInfo == null) { //neu code ko ton tai thi se tao cai moi va set no lam true
			productInfo = new ProductInfo();
			productInfo.setNewProduct(true);
		}
		
		model.addAttribute("productForm",productInfo);
		return "product";
	}
	
	//post SAVE PRODUCT
	@RequestMapping(value = {"/product"}, method = RequestMethod.POST)
	public String productSave(Model model, @ModelAttribute("productForm") @Validated ProductInfo productInfo,
			BindingResult result) {
		productInfoValidator.validate(productInfo, result);
		if (result.hasErrors()) {
			return "product";
		}
		
		try {
			productDAO.saveProductInfo(productInfo);
		} catch (Exception e) {
			model.addAttribute("errorMessage",e.getMessage());
			return "product";
		}
		return "redirect:/productList";
	}
	
	//get DELETE PRODUCT
	@RequestMapping({ "/removeProduct" })
	public String deleteProductHandler(HttpServletRequest request, Model model,
			@RequestParam(value = "code", defaultValue = "") String code) {
		Products product = null;

		if (code != null) {
			product = productDAO.getProductByCode(code);
		}

		if (product != null) {
			productDAO.deleteProductByCode(code);
		}
		return "redirect:/productList";
	}
	
	
	@RequestMapping(value = { "/accountList" }, method = RequestMethod.GET)
	public String accountList(Model model, @RequestParam(value = "page", defaultValue = "1") String pageStr) {
		int page = 1;
		try {
			page = Integer.parseInt(pageStr);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		final int MAX_RESULT = 5;
		final int MAX_NAVIGATION_PAGE = 10;
		PaginationResult<AccountInfo> paginationAccountInfos = accountDAO.getAllAccountInfos(page, MAX_RESULT, MAX_NAVIGATION_PAGE);
		model.addAttribute("paginationAccountInfos", paginationAccountInfos);
		return "accountList";
	}
	
	// go to create a new user
	@RequestMapping(value = { "/account" }, method = RequestMethod.GET)
	public String account(Model model, @RequestParam(value = "userName", defaultValue = "") String userName) {
		Accounts account = null;
		if (userName != null) {
			account = accountDAO.getAccountByUsername(userName);
		}
		if (account == null) {
			account = new Accounts();
		}
		model.addAttribute("accountForm", account);
		return "account";
	}

		// inserting and updating a new user method
	@RequestMapping(value = { "/account" }, method = RequestMethod.POST)
	public String accountSave(Model model, @ModelAttribute("accountForm") @Validated AccountInfo accountInfo,
			BindingResult result) {
		accountInfoValidator.validate(accountInfo, result);
		if (result.hasErrors()) {
			return "account";
		}
		try {
			accountDAO.saveAccountInfo(accountInfo);
		} catch (Exception e) {
			model.addAttribute("errorMessage", e.getMessage());
			return "account";
		}
		return "redirect:/accountList";
	}

	@RequestMapping({ "/deleteAccount" })
	public String deleteAccountHandler(HttpServletRequest request, Model model,
			@RequestParam(value = "userName", defaultValue = "") String userName) {
		Accounts account = null;

		if (userName != null) {
			account = accountDAO.getAccountByUsername(userName);
		}

		if (account != null) {
			accountDAO.deleteAccountByUsername(userName);
		}
		return "redirect:/accountsList";
	}
	
	/*

		@RequestMapping("/search")
		public ModelAndView search(@RequestParam String keyword) {// lay dung ten name ben jsp moi chay
			ModelAndView mav = new ModelAndView("search-users");
			List<User> result = userServiceImpl.search(keyword);
			mav.addObject("result", result);

			return mav;
		}
	
	*/
}
