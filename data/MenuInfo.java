package com.test.ygdd.data;

/**
 * 메뉴 정보 클래스
 */
public class MenuInfo {
	
	private String storeUserCode;
	private String storeName;
	private String foodName;
	private int price;
	private int time;
	
	/**
	 * 메뉴 정보 클래스 생성자
	 * @param storeUserCode
	 * @param storeName
	 * @param foodName
	 * @param price
	 * @param time
	 */
	public MenuInfo(String storeUserCode, String storeName, String foodName, int price
					,int time) {
		this.storeUserCode = storeUserCode;
		this.storeName = storeName;
		this.foodName = foodName;
		this.price = price;
		this.time = time;
	}
	/**
	 * 가맹점회원코드 데이터를 가져오는 메서드
	 * @return
	 */
	public String getStoreUserCode() {
		return storeUserCode;
	}
	/**
	 * 가맹점회원코드를 데이터를 수정하는 메서드
	 * @param storeUserCode
	 */
	public void setStoreUserCode(String storeUserCode) {
		this.storeUserCode = storeUserCode;
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
	/**
	 * 조리시간 데이터를 가져오는 메서드
	 * @return
	 */
	public int getTime() {
		return time;
	}
	/**
	 * 조리시간 데이터를 수정하는 메서드
	 * @param time
	 */
	public void setTime(int time) {
		this.time = time;
	}
	/**
	 * 객체의 정보를 문자열로 반환하는 메서드
	 */
	@Override
	public String toString() {
		return "MenuInfo [storeUserCode=" + storeUserCode + ", storeName=" + storeName + ", foodName=" + foodName
				+ ", price=" + price + ", time=" + time + "]";
	}
	
}
