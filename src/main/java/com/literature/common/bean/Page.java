package com.literature.common.bean;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
/**
 * 分页Bean
 * @author Literature
 * @date 2018年10月25日
 *
 */
public class Page<T> {
	//分页参数
    private int pageNow=1;
    private int pageSize=0;
    private long rowCount;
    private long pageCount;

	private String orderBy;//设置排序字段
	private String orderWay;//设置排序方向
	
	//-- 公共变量 --//
	public static final String ASC = "asc";
	public static final String DESC = "desc";
 
    private List<T> pageList;

	public int getPageNow() {
		return pageNow;
	}

	public void setPageNow(int pageNow) {
		this.pageNow = pageNow;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public long getRowCount() {
		return rowCount;
	}

	public void setRowCount(long rowCount) {
		this.rowCount = rowCount;
	}

	/**
	 * 计算出总页数
	 * @return
	 */
	public long getPageCount() {
		if (rowCount < 0) {
			return -1;
		}
		pageCount=(rowCount-1)/pageSize+1;
		return pageCount;
	}

	public void setPageCount(long pageCount) {
		this.pageCount = pageCount;
	}

	public List<T> getPageList() {
		return pageList;
	}

	public void setPageList(List<T> pageList) {
		this.pageList = pageList;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public String getOrderWay() {
		return orderWay;
	}

	public void setOrderWay(String orderWay) {
		this.orderWay = orderWay;
	}	
	
	/**
	 * 是否已设置排序字段,无默认值.
	 */
	public boolean isOrderBySetted() {
		return (StringUtils.isNotBlank(orderBy) && StringUtils.isNotBlank(orderWay));
	}
	/**
	 * 是否还有下一页.
	 * @return boolean
	 */
	public boolean isHasNext() {
		return (pageNow + 1 <= getPageCount());
	}

	/**
	 * 取得下页的页号, 序号从1开始.
	 * 当前页为尾页时仍返回尾页序号.
	 */
	public int getNextPage() {
		if (isHasNext()) {
			return pageNow + 1;
		} else {
			return pageNow;
		}
	}

	/**
	 * 是否还有上一页.
	 */
	public boolean isHasPre() {
		return (pageNow - 1 >= 1);
	}

	/**
	 * 取得上页的页号, 序号从1开始.
	 * 当前页为首页时返回首页序号.
	 */
	public int getPrePage() {
		if (isHasPre()) {
			return pageNow - 1;
		} else {
			return pageNow;
		}
	}
}
