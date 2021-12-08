package com.springmvc.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.springmvc.dao.OrderDAO;
import com.springmvc.dao.ProductDao;
import com.springmvc.entity.Accounts;
import com.springmvc.models.OrderDetailInfo;
import com.springmvc.models.OrderInfo;
import com.springmvc.models.PaginationResult;
import com.springmvc.models.ProductInfo;
import com.springmvc.service.AccountsServiceImpl;
import com.springmvc.validator.AccountsInfoValidator;
import com.springmvc.validator.ProductInfoValidator;

@Controller
public class AdminController {
	
	@Autowired
	private OrderDAO orderoDAO;
	
	@Autowired
	private ProductDao productDAO;
	
	@Autowired
	private AccountsServiceImpl accountServImpl;
	
	@Autowired
	private ProductInfoValidator productInfoValidator;
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
		return "redirect://productList";
	}
	
	//get DELETE PRODUCT
	@GetMapping("/delete")
	public String deleteProduct(@RequestParam("code") String code) {
		productDAO.deleteProductByCode(code);
		return "redirect://productList";
	}
	
	
	
	
	@RequestMapping("/accountsList")
	public ModelAndView accountsList() {
		ModelAndView mav = new ModelAndView("accountsList");
		List<Accounts> listAccounts = accountServImpl.getAllAccounts();
		mav.addObject("listAccounts", listAccounts);
		return mav;
	}
	
	// go to create a new user
		@RequestMapping("/account")
		public String addNewAccountsForm(Map<String, Object> model) {
			model.put("account", new Accounts());
			
			return "add-new-user-form";
		}

		// inserting and updating a new user method
		@RequestMapping(value = "/insert", method = RequestMethod.POST)
		public String insertUser(@ModelAttribute("accounts") Accounts accounts) {
			accountServImpl.saveAccounts(accounts);

			return "redirect:/accounts/accountsList";
		}

		/*
		// go to editing form and edting a user
		@RequestMapping("/edit")
		public ModelAndView editUserForm(@RequestParam long user_id, Model model) {
			// @ParamVariable("user_id") Long user_id
			ModelAndView mav = new ModelAndView("edit-user-form");

			User user = userServiceImpl.getUserById(user_id);
			List<Role> listRoles = userServiceImpl.getRoles();

			mav.addObject("user", user);
			model.addAttribute("listRoles", listRoles);

			return mav;
		}

		@RequestMapping("/delete")
		public String deleteUserById(@RequestParam long user_id) {
			userServiceImpl.deleteUserById(user_id);

			return "redirect:/user/list";
		}

		@RequestMapping("/search")
		public ModelAndView search(@RequestParam String keyword) {// lay dung ten name ben jsp moi chay
			ModelAndView mav = new ModelAndView("search-users");
			List<User> result = userServiceImpl.search(keyword);
			mav.addObject("result", result);

			return mav;
		}
	
	*/
}
