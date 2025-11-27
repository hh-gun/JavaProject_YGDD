package com.test.ygdd.data;

/**
 * 음식카테고리 정보 클래스
 */
public class Category {
	
	private String categoryCode;
	private String categoryName;
	
	/**
	 * 음식카테고리 정보 클래스 생성자
	 * @param categoryCode
	 * @param categoryName
	 */
	public Category(String categoryCode, String categoryName) {
		this.categoryCode = categoryCode;
		this.categoryName = categoryName;
	}
	/**
	 * 카테고리코드 데이터를 가져오는 메서드
	 * @return
	 */
	public String getCategoryCode() {
		return categoryCode;
	}
	/**
	 * 카테고리코드 데이터를 수정하는 메서드
	 * @param categoryCode
	 */
	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}
	/**
	 * 카테고리명 데이터를 가져오는 메서드
	 * @return
	 */
	public String getCategoryName() {
		return categoryName;
	}
	/**
	 * 카테고리명 데이터를 수정하는 메서드
	 * @param categoryName
	 */
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	/**
	 * 객체의 정보를 문자열로 반환하는 메서드
	 */
	@Override
	public String toString() {
		return "Category [categoryCode=" + categoryCode + ", categoryName=" + categoryName + "]";
	}
	
}
