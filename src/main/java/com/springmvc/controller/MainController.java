package com.springmvc.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.springmvc.dao.OrderDAO;
import com.springmvc.dao.ProductDao;
import com.springmvc.entity.Products;
import com.springmvc.models.CartInfo;
import com.springmvc.models.CustomerInfo;
import com.springmvc.models.PaginationResult;
import com.springmvc.models.ProductInfo;
import com.springmvc.repository.ProductsRepository;
import com.springmvc.utils.Utils;
import com.springmvc.validator.CustomerInfoValidator;

@Controller
public class MainController {

	@Autowired
	private ProductDao productDAO;

	@Autowired
	private OrderDAO orderoDAO;
	
	@Autowired
	private ProductsRepository productsRepo;

	@Autowired
	private CustomerInfoValidator customerInfoValidator;

	// danh sach san pham
	@RequestMapping("/productList")
	public String getAllProductInfos(Model model, @RequestParam(value = "name", defaultValue = "") String likeName,
			@RequestParam(value = "page", defaultValue = "1") int page) {
		final int maxResult = 5;
		final int maxNavigationPage = 10;
		PaginationResult<ProductInfo> productInfos = productDAO.getAllProductInfos(page, maxResult, maxNavigationPage,
				likeName);
		// tao dc 1 trang do result chi dc 5 mon, tong trang thi len 10 trang
		model.addAttribute("paginationProductInfos", productInfos);
		return "productList";
	}

	// lay hinh anh tu may de upload
	@RequestMapping(value = { "/productImage" }, method = RequestMethod.GET)
	public void productImage(HttpServletRequest request, HttpServletResponse response, Model model,
			@RequestParam("code") String code) throws IOException { // track tu ben jsp code qua source se tu dong
																	// request tu day
		Products products = null;
		if (code != null) {
			products = productDAO.getProductByCode(code);
		}

		if (products != null && products.getImage() != null) {
			response.setContentType("image/jpeg, image/jpg, image/png, image/gif"); // set content file de hien len jsp
			response.getOutputStream().write(products.getImage());
		}
		response.getOutputStream().close();
	}

	@RequestMapping({ "/buyProduct" })
	public String buyProductHandler(HttpServletRequest request, Model model,
			@RequestParam(value = "code", defaultValue = "") String code) {
		Products product = null;

		if (code != null && code.length() > 0) {
			product = productDAO.getProductByCode(code);
		}
		if (product != null) {
			// thong tin gio hang co the da luu vao tu session trc do
			CartInfo cartInfo = Utils.getCartInfoInSession(request);
			ProductInfo productInfo = new ProductInfo(product);
			cartInfo.addProduct(productInfo, 1);
		}

		// chuyen sang danh sach cac san pham da mua
		return "redirect:/shoppingCart";
	}

	// GET : hien thi shopping cart
	@RequestMapping(value = { "/shoppingCart" }, method = RequestMethod.GET)
	public String shoppingCartHandler(HttpServletRequest request, Model model) {
		CartInfo cartForm = Utils.getCartInfoInSession(request);
		cartForm.updateQuantity(cartForm); // class="button-update-sc" type="submit" value="Update Quantity"

		model.addAttribute("cartForm", cartForm);// add quantity bat dau tu day : "cartLineInfos[${varStatus.index
													// }].quantity"
		return "shoppingCart";
	}

	// POST: Cập nhập số lượng cho các sản phẩm đã mua.
	@RequestMapping(value = { "/shoppingCart" }, method = RequestMethod.POST)
	public String shoppingCartUpdateQuantity(HttpServletRequest request, Model model,
			@ModelAttribute("cartForm") CartInfo cartForm) {
		CartInfo cartInfo = Utils.getCartInfoInSession(request);
		cartInfo.updateQuantity(cartForm);

		// Chuyển sang trang danh sách các sản phẩm đã mua.
		return "redirect:/shoppingCart";
	}

	@RequestMapping({ "/shoppingCartRemoveProduct" })
	public String removeProductHandler(HttpServletRequest request, Model model,
			@RequestParam(value = "code", defaultValue = "") String code) {
		Products product = null;

		if (code != null && code.length() > 0) {
			product = productDAO.getProductByCode(code);
		}

		if (product != null) {
			// thong tin gio hang co the da luu vao trong session trc do
			CartInfo cartInfo = Utils.getCartInfoInSession(request);
			ProductInfo productInfo = new ProductInfo(product);
			cartInfo.removeProduct(productInfo);
		}
		// chuyen sang danh sach cac san pham da mua
		return "redirect:/shoppingCart";
	}

	// GET: NHAP THONG TIN KHACH HANG
	@RequestMapping(value = { "/shoppingCartCustomer" }, method = RequestMethod.GET)
	public String shoppingCartCustomer(HttpServletRequest request, Model model) {
		CartInfo cartInfo = Utils.getCartInfoInSession(request);

		// chua mua mat hang nao
		if (cartInfo.isEmpty()) {
			// chuyen toi danh sach gio hang
			return "redirect:/shoppingCart";
		}
		CustomerInfo customerInfo = cartInfo.getCustomerInfo();
		if (customerInfo == null) {
			customerInfo = new CustomerInfo();
		}

		model.addAttribute("customerForm", customerInfo);

		return "shoppingCartCustomer";
	}

	@RequestMapping(value = "/shoppingCartCustomer", method = RequestMethod.POST)
	public String shoppingCartCustomerSave(HttpServletRequest request, Model model,
			@ModelAttribute("customerForm") @Validated CustomerInfo customerForm, BindingResult result) {
		customerInfoValidator.validate(customerForm, result);
		// ket qua validate customerinfo
		if (result.hasErrors()) {
			customerForm.setValid(false);

			return "shoppingCartCustomer";
		}
		customerForm.setValid(true);
		CartInfo cartInfo = Utils.getCartInfoInSession(request);
		cartInfo.setCustomerInfo(customerForm);
		// chuyen huong trang xac nhan
		return "redirect:/shoppingCartConfirmation";
	}

	// GET xem lai thong tin xac nhan
	@RequestMapping(value = "/shoppingCartConfirmation", method = RequestMethod.GET)
	public String shoppingCartConfirmationReview(HttpServletRequest request, Model model) {
		CartInfo cartInfo = Utils.getCartInfoInSession(request);

		// neu chua co mat hang nao
		if (cartInfo.isEmpty()) {
			// chuyen toi trang danh sach gio hang
			return "redirect:/shoppingCart";
		} else if (!cartInfo.isValidCustomer()) {
			// chuyen toi trang nhap thong tin khach hang
			return "redirect:/shoppingCartCustomer";
		}
		return "/shoppingCartConfirmation";
	}

	// post gui donhang (save)
	@RequestMapping(value = { "/shoppingCartConfirmation" }, method = RequestMethod.POST)
	private String shoppingCartConfirmationSave(HttpServletRequest request, Model model) {
		CartInfo cartInfo = Utils.getCartInfoInSession(request);

		// chua mat hang nao dc them vao gio hang
		if (cartInfo.isEmpty()) {
			// chuyen toi trang danh sach gio hang
			return "redirect:/shoppingCart";
		} else if (!cartInfo.isValidCustomer()) {
			// chuyen toi trang nhap thong tin khach hang
			return "redirect:/shoppingCartCustomer";
		}
		try {
			orderoDAO.saveOrder(cartInfo);
		} catch (Exception e) {
			// can thiet: propogation never ?
			return "/shoppingCartConfirmation";
		}
		// xoa hang khoi session
		Utils.removeCartInfoSession(request);

		// luu thong tin don hang da xac nhan mua
		Utils.storeLastOrderedCartInfoInSession(request, cartInfo);

		// chuyen huong toi trang hoan thanh gio hang
		return "redirect:/shoppingCartFinalize";
	}

	@RequestMapping(value = { "/shoppingCartFinalize" }, method = RequestMethod.GET)
	public String shoppingCartFinalize(HttpServletRequest request, Model model) {

		CartInfo lastOrderedCart = Utils.getLastOrderedCartInfoInSession(request);
		if (lastOrderedCart == null) {
			return "redirect:/shoppingCart";
		}
		return "shoppingCartFinalize";
	}
	
	@RequestMapping("/search")
	public ModelAndView search(@RequestParam String keyword) {
		ModelAndView mav = new ModelAndView("product-search-result");

		List<Products> result = productsRepo.search(keyword);
		mav.addObject("result", result);

		return mav;
	}

}
