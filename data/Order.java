package com.test.ygdd.data;

/**
 * 주문 정보 클래스
 */
public class Order {
	
	private String storeUserCode;
	private String generalUserCode;
	private String orderCode;
	private String foodName;
	private int price;
	
	/**
	 * 주문 정보 클래스 생성자
	 * @param storeUserCode
	 * @param generalUserCode
	 * @param orderCode
	 * @param foodName
	 * @param price
	 */
	public Order(String storeUserCode, String generalUserCode, String orderCode, String foodName
				,int price) {
		this.storeUserCode = storeUserCode;
		this.generalUserCode = generalUserCode;
		this.orderCode = orderCode;
		this.foodName = foodName;
		this.price = price;
	}
	/**
	 * 가맹점회원코드 데이터를 가져오는 메서드
	 * @return
	 */
	public String getStoreUserCode() {
		return storeUserCode;
	}
	/**
	 * 가맹점회원코드 데이터를 수정하는 메서드
	 * @param storeUserCode
	 */
	public void setStoreUserCode(String storeUserCode) {
		this.storeUserCode = storeUserCode;
	}
	/**
	 * 일반회원코드 데이터를 가져오는 메서드
	 * @return
	 */
	public String getGeneralUserCode() {
		return generalUserCode;
	}
	/**
	 * 일반회원코드 데이터를 수정하는 메서드
	 * @param generalUserCode
	 */
	public void setGeneralUserCode(String generalUserCode) {
		this.generalUserCode = generalUserCode;
	}
	/**
	 * 주문코드 데이터를 가져오는 메서드
	 * @return
	 */
	public String getOrderCode() {
		return orderCode;
	}
	/**
	 * 주문코드 데이터를 수정하는 메서드
	 * @param orderCode
	 */
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
	/**
	 * 음식명 데이터를 가져오는 메서드
	 * @return
	 */
	public String getFoodName() {
		return foodName;
	}
	/**
	 * 음식명 데이터를 수정하는 메서드
	 * @param foodName
	 */
	public void setFoodName(String foodName) {
		this.foodName = foodName;
	}
	/**
	 * 음식가격 데이터를 가져오는 메서드
	 * @return
	 */
	public int getPrice() {
		return price;
	}
	/**
	 * 음식가격 데이터를 수정하는 메서드
	 * @param price
	 */
	public void setPrice(int price) {
		this.price = price;
	}
	/**
	 * 객체의 정보를 문자열로 반환하는 메서드
	 */
	@Override
	public String toString() {
		return "Order [storeUserCode=" + storeUserCode + ", generalUserCode=" + generalUserCode + ", orderCode="
				+ orderCode + ", foodName=" + foodName + ", price=" + price + "]";
	}
	
}
