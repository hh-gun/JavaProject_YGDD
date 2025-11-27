package com.test.ygdd.data;

/**
 * 주문상세정보 클래스
 */
public class OrderDetail {

	private String OrderCode;
	private String GeneralUserCode;
	private String FoodName;
	private int price;
	
	/**
	 * 주문상세정보 클래스 생성자
	 * @param orderCode
	 * @param generalUserCode
	 * @param foodName
	 * @param price
	 */
	public OrderDetail(String orderCode, String generalUserCode, String foodName, int price) {
		OrderCode = orderCode;
		GeneralUserCode = generalUserCode;
		FoodName = foodName;
		this.price = price;
	}
	/**
	 * 주문코드 데이터를 가져오는 메서드
	 * @return
	 */
	public String getOrderCode() {
		return OrderCode;
	}
	/**
	 * 주문코드 데이터를 수정하는 메서드
	 * @param orderCode
	 */
	public void setOrderCode(String orderCode) {
		OrderCode = orderCode;
	}
	/**
	 * 일반회원코드 데이터를 가져오는 메서드
	 * @return
	 */
	public String getGeneralUserCode() {
		return GeneralUserCode;
	}
	/**
	 * 일반회원코드 데이터를 수정하는 메서드
	 * @param generalUserCode
	 */
	public void setGeneralUserCode(String generalUserCode) {
		GeneralUserCode = generalUserCode;
	}
	/**
	 * 음식명 데이터를 가져오는 메서드
	 * @return
	 */
	public String getFoodName() {
		return FoodName;
	}
	/**
	 * 음식명 데이터를 수정하는 메서드
	 * @param foodName
	 */
	public void setFoodName(String foodName) {
		FoodName = foodName;
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
	
	@Override
	public String toString() {
		return "OrderDetail [OrderCode=" + OrderCode + ", GeneralUserCode=" + GeneralUserCode + ", FoodName=" + FoodName
				+ ", price=" + price + "]";
	}
}
