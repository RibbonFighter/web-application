package com.springmvc.dao.impl;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.springmvc.dao.ProductDao;
import com.springmvc.entity.Products;
import com.springmvc.models.PaginationResult;
import com.springmvc.models.ProductInfo;

@Repository
@Transactional
public class ProductDAOImpl implements ProductDao {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private ProductDao productDAO;

	@Override
	public PaginationResult<ProductInfo> getAllProductInfos(int page, int maxResult, int maxNavigationPage,
			String likeName) {

		Session session = sessionFactory.getCurrentSession();
		// SELECT PRO.code, PRO.name, PRO.price FROM Products PRO --> cach thuong su
		// dung
		// muon su dung NEW " + ProductInfo.class.getName() thi trong class ProductInfo
		// phai khai bao Constructor
		// co 3 tham so la code, name , price
		String hql = "SELECT NEW " + ProductInfo.class.getName()
				+ " (PRO.code, PRO.name, PRO.price) FROM Products PRO ";
		//viet nho cach ra " " 
		if (likeName != null && likeName.length() > 0) {
			hql += " WHERE LOWER(PRO.name) LIKE :LIKENAME ";
		}
		hql += " ORDER BY PRO.createDate DESC ";

		Query<ProductInfo> query = session.createQuery(hql);
		List<ProductInfo> productInfos = query.list();
		if (likeName != null && likeName.length() > 0) {
			query.setParameter("LIKENAME", "%" + likeName.toLowerCase() + "%");
		}
		return new PaginationResult<ProductInfo>(query, page, maxResult, maxNavigationPage);

	}

	@Override
	public Products getProductByCode(String code) {

		Session session = sessionFactory.getCurrentSession();
		String hql = "SELECT PRO FROM Products PRO WHERE PRO.code = :CODE";
		Query<Products> query = session.createQuery(hql);
		query.setParameter("CODE", code);
		Products products = (Products) query.uniqueResult();
		return products;
	}

	@Override
	public ProductInfo getProductInfoByCode(String code) {
		Products product = getProductByCode(code);
		if(product == null) {
			return null;
		}
		
		ProductInfo productInfo = new ProductInfo(product.getCode(), product.getName(), product.getPrice());
		return productInfo;
	}

	@Override
	public void saveProductInfo(ProductInfo productInfo) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		String code = productInfo.getCode();
		Products product = null;
		boolean isNew = false;
		
		if (code != null) {
			product = getProductByCode(code);
		}
		
		if (product == null) {
			isNew = true;
			product = new Products();
			product.setCreateDate(new Date());
		}
		
		product.setCode(code);
		product.setName(productInfo.getName());
		product.setPrice(productInfo.getPrice());
		
		if (productInfo.getFileData() != null) {
			byte[] image = productInfo.getFileData().getBytes();
			if (image != null && image.length > 0) {
				product.setImage(image);
			}
		}
		if (isNew) {
			session.persist(product);
		}
		//neu co loi tai db, ngoai le se nem ra ngay lap tap
		session.flush();
	}

	@Override
	public boolean deleteProductByCode(String code) {
		try {
			Session session = sessionFactory.getCurrentSession();	
			String hql = "DELETE FROM Products PRO WHERE PRO.code = :code";
			Query query = session.createQuery(hql);
			query.setParameter("CODE", code);
			query.executeUpdate();
			return true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return false;
	}

}
