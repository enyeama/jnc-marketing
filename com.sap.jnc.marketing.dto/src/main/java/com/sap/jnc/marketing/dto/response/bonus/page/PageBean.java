package com.sap.jnc.marketing.dto.response.bonus.page;

import java.io.Serializable;
import java.util.List;

/**
 * 分页实体对象
 * 
 * @author I327119
 * @param <T>
 */
public class PageBean<T extends Serializable> implements Serializable {

	private static final long serialVersionUID = -1807322006193703312L;

	// -- 传递的数据或配置信息
	private int currentPage; // 当前页
	private int pageRow; // 每页记录数

	// -- 查询数据库
	private int totalRow; // 总记录数
	private List<T> pageList; // 当前页的所有数据

	// -- 计算的结果
	private int firstPage; // 首页
	private int beginPage; // 当前显示起始页
	private int endPage; // 当前显示结束页
	private int lastPage; // 尾页
	private int totalPage; // 总页数

	/**
	 * 接收前面几个属性，计算出后面的属性
	 * 
	 * @param currentPage
	 *            当前页数
	 * @param pageRow
	 *            单页记录顺
	 * @param totalRow
	 *            总记录数
	 * @param pageList
	 *            当前页数据
	 */
	public PageBean(int currentPage, int pageRow, int totalRow, List<T> pageList) {
		this.currentPage = currentPage;
		this.pageRow = pageRow;
		this.totalRow = totalRow;
		this.pageList = pageList;

		// 计算总页数
		totalPage = (totalRow % pageRow > 0) ? (totalRow / pageRow + 1) : (totalRow / pageRow);

		if (totalPage <= 5) {
			// a,总页数不超过10页,全部显示
			firstPage = beginPage = 1;
			lastPage = endPage = totalPage;
		}
		// b,总页数超过10页,显示当前页前4页和后5页
		else {
			// 初始化firstPage和lastPage
			firstPage = 1;
			lastPage = totalPage;

			// 计算beginPage
			beginPage = currentPage - 2;
			// 计算endPage
			endPage = currentPage + 2;

			if (beginPage < 1) {
				// 如果首页beginPage<0,显示前10页
				beginPage = 1;
				endPage = 5;
			}
			else if (endPage > totalPage) {
				// 如果尾页endPage>totalPage,显示后5页
				beginPage = totalPage - 5 + 1;
				endPage = totalPage;
			}
		}

	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getPageRow() {
		return pageRow;
	}

	public void setPageRow(int pageRow) {
		this.pageRow = pageRow;
	}

	public int getTotalRow() {
		return totalRow;
	}

	public void setTotalRow(int totalRow) {
		this.totalRow = totalRow;
	}

	public List<T> getPageList() {
		return pageList;
	}

	public void setPageList(List<T> pageList) {
		this.pageList = pageList;
	}

	public int getFirstPage() {
		return firstPage;
	}

	public void setFirstPage(int firstPage) {
		this.firstPage = firstPage;
	}

	public int getBeginPage() {
		return beginPage;
	}

	public void setBeginPage(int beginPage) {
		this.beginPage = beginPage;
	}

	public int getEndPage() {
		return endPage;
	}

	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}

	public int getLastPage() {
		return lastPage;
	}

	public void setLastPage(int lastPage) {
		this.lastPage = lastPage;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	@Override
	public String toString() {
		return "PageBean [currentPage=" + currentPage + ", pageRow=" + pageRow + ", totalRow=" + totalRow + ", pageList=" + pageList + ", firstPage="
			+ firstPage + ", beginPage=" + beginPage + ", endPage=" + endPage + ", lastPage=" + lastPage + ", totalPage=" + totalPage + "]";
	}

}
