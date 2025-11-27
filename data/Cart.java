package com.test.ygdd.data;
/**
 * 장바구니 정보 클래스
 */
public class Cart {
	
	private String storeUserCode;
	private String generalUserCode;
	private String foodName;
	
	/**
	 * 장바구니 정보 클래스 생성자
	 * @param storeUserCode
	 * @param generalUserCode
	 * @param foodName
	 */
	public Cart(String storeUserCode, String generalUserCode, String foodName) {
		this.storeUserCode = storeUserCode;
		this.generalUserCode = generalUserCode;
		this.foodName = foodName;
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
	 * 객체의 정보를 문자열로 반환하는 메서드
	 */
	@Override
	public String toString() {
		return "Cart [storeUserCode=" + storeUserCode + ", generalUserCode=" + generalUserCode + ", foodName="
				+ foodName + "]";
	}
	
}
