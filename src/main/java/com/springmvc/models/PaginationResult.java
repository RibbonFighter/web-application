package com.springmvc.models;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;
import org.hibernate.query.Query;

public class PaginationResult<E> { //E = ProductInfos file generic

	private int totalRecords;

	private int currentPage;

	private List<E> list; //paginationProductInfos.list ben productList

	private int maxResult;

	private int totalPage;

	private int maxNavigationPage;

	private List<Integer> navigationPages;

	public int getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public List<E> getList() {
		return list;
	}

	public void setList(List<E> list) {
		this.list = list;
	}

	public int getMaxResult() {
		return maxResult;
	}

	public void setMaxResult(int maxResult) {
		this.maxResult = maxResult;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getMaxNavigationPage() {
		return maxNavigationPage;
	}

	public void setMaxNavigationPage(int maxNavigationPage) {
		this.maxNavigationPage = maxNavigationPage;
	}

	public List<Integer> getNavigationPages() {
		return navigationPages;
	}

	public void setNavigationPages(List<Integer> navigationPages) {
		this.navigationPages = navigationPages;
	}

	public PaginationResult(Query query, int page, int maxResult, int maxNavigationPage) {
		int pageIndex = page - 1 < 0 ? 0 : page - 1;// 1 -1 < 0 ? 0 : 1 - 1 = ket qua 0
		//hien tai co 2 trang vay thi 2 - 1 < 0 ? 0 : 2 - 1 --> 1
		int fromRecordIndex = pageIndex * maxResult; // 0 * 5 = 0
		int maxRecordIndex = fromRecordIndex + maxResult; // 0 + 5 = 5

		ScrollableResults resultScroll = query.scroll(ScrollMode.SCROLL_INSENSITIVE); //hibernate phan trang thi dung ham nay

		List<E> results = new ArrayList<E>(); //productInfo = E nen cho no List<ProductInfo> results = ...

		boolean hasResult = resultScroll.first();

		if (hasResult) {
			// cuon toi vi tri
			hasResult = resultScroll.scroll(fromRecordIndex); //0 cach no se di toi vi tri cua cai page
			// bat dau o trang 2 thi se scroll tu 5 -> 9 va giong nhu cach thuc trang 1
			if (hasResult) {
				do { // chay lenh nay it nhat mot lan du co sai hay ko 
					
					E record = (E) resultScroll.get(0); //ProdyctInfo record = bat dau tu 0 add tu tu toi het
					results.add(record); //dang add product tu result vo list
				} while (resultScroll.next() && resultScroll.getRowNumber() >= fromRecordIndex // 3 > 0 // 4 >= 0
						//.next de check coi la phan tu co ton tai hay ko de tranh bao loi
						// cu lay mot gia tri thi se add vo list
						//.getrownumber de lay record tu 0 toi 4
						&& resultScroll.getRowNumber() < maxRecordIndex); //3 < 5 // 4 < 5
						// dk dung thi no se di tiep
			}
			// chuyen toi ban ghi cuoi
			resultScroll.last();
		}
		// tong so ban ghi.
		this.totalRecords = resultScroll.getRowNumber() + 1; // cho toi vi tri thu 9 = de tinh tong so record co la bao nhieu bat dau
		// tu 0 toi het, + 1 = ra du 10 record
		// = i
		this.currentPage = pageIndex + 1; //pageIndex = 0 , currentpage = 1
		this.list = results; // o tren List toi 4
		this.maxResult = maxResult; // tong so trang

		this.totalPage = (this.totalRecords / this.maxResult) + 1; //10 / 5 = 2 + 1 = 3 trang hien thi 
		//de no lo co du thi se hien ra mot trang nua confirm la lay du record
		//gia su co 60 record thi chia 5
		this.maxNavigationPage = maxNavigationPage;

		if (maxNavigationPage < this.totalPage) {
			this.maxNavigationPage = this.totalPage; //de can xung lai tong so trang neu luong total page nhieu hon
			//maxNavpage thi se cho totalpage = maxNavpage
		}

		this.calcNavigationPages();
	}

	private void calcNavigationPages() {

		this.navigationPages = new ArrayList<Integer>();

		int current = this.currentPage > this.totalPage ? this.totalPage : this.currentPage;

		int begin = current - this.maxNavigationPage / 2;
		int end = current + this.maxNavigationPage / 2;

		// trang dau tien
		this.navigationPages.add(1);
		if (begin > 2) {
			// dung cho
			this.navigationPages.add(-1);
		}

		for (int i = begin; i < end; i++) {
			if (i > 1 && i < this.totalPage) {
				this.navigationPages.add(i);
			}
		}

		if (end < this.totalPage - 2) {
			// dung cho "..."
			this.navigationPages.add(-1);
		}
		// trang cuoi cung
		this.navigationPages.add(this.totalPage);
	}
}
