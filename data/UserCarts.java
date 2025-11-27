package com.test.ygdd.data;

/**
 * 내 장바구니 정보 클래스
 */
public class UserCarts {

	private int index;
	private String storeCode;
	private String storeName;
	private String foodName;
	private int price;
	
	/**
	 * 내 장바구니 정보 클래스 생성자
	 * @param index
	 * @param storeCode
	 * @param storeName
	 * @param foodName
	 * @param price
	 */
	public UserCarts(int index, String storeCode, String storeName, String foodName, int price) {
		this.index = index;
		this.storeCode = storeCode;
		this.storeName = storeName;
		this.foodName = foodName;
		this.price = price;
	}
	/**
	 * 길이 데이터를 가져오는 메서드
	 * @return
	 */
	public int getIndex() {
		return index;
	}
	/**
	 * 길이 데이터를 수정하는 메서드
	 * @param index
	 */
	public void setIndex(int index) {
		this.index = index;
	}
	/**
	 * 가맹점회원코드 데이터를 가져오는 메서드
	 * @return
	 */
	public String getStoreCode() {
		return storeCode;
	}
	/**
	 * 가맹점회원코드 데이터를 수정하는 메서드
	 * @param storeCode
	 */
	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}
	/**
	 * 가게명 데이터를 가져오는 메서드
	 * @return
	 */
	public String getStoreName() {
		return storeName;
	}
	/**
	 * 가게명 데이터를 수정하는 메서드
	 * @param storeName
	 */
	public void setStoreName(String storeName) {
		this.storeName = storeName;
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

	@Override
	public String toString() {
		return "UserCarts [storeCode=" + storeCode + ", foodName=" + foodName + ", price=" + price
				+ "]";
	}
}
